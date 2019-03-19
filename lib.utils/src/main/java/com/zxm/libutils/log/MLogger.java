package com.zxm.libutils.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by ZhangXinmin on 2018/5/24.
 * Copyright (c) 2018 . All rights reserved.
 * android log utils
 */
public final class MLogger {

    //log level
    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;
    public static final int A = Log.ASSERT;

    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};

    //log type
    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML = 0x30;

    //separator
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");

    //border
    private static final String TOP_CORNER = "┌";
    private static final String MIDDLE_CORNER = "├";
    private static final String LEFT_BORDER = "│ ";
    private static final String BOTTOM_CORNER = "└";
    private static final String SIDE_DIVIDER =
            "────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER =
            "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;

    private static final int MAX_LEN = 3000;

    //format
    private static final Format FORMAT =
            new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.CHINA);

    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String ARGS = "args";
    private static final String PLACEHOLDER = " ";

    private static ConfigBuilder CONFIG_BUILDER;

    private static ExecutorService sExecutor;

    private MLogger() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Get log config
     *
     * @return {@link ConfigBuilder}
     */
    public static ConfigBuilder getLogConfig() {
        return CONFIG_BUILDER;
    }

    /**
     * Set log config
     *
     * @param builder {@link ConfigBuilder}
     */
    public static void resetLogConfig(@NonNull ConfigBuilder builder) {
        CONFIG_BUILDER = builder;
    }

    public static void v(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(V, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void vTag(final String tag, final Object... contents) {
        log(V, tag, contents);
    }

    public static void d(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(D, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void dTag(final String tag, final Object... contents) {
        log(D, tag, contents);
    }

    public static void i(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(I, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void iTag(final String tag, final Object... contents) {
        log(I, tag, contents);
    }

    public static void w(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(W, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void wTag(final String tag, final Object... contents) {
        log(W, tag, contents);
    }

    public static void e(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(E, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void eTag(final String tag, final Object... contents) {
        log(E, tag, contents);
    }

    public static void a(final Object... contents) {
        if (CONFIG_BUILDER != null) {
            log(A, CONFIG_BUILDER.mGlobalTag, contents);
        }
    }

    public static void aTag(final String tag, final Object... contents) {
        log(A, tag, contents);
    }

    public static void file(final Object content) {
        if (CONFIG_BUILDER != null)
            log(FILE | D, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void file(@TYPE final int type, final Object content) {
        if (CONFIG_BUILDER != null)
            log(FILE | type, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void file(final String tag, final Object content) {
        log(FILE | D, tag, content);
    }

    public static void file(@TYPE final int type, final String tag, final Object content) {
        log(FILE | type, tag, content);
    }

    public static void json(final String content) {
        if (CONFIG_BUILDER != null)
            log(JSON | D, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void json(@TYPE final int type, final String content) {
        if (CONFIG_BUILDER != null)
            log(JSON | type, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void json(final String tag, final String content) {
        log(JSON | D, tag, content);
    }

    public static void json(@TYPE final int type, final String tag, final String content) {
        log(JSON | type, tag, content);
    }

    public static void xml(final String content) {
        if (CONFIG_BUILDER != null)
            log(XML | D, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void xml(@TYPE final int type, final String content) {
        if (CONFIG_BUILDER != null)
            log(XML | type, CONFIG_BUILDER.mGlobalTag, content);
    }

    public static void xml(final String tag, final String content) {
        log(XML | D, tag, content);
    }

    public static void xml(@TYPE final int type, final String tag, final String content) {
        log(XML | type, tag, content);
    }

    /**
     * log
     *
     * @param type     log level,maybe one of{@link #V,#D,#I,#W,#E,#A}
     * @param tag      log tag
     * @param contents information to print
     */
    public static void log(final int type, final String tag, final Object... contents) {
        if (CONFIG_BUILDER == null)
            return;
        if (!CONFIG_BUILDER.mLogSwitch || (!CONFIG_BUILDER.mLog2ConsoleSwitch && !CONFIG_BUILDER.mLog2FileSwitch))
            return;

        final int type_low = type & 0x0f, type_high = type & 0xf0;

        if (type_low < CONFIG_BUILDER.mConsoleFilter && type_low < CONFIG_BUILDER.mFileFilter)
            return;

        final TagHead tagHead = processTagAndHead(tag);

        final String body = processBody(type_high, contents);
        if (CONFIG_BUILDER.mLog2ConsoleSwitch && type_low >= CONFIG_BUILDER.mConsoleFilter && type_high != FILE) {
            print2Console(type_low, tagHead.tag, tagHead.consoleHead, body);
        }
        if ((CONFIG_BUILDER.mLog2FileSwitch && type_high == FILE) && type_low >= CONFIG_BUILDER.mFileFilter) {
            print2File(type_low, tagHead.tag, tagHead.fileHead + body);
        }
    }

    /**
     * process tag and head
     *
     * @param tag log tag
     * @return TagHead
     */
    private static TagHead processTagAndHead(String tag) {

        if (!CONFIG_BUILDER.mTagIsSpace && !CONFIG_BUILDER.mLogHeadSwitch) {
            tag = CONFIG_BUILDER.mGlobalTag;
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            final int stackIndex = 3 + CONFIG_BUILDER.mStackOffset;
            if (stackIndex >= stackTrace.length) {
                StackTraceElement targetElement = stackTrace[3];
                final String fileName = getFileName(targetElement);
                if (CONFIG_BUILDER.mTagIsSpace && isSpace(tag)) {
                    int index = fileName.indexOf('.');// Use proguard may not find '.'.
                    tag = index == -1 ? fileName : fileName.substring(0, index);
                }
                return new TagHead(tag, null, ": ");
            }
            StackTraceElement targetElement = stackTrace[stackIndex];
            final String fileName = getFileName(targetElement);
            if (CONFIG_BUILDER.mTagIsSpace && isSpace(tag)) {
                int index = fileName.indexOf('.');// Use proguard may not find '.'.
                tag = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (CONFIG_BUILDER.mLogHeadSwitch) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter()
                        .format("%s, %s.%s(%s:%d)",
                                tName,
                                targetElement.getClassName(),
                                targetElement.getMethodName(),
                                fileName,
                                targetElement.getLineNumber())
                        .toString();
                final String fileHead = " [" + head + "]: ";
                if (CONFIG_BUILDER.mStackDeep <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead =
                            new String[Math.min(
                                    CONFIG_BUILDER.mStackDeep,
                                    stackTrace.length - stackIndex
                            )];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + stackIndex];
                        consoleHead[i] = new Formatter()
                                .format("%s%s.%s(%s:%d)",
                                        space,
                                        targetElement.getClassName(),
                                        targetElement.getMethodName(),
                                        getFileName(targetElement),
                                        targetElement.getLineNumber())
                                .toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String getFileName(final StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) return fileName;
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf('$');
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String processBody(final int type, final Object... contents) {
        String body = NULL;
        if (contents != null) {
            if (contents.length == 1) {
                Object object = contents[0];
                if (object != null) body = object.toString();
                if (type == JSON) {
                    body = formatJson(body);
                } else if (type == XML) {
                    body = formatXml(body);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(ARGS)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(content == null ? NULL : content.toString())
                            .append(LINE_SEP);
                }
                body = sb.toString();
            }
        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static void print2Console(final int type,
                                      final String tag,
                                      final String[] head,
                                      final String msg) {
        if (CONFIG_BUILDER.mSingleTagSwitch) {
            StringBuilder sb = new StringBuilder();
            sb.append(PLACEHOLDER).append(LINE_SEP);
            if (CONFIG_BUILDER.mLogBorderSwitch) {
                sb.append(TOP_BORDER).append(LINE_SEP);
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(LEFT_BORDER).append(aHead).append(LINE_SEP);
                    }
                    sb.append(MIDDLE_BORDER).append(LINE_SEP);
                }
                for (String line : msg.split(LINE_SEP)) {
                    sb.append(LEFT_BORDER).append(line).append(LINE_SEP);
                }
                sb.append(BOTTOM_BORDER);
            } else {
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(aHead).append(LINE_SEP);
                    }
                }
                sb.append(msg);
            }
            printMsgSingleTag(type, tag, sb.toString());
        } else {
            printBorder(type, tag, true);
            printHead(type, tag, head);
            printMsg(type, tag, msg);
            printBorder(type, tag, false);
        }
    }

    /**
     * print log message border
     *
     * @param type  log level,maybe one of{@link #V,#D,#I,#W,#E,#A}
     * @param tag   log tag
     * @param isTop true or false
     */
    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (CONFIG_BUILDER.mLogBorderSwitch) {
            Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    /**
     * print log message header
     *
     * @param type log level,maybe one of{@link #V,#D,#I,#W,#E,#A}
     * @param tag  log tag
     * @param head log message head
     */
    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                Log.println(type, tag, CONFIG_BUILDER.mLogBorderSwitch ? LEFT_BORDER + aHead : aHead);
            }
            if (CONFIG_BUILDER.mLogBorderSwitch)
                Log.println(type, tag, MIDDLE_BORDER);
        }
    }

    /**
     * print log message body
     *
     * @param type log level,maybe one of{@link #V,#D,#I,#W,#E,#A}
     * @param tag  log tag
     * @param msg  log message body
     */
    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printMsgSingleTag(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            if (CONFIG_BUILDER.mLogBorderSwitch) {
                Log.println(type, tag, msg.substring(0, MAX_LEN) + LINE_SEP + BOTTOM_BORDER);
                int index = MAX_LEN;
                for (int i = 1; i < countOfSub; i++) {
                    Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, index + MAX_LEN)
                            + LINE_SEP + BOTTOM_BORDER);
                    index += MAX_LEN;
                }
                if (index != len) {
                    Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, len));
                }
            } else {
                int index = 0;
                for (int i = 0; i < countOfSub; i++) {
                    Log.println(type, tag, msg.substring(index, index + MAX_LEN));
                    index += MAX_LEN;
                }
                if (index != len) {
                    Log.println(type, tag, msg.substring(index, len));
                }
            }
        } else {
            Log.println(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!CONFIG_BUILDER.mLogBorderSwitch) {
            Log.println(type, tag, msg);
            return;
        }
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void print2File(final int type, final String tag, final String msg) {
        final Date now = new Date(System.currentTimeMillis());
        final String format = FORMAT.format(now);
        final String date = format.substring(0, 5);
        final String time = format.substring(6);
        final String fullPath =
                (TextUtils.isEmpty(CONFIG_BUILDER.mDir) ? CONFIG_BUILDER.mDefaultDir : CONFIG_BUILDER.mDir)
                        + CONFIG_BUILDER.mFilePrefix + "-" + date + ".txt";

        if (!createOrExistsFile(fullPath)) {
            return;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append(T[type - V])
                .append("/")
                .append(tag)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        input2File(content, fullPath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) printDeviceInfo(filePath);
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = CONFIG_BUILDER.mContext
                    .getPackageManager()
                    .getPackageInfo(CONFIG_BUILDER.mContext.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String time = filePath.substring(filePath.length() - 9, filePath.length() - 4);
        final String head = "************* Log Head ****************" +
                "\nDate of Log        : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        final Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            if (submit.get()) return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("MLogger", "log to " + filePath + " failed!");
    }

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    /**
     * ConfigBuilder class
     */
    public static class ConfigBuilder {
        private Context mContext;
        // The default storage directory of log.
        private String mDefaultDir;
        // The storage directory of log.
        private String mDir;
        // The file prefix of log.
        private String mFilePrefix = "log";
        // The switch of log.
        private boolean mLogSwitch = true;
        // The switch of logcat to print info in console.
        private boolean mLog2ConsoleSwitch = true;
        // The global tag of log.
        private String mGlobalTag = null;
        // The global tag is space.
        private boolean mTagIsSpace = true;
        // The head's switch of log.
        private boolean mLogHeadSwitch = true;
        // The file's switch of log.
        private boolean mLog2FileSwitch = false;
        // The border's switch of log.
        private boolean mLogBorderSwitch = true;
        // The single tag of log.
        private boolean mSingleTagSwitch = true;
        // The console's filter of log.
        private int mConsoleFilter = V;
        // The file's filter of log.
        private int mFileFilter = V;
        // The stack's deep of log.
        private int mStackDeep = 1;
        // The stack's offset of log.
        private int mStackOffset = 0;

        /**
         * ConfigBuilder Constructor
         *
         * @param context You'd better use application context.
         */
        public ConfigBuilder(@NonNull Context context) {
            if (context == null || mDefaultDir != null) {
                return;
            }

            mContext = context;

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && context.getExternalCacheDir() != null) {
                mDefaultDir = context.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            } else {
                mDefaultDir = context.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        /**
         * The switch of log.
         *
         * @param logSwitch true or false
         * @return
         */
        public ConfigBuilder setLogSwitch(@NonNull final boolean logSwitch) {
            mLogSwitch = logSwitch;
            return this;
        }

        /**
         * The switch of logcat to print info in console.
         *
         * @param consoleSwitch true or false
         * @return
         */
        public ConfigBuilder setConsoleSwitch(final boolean consoleSwitch) {
            mLog2ConsoleSwitch = consoleSwitch;
            return this;
        }

        /**
         * The global tag of log.
         *
         * @param tag
         * @return
         */
        public ConfigBuilder setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                mGlobalTag = "";
                mTagIsSpace = true;
            } else {
                mGlobalTag = tag;
                mTagIsSpace = false;
            }
            return this;
        }

        /**
         * The head's switch of log.
         *
         * @param logHeadSwitch true or false
         * @return
         */
        public ConfigBuilder setLogHeadSwitch(final boolean logHeadSwitch) {
            mLogHeadSwitch = logHeadSwitch;
            return this;
        }

        /**
         * The file's switch of log.
         *
         * @param log2FileSwitch true or false
         * @return
         */
        public ConfigBuilder setLog2FileSwitch(final boolean log2FileSwitch) {
            mLog2FileSwitch = log2FileSwitch;
            return this;
        }

        /**
         * The storage directory of log.
         *
         * @param dir
         * @return
         */
        public ConfigBuilder setDir(final String dir) {
            if (isSpace(dir)) {
                mDir = null;
            } else {
                mDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
            }
            return this;
        }

        /**
         * The storage directory of log.
         *
         * @param dir
         * @return
         */
        public ConfigBuilder setDir(final File dir) {
            mDir = dir == null ? null : dir.getAbsolutePath() + FILE_SEP;
            return this;
        }

        /**
         * The file prefix of log.
         *
         * @param filePrefix
         * @return
         */
        public ConfigBuilder setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                mFilePrefix = "util";
            } else {
                mFilePrefix = filePrefix;
            }
            return this;
        }

        /**
         * The border's switch of log.
         *
         * @param borderSwitch true or false
         * @return
         */
        public ConfigBuilder setBorderSwitch(final boolean borderSwitch) {
            mLogBorderSwitch = borderSwitch;
            return this;
        }

        /**
         * The single tag of log.
         *
         * @param singleTagSwitch true or false
         * @return
         */
        public ConfigBuilder setSingleTagSwitch(final boolean singleTagSwitch) {
            mSingleTagSwitch = singleTagSwitch;
            return this;
        }

        /**
         * The console's filter of log.
         *
         * @param consoleFilter
         * @return
         */
        public ConfigBuilder setConsoleFilter(@TYPE final int consoleFilter) {
            mConsoleFilter = consoleFilter;
            return this;
        }

        /**
         * The file's filter of log.
         *
         * @param fileFilter
         * @return
         */
        public ConfigBuilder setFileFilter(@TYPE final int fileFilter) {
            mFileFilter = fileFilter;
            return this;
        }

        /**
         * The stack's deep of log.
         *
         * @param stackDeep
         * @return
         */
        public ConfigBuilder setStackDeep(@IntRange(from = 1) final int stackDeep) {
            mStackDeep = stackDeep;
            return this;
        }

        /**
         * The stack's offset of log.
         *
         * @param stackOffset
         * @return
         */
        public ConfigBuilder setStackOffset(@IntRange(from = 0) final int stackOffset) {
            mStackOffset = stackOffset;
            return this;
        }

        @Override
        public String toString() {
            return "log enable: " + mLogSwitch
                    + LINE_SEP + "console: " + mLog2ConsoleSwitch
                    + LINE_SEP + "tag: " + (mTagIsSpace ? "null" : mGlobalTag)
                    + LINE_SEP + "head: " + mLogHeadSwitch
                    + LINE_SEP + "file: " + mLog2FileSwitch
                    + LINE_SEP + "dir: " + (mDir == null ? mDefaultDir : mDir)
                    + LINE_SEP + "filePrefix: " + mFilePrefix
                    + LINE_SEP + "border: " + mLogBorderSwitch
                    + LINE_SEP + "singleTag: " + mSingleTagSwitch
                    + LINE_SEP + "consoleFilter: " + T[mConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[mFileFilter - V]
                    + LINE_SEP + "stackDeep: " + mStackDeep
                    + LINE_SEP + "mStackOffset: " + mStackOffset;
        }
    }

    private static class TagHead {
        String tag;
        String[] consoleHead;
        String fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }
}
