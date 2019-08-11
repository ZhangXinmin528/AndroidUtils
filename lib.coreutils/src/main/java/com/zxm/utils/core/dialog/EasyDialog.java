package com.zxm.utils.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zxm.utils.core.R;
import com.zxm.utils.core.screen.ScreenUtil;


/**
 * Created by ZhangXinmin on 2018/8/7.
 * Copyright (c) 2018 . All rights reserved.
 * EasyDialog is a convenient form of Dialog.
 */
public class EasyDialog extends Dialog {

    private static final String TAG = EasyDialog.class.getSimpleName();

    public EasyDialog(Context context) {
        super(context);
    }

    /**
     * Creates an common alert dialog that uses an explicit theme resource.
     *
     * @param context    the parent context
     * @param themeResId the resource ID of the theme against which to inflate
     *                   this dialog
     */
    public EasyDialog(@NonNull Context context, @StyleRes int themeResId) {
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
        void onClick(EasyDialog dialog);
    }

    /**
     * The Builder for Common Dialog.
     */
    public static final class Builder {

        private final DialogContrller P;

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
            P = new DialogContrller(context, themeResId, layoutResID);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId, @NonNull View rootView) {
            P = new DialogContrller(context, themeResId, rootView);
        }

        /**
         * Set the screen content from a layout resource.  The resource will be
         * inflated, adding all top-level views to the screen.
         *
         * @param layoutResID Resource ID to be inflated.
         */
        public Builder setContentView(@LayoutRes int layoutResID) {
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
        public Builder setContentView(@NonNull View view) {
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
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Set the message to display.
         *
         * @param title
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@Nullable CharSequence title, @IdRes int viewId) {
            P.mTilte = title;
            P.mTitleId = viewId;
            return this;
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @param titleId
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@StringRes int titleId, @IdRes int viewId) {
            P.mTilte = P.mContext.getText(titleId);
            P.mTitleId = viewId;
            return this;
        }

        /**
         * Set the message to display.
         *
         * @param message
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@Nullable CharSequence message, @IdRes int viewId) {
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
        public Builder setMessage(@StringRes int messageId, @IdRes int viewId) {
            P.mMessage = P.mContext.getText(messageId);
            P.mMessageId = viewId;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the positive button
         * @param listener The {@link OnClickListener} to use.
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(@StringRes int textId, @IdRes int viewId, final OnClickListener listener) {
            P.mPositiveButtonText = P.mContext.getText(textId);
            P.mPositiveButtonListener = listener;
            P.mPositiveBtnId = viewId;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param text     The text to display in the positive button
         * @param listener The {@link OnClickListener} to use.
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(CharSequence text, @IdRes int viewId, final OnClickListener listener) {
            P.mPositiveButtonText = text;
            P.mPositiveButtonListener = listener;
            P.mPositiveBtnId = viewId;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the negative button
         * @param listener The {@link OnClickListener} to use.
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(@StringRes int textId, @IdRes int viewId, final OnClickListener listener) {
            P.mNegativeButtonText = P.mContext.getText(textId);
            P.mNegativeButtonListener = listener;
            P.mNegativeBtnId = viewId;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param text     The text to display in the negative button
         * @param listener The {@link OnClickListener} to use.
         * @param viewId
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(CharSequence text, @IdRes int viewId, final OnClickListener listener) {
            P.mNegativeButtonText = text;
            P.mNegativeButtonListener = listener;
            P.mNegativeBtnId = viewId;
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
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        @Deprecated
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * Creates an {@link EasyDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public EasyDialog create() {
            final EasyDialog dialog = new EasyDialog(P.mContext, P.mTheme);
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
        public EasyDialog showDialog() {
            final EasyDialog dialog = create();
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

        private void setUpView(@NonNull final EasyDialog dialog) {
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

            //title
            if (!TextUtils.isEmpty(P.mTilte) && P.mTitleId != 0) {
                final TextView titleView = P.mRootView.findViewById(P.mTitleId);
                titleView.setText(P.mTilte);
            }
            //Message
            if (!TextUtils.isEmpty(P.mMessage) && P.mMessageId != 0) {
                final TextView msgView = P.mRootView.findViewById(P.mMessageId);
                msgView.setText(P.mMessage);
            }
            //PositiveButton
            if (!TextUtils.isEmpty(P.mPositiveButtonText)) {
                final TextView positiveBtn = P.mRootView.findViewById(P.mPositiveBtnId);
                positiveBtn.setText(P.mPositiveButtonText);

                if (P.mPositiveButtonListener != null) {
                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            P.mPositiveButtonListener.onClick(dialog);
                            dialog.dismiss();
                        }
                    });
                }
            }

            //NegativeButton
            if (!TextUtils.isEmpty(P.mNegativeButtonText)) {
                final TextView negativeBtn = P.mRootView.findViewById(P.mNegativeBtnId);
                negativeBtn.setText(P.mNegativeButtonText);

                if (P.mNegativeButtonListener != null) {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            P.mNegativeButtonListener.onClick(dialog);
                            dialog.dismiss();
                        }
                    });
                }
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
        //title
        public CharSequence mTilte;
        public int mTitleId;
        //message
        public CharSequence mMessage;
        public int mMessageId;
        //positive button
        public CharSequence mPositiveButtonText;
        public int mPositiveBtnId;
        public OnClickListener mPositiveButtonListener;
        //negative button
        public CharSequence mNegativeButtonText;
        public int mNegativeBtnId;
        public OnClickListener mNegativeButtonListener;
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
