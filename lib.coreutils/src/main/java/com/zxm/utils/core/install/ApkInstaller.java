package com.zxm.utils.core.install;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.zxm.utils.core.BuildConfig;
import com.zxm.utils.core.device.DeviceUtil;
import com.zxm.utils.core.file.FileUtils;
import com.zxm.utils.core.install.service.InstallerService;
import com.zxm.utils.core.log.MLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by ZhangXinmin on 2018/8/24.
 * Copyright (c) 2018 . All rights reserved.
 * The Util is used to help developers to
 * install android app sliently.
 */
public final class ApkInstaller {

    private static final String TAG = ApkInstaller.class.getSimpleName();

    private static ApkInstaller INSTANCE;
    private Context mContext;
    private OnInstallStateListener mStateListener;
    private InstallState mInstallState;

    private ApkInstaller(Context context) {
        mContext = context;

        initParams();
    }

    public static synchronized ApkInstaller getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ApkInstaller(context);
        }
        return INSTANCE;
    }

    private void initParams() {
        mInstallState = new InstallState();
    }

    /**
     * 设置安装状态监听器
     *
     * @param listener
     */
    public void setInstallStateListener(OnInstallStateListener listener) {
        this.mStateListener = listener;
    }

    /**
     * 指定Apk路径进行安装
     * <p>
     * 需要根据设备是否root采取不同的安装方案；
     * </p>
     *
     * @param filePath
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void startInstall(@NonNull String filePath) {
        final File file = FileUtils.getFileByPath(filePath);
        if (FileUtils.isFileExists(file)) {

            if (mStateListener != null) {
                mStateListener.onInstallStart(filePath);
            }

            final boolean isRoot = DeviceUtil.isDeviceRooted();
            MLogger.d(TAG, "Current device is root?-->" + isRoot);
            if (isRoot) {
                installApkWithRoot(filePath);
            } else {
                installApkWitNoRoot(file);
            }
        } else {
            if (mStateListener != null) {
                mInstallState.setType(StateType.TYPE_INVALID_FILE_PATH);
                mInstallState.setMessage(StateMessage.INVALID_FILE_PATH);
                mStateListener.onInstallFailed(mInstallState);
            }
            MLogger.e(TAG, "The file path of apk is null or empty!");
        }
    }

    /**
     * 安装指定路径下的apk
     * <p>
     * 设备已经Root
     * </p>
     *
     * @param filePath 文件路径
     * @return 是否安装成功
     */
    private boolean installApkWithRoot(String filePath) {
        boolean result = false;

        final InstallState installState = new InstallState();

        if (!TextUtils.isEmpty(filePath)) {

            Process process = null;
            OutputStream outputStream = null;
            BufferedReader errorStream = null;

            try {
                //1.SecurityException;2.NullPointerException;
                //3.IllegalArgumentException;4.IOException
                process = Runtime.getRuntime().exec("su");
                outputStream = process.getOutputStream();

                String command = "pm install -r " + filePath + "\n";
                //IOException
                outputStream.write(command.getBytes());
                outputStream.flush();
                outputStream.write("exit\n".getBytes());
                outputStream.flush();
                //InterruptedException
                process.waitFor();
                errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder msg = new StringBuilder();
                String line;
                //IOException
                while ((line = errorStream.readLine()) != null) {
                    msg.append(line);
                }
                MLogger.d(TAG, "install msg is " + msg);
                if (!msg.toString().contains("Failure")) {
                    result = true;
                    if (mStateListener != null) {
                        mStateListener.onInstallComplete();
                    }
                }
            } catch (NullPointerException e) {
                if (mStateListener != null) {
                    mInstallState.setType(StateType.TYPE_NULL_REFERENCE);
                    mInstallState.setMessage(StateMessage.FAILED_NULL_REFERENCE + e.getMessage());
                    mStateListener.onInstallFailed(mInstallState);
                }
                MLogger.e(TAG, e.getMessage());
            } catch (InterruptedException e) {
                if (mStateListener != null) {
                    mInstallState.setType(StateType.TYPE_THREAD_INTERRUPTED);
                    mInstallState.setMessage(StateMessage.FAILED_THREAD_INTERRUPTED + e.getMessage());
                    mStateListener.onInstallFailed(mInstallState);
                }
                MLogger.e(TAG, e.getMessage());
            } catch (IOException e) {
                if (mStateListener != null) {
                    mInstallState.setType(StateType.TYPE_IO_OPERATION);
                    mInstallState.setMessage(StateMessage.FAILED_IO_OPERATION + e.getMessage());
                    mStateListener.onInstallFailed(mInstallState);
                }
                MLogger.e(TAG, e.getMessage());
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (errorStream != null) {
                        errorStream.close();
                    }
                } catch (IOException e) {
                    outputStream = null;
                    errorStream = null;
                    process.destroy();
                }
            }
        } else {
            if (mStateListener != null) {
                installState.setType(StateType.TYPE_INVALID_FILE_PATH);
                installState.setMessage(StateMessage.INVALID_FILE_PATH);
                mStateListener.onInstallFailed(installState);
            }
            MLogger.e(TAG, "The file path of apk is null or empty!");
        }
        return result;
    }

    /**
     * 安装指定路径下的apk
     * <p>
     * 注意：设备没有root
     * 部分机型存在问题：小米8和华为Mate9
     * </p>
     *
     * @param file
     */
    private void installApkWitNoRoot(File file) {

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
        if (!isAccessibilitySettingsOn(mContext)) {
            toAccessibilityService();
        }
    }

    /**
     * 跳转到辅助功能设置页：进行设置
     */
    private void toAccessibilityService() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mContext.startActivity(intent);
        if (mStateListener != null) {
            mStateListener.onAccessibilitySwitchChanged();
        }
    }

    /**
     * 是否打开辅助功能
     *
     * @param mContext
     * @return
     */
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + InstallerService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            MLogger.d(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            if (mStateListener != null) {
                mInstallState.setType(StateType.TYPE_ACCESSIBILITY_PAGE_NOT_FOUND);
                mInstallState.setMessage(StateMessage.FAILED_SETTING_ACCESSIBILITY);
                mStateListener.onInstallFailed(mInstallState);
            }
            MLogger.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            MLogger.d(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    MLogger.d(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        MLogger.d(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            MLogger.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }
}
