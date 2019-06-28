package com.zxm.utils.core.install;

/**
 * Created by ZhangXinmin on 2018/8/24.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class StateMessage {
    /**
     * Apk file path is invalid.
     */
    public static final String INVALID_FILE_PATH = "Apk file path is invalid.";

    /**
     * an application attempts to use {@code null} in a
     * case where an object is required.
     */
    public static final String FAILED_NULL_REFERENCE = "Apk file path is invalid.";

    /**
     * failed or interrupted I/O operations{@link java.io.IOException}
     */
    public static final String FAILED_IO_OPERATION = "IOException:";

    /**
     * The current thread has been interrupted{@link InterruptedException}
     */
    public static final String FAILED_THREAD_INTERRUPTED = "InterruptedException:";

    //accessibility
    /**
     * The switch of accessibility in setting page was not found.
     */
    public static final String FAILED_SETTING_ACCESSIBILITY =
            "The switch of accessibility in setting page was not found.";
}
