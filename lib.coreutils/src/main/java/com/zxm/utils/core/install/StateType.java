package com.zxm.utils.core.install;

/**
 * Created by ZhangXinmin on 2018/8/24.
 * Copyright (c) 2018 . All rights reserved.
 */
public class StateType {

    /**
     * Apk file path is invalid.{@link StateMessage#INVALID_FILE_PATH}
     */
    public static final int TYPE_INVALID_FILE_PATH = 1001;

    //null
    /**
     * an application attempts to use {@code null} in a
     * case where an object is required.{@link NullPointerException}
     */
    public static final int TYPE_NULL_REFERENCE = 2001;

    //io
    /**
     * failed or interrupted I/O operations{@link java.io.IOException}
     */
    public static final int TYPE_IO_OPERATION = 3001;

    //Thread
    /**
     * The current thread has been interrupted{@link InterruptedException}
     */
    public static final int TYPE_THREAD_INTERRUPTED = 4001;

    //accessibility
    /**
     * The switch of accessibility in setting page was not found.
     */
    public static final int TYPE_ACCESSIBILITY_PAGE_NOT_FOUND = 5001;
}
