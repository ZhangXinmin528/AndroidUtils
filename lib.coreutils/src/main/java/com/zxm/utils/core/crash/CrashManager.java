package com.zxm.utils.core.crash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import android.util.Log;

import com.zxm.utils.core.file.FileUtils;
import com.zxm.utils.core.time.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZhangXinmin on 2019/8/15.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class CrashManager implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashManager.class.getSimpleName();

    private static final SimpleDateFormat DEFAULT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    //系统程序
    private final Thread.UncaughtExceptionHandler mSystemExceptionHandler;

    private Context mContext;

    //Crash params
    private CrashConfig.Builder mCrashParams;

    private Handler mHandler;

    private CrashManager() {
        mSystemExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        final HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();

        mHandler = new Handler(handlerThread.getLooper());
    }

    public static CrashManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Init {@link CrashConfig}.
     *
     * @param config CrashConfig
     */
    public void init(@NonNull CrashConfig config) {
        mCrashParams = config.builder;
        mContext = mCrashParams.context;
    }

    /**
     * Start useing custom exception handler.
     */
    public void startCapture() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * Stop useing custom exception handler.
     */
    public void stopCapture() {
        Thread.setDefaultUncaughtExceptionHandler(mSystemExceptionHandler);
    }

    //Method invoked when the given thread terminates
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        Log.d(TAG, "uncaughtException()~");

        @SuppressLint("MissingPermission") final File dir = getCrashCacheDir();

        if (dir != null) {
            final File file = new File(dir, TimeUtil.getNowString(DEFAULT_FORMAT) + ".txt");
            saveCrashCache(Log.getStackTraceString(e), file);
            //自己处理完毕，切换为系统的处理程序
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSystemExceptionHandler != null) {
                        mSystemExceptionHandler.uncaughtException(t, e);
                    }
                }
            }, 1500);
        }

    }

    /**
     * Save crash information to local file.
     *
     * @param s    Crash information.
     * @param file The file.
     */
    private boolean saveCrashCache(@NonNull Serializable s, @NonNull File file) {
        if (s != null && file != null) {
            Log.d(TAG, "crash file path : " + file.getAbsolutePath());
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(s);
                oos.flush();
                return true;
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                return false;
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the dir of the crash information.
     *
     * @return file or null.
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private File getCrashCacheDir() {
        if (mCrashParams != null) {
            String parentDir = mCrashParams.parentDir;
            String dirName = mCrashParams.cacheDirName;

            if (TextUtils.isEmpty(parentDir)) {
                if (mContext != null) {
                    parentDir = mContext.getCacheDir().getPath();
                }
            }
            if (TextUtils.isEmpty(dirName)) {
                dirName = "crash_info";
            }

            final File file = new File(parentDir + File.separator + dirName);

            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    /**
     * Clear crash files in the directory{@link #getCrashCacheDir()}
     *
     * @return true or false
     */
    @SuppressLint("MissingPermission")
    public boolean clearCrashFiles() {
        return FileUtils.delete(getCrashCacheDir());
    }

    /**
     * Get crash files length.
     *
     * @return long value
     */
    @SuppressLint("MissingPermission")
    public long getCrashFilesSize() {
        return FileUtils.getDirLength(getCrashCacheDir());
    }

    /**
     * Get crash files size.
     *
     * @return long value
     */
    public String getCrashFilesMemorySize() {
        final long length = getCrashFilesSize();
        if (length != -1) {
            if (length < 1024) {
                return String.format(Locale.getDefault(), "%.3fB", (double) length);
            } else if (length < 1048576) {
                return String.format(Locale.getDefault(), "%.3fKB", (double) length / 1024);
            } else if (length < 1073741824) {
                return String.format(Locale.getDefault(), "%.3fMB", (double) length / 1048576);
            } else {
                return String.format(Locale.getDefault(), "%.3fGB", (double) length / 1073741824);
            }
        }
        return "unKnown";
    }

    public List<CrashInfo> getCrashCaches() {
        @SuppressLint("MissingPermission") final File cacheDir = getCrashCacheDir();
        final List<CrashInfo> caches = new ArrayList<>();
        if (cacheDir.exists() &&
                cacheDir.isDirectory()) {
            final File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    final Serializable ser = readCrashCache(file);
                    if (ser != null && ser instanceof Throwable) {
                        final CrashInfo crashInfo = new CrashInfo((Throwable) ser, file.lastModified());
                        caches.add(crashInfo);
                    }
                }
            }
        }
        return caches;
    }

    /**
     * Read the cache file and convert it to java object.
     *
     * @param data file
     * @return Serializable
     */
    private Serializable readCrashCache(@NonNull File data) {
        if (data == null || !data.exists() || data.isDirectory()) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(data);
            ois = new ObjectInputStream(fis);

            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {

            try {
                if (ois != null) {
                    ois.close();
                }

                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }

        }

    }

    private static class Holder {
        private static final CrashManager INSTANCE = new CrashManager();
    }

}
