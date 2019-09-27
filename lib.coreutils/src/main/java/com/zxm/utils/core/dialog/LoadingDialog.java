package com.zxm.utils.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.zxm.utils.core.R;
import com.zxm.utils.core.screen.ScreenUtil;

/**
 * Created by ZhangXinmin on 2019/7/22.
 * Copyright (c) 2019 . All rights reserved.
 */
public class LoadingDialog extends Dialog {

    private static final String TAG = LoadingDialog.class.getSimpleName();

    public LoadingDialog(Context context) {
        super(context);
    }

    /**
     * Creates an common alert dialog that uses an explicit theme resource.
     *
     * @param context    the parent context
     * @param themeResId the resource ID of the theme against which to inflate
     *                   this dialog
     */
    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item on the dialog is clicked.
     */
    public interface OnClickListener {
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         * @param dialog the dialog that received the click
         */
        void onClick(LoadingDialog dialog);
    }

    /**
     * The Builder for Common Dialog.
     */
    public static final class Builder {

        private final LoadingDialog.DialogContrller P;

        public Builder(@NonNull Context context) {
            this(context, -1);
        }

        public Builder(@NonNull Context context, @NonNull View rootView) {
            this(context, R.style.Theme_Style_Dialog, rootView);
        }

        public Builder(@NonNull Context context, @LayoutRes int layoutResID) {
            this(context, R.style.Theme_Style_Dialog, layoutResID);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId, @LayoutRes int layoutResID) {
            P = new LoadingDialog.DialogContrller(context, themeResId, layoutResID);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId, @NonNull View rootView) {
            P = new LoadingDialog.DialogContrller(context, themeResId, rootView);
        }

        /**
         * Set the screen content from a layout resource.  The resource will be
         * inflated, adding all top-level views to the screen.
         *
         * @param layoutResID Resource ID to be inflated.
         */
        public LoadingDialog.Builder setContentView(@LayoutRes int layoutResID) {
            P.layoutResID = layoutResID;
            return this;
        }

        /**
         * Set the screen content to an explicit view.  This view is placed
         * directly into the screen's view hierarchy.  It can itself be a complex
         * view hierarchy.
         *
         * @param view The desired content to display.
         */
        public LoadingDialog.Builder setContentView(@NonNull View view) {
            P.mRootView = view;
            return this;
        }

        /**
         * Set the width for this dialog.After use this method,the dialog will update the layoutParams
         * {@link WindowManager.LayoutParams}.
         *
         * @param width The desired width in dp to display.
         * @return
         */
        public Builder setWidth(@IntRange(from = 0) int width) {
            P.mWidth = ScreenUtil.dp2px(P.mContext, width);
            return this;
        }


        /**
         * Set the height for this dialog.After use this method,the dialog will update the layoutParams
         * {@link WindowManager.LayoutParams}.
         *
         * @param height The desired height in dp to display.
         * @return
         */
        public Builder setHeight(@IntRange(from = 0) int height) {
            P.mHeight = ScreenUtil.dp2px(P.mContext, height);
            return this;
        }

        /**
         * Set the gravity for this dialog.After use this method,the dialog will update the layoutParams
         * {@link WindowManager.LayoutParams}.
         *
         * @param gravity Placement of window within the screen as per {@link Gravity}.
         * @return
         */
        public Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public LoadingDialog.Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Set the message to display.
         *
         * @param message
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public LoadingDialog.Builder setMessage(@Nullable CharSequence message, @IdRes int viewId) {
            P.mMessage = message;
            P.mMessageId = viewId;
            return this;
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @param messageId
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public LoadingDialog.Builder setMessage(@StringRes int messageId, @IdRes int viewId) {
            P.mMessage = P.mContext.getText(messageId);
            P.mMessageId = viewId;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         * <p>
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(OnDismissListener)
         * setOnDismissListener}.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(OnDismissListener)
         */
        @Deprecated
        public LoadingDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public LoadingDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        @Deprecated
        public LoadingDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Creates an {@link LoadingDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public LoadingDialog create() {
            final LoadingDialog dialog = new LoadingDialog(P.mContext, P.mTheme);
            setUpView(dialog);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Start the dialog and display it on screen.The window is placed in the
         * application layer and opaque.
         */
        public LoadingDialog showDialog() {
            final LoadingDialog dialog = create();
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            if (window != null) {
                lp.copyFrom(window.getAttributes());
                lp.width = P.mWidth;
                lp.height = P.mHeight;
                if (P.mGravity == 0) {
                    lp.gravity = Gravity.CENTER;
                } else {
                    lp.gravity = P.mGravity;
                }
                window.setAttributes(lp);
            }
            return dialog;
        }

        private void setUpView(@NonNull final LoadingDialog dialog) {
            if (P.mRootView == null) {
                if (P.layoutResID != 0) {
                    P.mRootView = LayoutInflater.from(P.mContext)
                            .inflate(P.layoutResID, null);
                }
            }
            if (P.mRootView == null) {
                dialog.cancel();
                return;
            }
            //Message
            if (!TextUtils.isEmpty(P.mMessage) && P.mMessageId != 0) {
                final TextView msgView = P.mRootView.findViewById(P.mMessageId);
                msgView.setText(P.mMessage);
            }

            dialog.setContentView(P.mRootView);
        }
    }

    public static class DialogContrller {
        public Context mContext;
        public int mTheme;
        public int layoutResID;
        public View mRootView;
        public int mWidth;
        public int mHeight;
        public int mGravity;
        //message
        public CharSequence mMessage;
        public int mMessageId;
        public boolean mCancelable;
        public OnCancelListener mOnCancelListener;
        public OnDismissListener mOnDismissListener;
        public OnKeyListener mOnKeyListener;

        public DialogContrller(@NonNull Context context, @StyleRes int themeResId) {
            this.mContext = context;
            this.mTheme = themeResId;
        }

        public DialogContrller(@NonNull Context mContext, @StyleRes int themeResId, @LayoutRes int layoutResID) {
            this.mContext = mContext;
            this.mTheme = themeResId;
            this.layoutResID = layoutResID;
        }

        public DialogContrller(@NonNull Context mContext, @StyleRes int themeResId, @NonNull View rootView) {
            this.mContext = mContext;
            this.mTheme = themeResId;
            this.mRootView = rootView;
        }

    }
}
