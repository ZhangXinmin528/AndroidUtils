package com.zxm.utils.core.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2018/12/20.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class DialogUtil {

    private static List<AlertDialog> DIALOG_ARRAY = new ArrayList<>();

    /**
     * Show message dialog which con't cancelable {@link AlertDialog #setCancelable(cancelable)}.
     *
     * @param context
     * @param message
     * @param listener
     */
    public static void showForceDialog(@NonNull Context context, @NonNull String message,
                                       @NonNull DialogInterface.OnClickListener listener) {

        showDialog(context, message, false, listener);
    }

    /**
     * show message dialog
     *
     * @param context
     * @param message
     */
    public static void showDialog(@NonNull Context context, @NonNull String message,
                                  @NonNull boolean cancelable, @NonNull DialogInterface.OnClickListener listener) {

        if (context == null || TextUtils.isEmpty(message) || listener == null)
            return;
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(android.R.string.ok, listener)
                .setMessage(message)
                .setCancelable(cancelable)
                .create();
        dialog.show();

        if (DIALOG_ARRAY.contains(dialog)) {
            DIALOG_ARRAY.add(dialog);
        }
    }

    /**
     * show message dialog
     *
     * @param context
     * @param message
     */
    public static void showDialog(@NonNull Context context, @NonNull String message,
                                  @NonNull boolean cancelable,
                                  @NonNull DialogInterface.OnClickListener positiveListener,
                                  @NonNull DialogInterface.OnClickListener negativeListener) {

        if (context == null || TextUtils.isEmpty(message) || positiveListener == null)
            return;
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(android.R.string.ok, positiveListener)
                .setNegativeButton(android.R.string.cancel,negativeListener)
                .setMessage(message)
                .setCancelable(cancelable)
                .create();
        dialog.show();

        if (DIALOG_ARRAY.contains(dialog)) {
            DIALOG_ARRAY.add(dialog);
        }
    }

    /**
     * dismiss dialog
     */
    public static void releaseAll() {
        if (!DIALOG_ARRAY.isEmpty()) {
            for (AlertDialog dialog : DIALOG_ARRAY) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    }
}
