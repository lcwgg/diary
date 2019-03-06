package com.example.fruitsdiary.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.fruitsdiary.util.StringUtils.EMPTY_STRING;

public class BaseDialogFragment extends DialogFragment {

    protected static final String ARGS_TITLE ="ARGS_TITLE";
    protected static final String ARGS_MESSAGE ="ARGS_MESSAGE";
    protected static final String ARGS_CANCEL_BUTTON ="ARGS_CANCEL_BUTTON";

    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    private String mTitle;
    private String mMessage;
    private boolean mHasCancelButton;
    private OnButtonClickListener mOnButtonClickListener;

    static BaseDialogFragment newInstance(String title, String message, boolean hasCancelButton){
        BaseDialogFragment fragment = new BaseDialogFragment();
        Bundle args = new Bundle(3);
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        args.putBoolean(ARGS_CANCEL_BUTTON, hasCancelButton);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mTitle = args.getString(ARGS_TITLE);
        mMessage = args.getString(ARGS_MESSAGE);
        mHasCancelButton = args.getBoolean(ARGS_CANCEL_BUTTON);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnButtonClickListener != null) {
                            mOnButtonClickListener.onPositiveClick();
                        }
                        dismiss();
                    }
                });
        if (mHasCancelButton){
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnButtonClickListener != null) {
                            mOnButtonClickListener.onNegativeClick();
                        }
                        dismiss();
                    }
                });

        }
        return builder.create();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    public void show(FragmentManager manager){
        show(manager, getDialogTag());
    }

    protected String getDialogTag() {
        return TAG;
    }

    public static class Builder {
        String title;
        String message;
        boolean addCancelButton;
        OnButtonClickListener onButtonClickListener;

        public Builder() {
            title = EMPTY_STRING;
            message = EMPTY_STRING;
            addCancelButton = false;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        public Builder addCancelButton(boolean addCancelButton){
            this.addCancelButton = addCancelButton;
            return this;
        }

        public Builder setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
            this.onButtonClickListener = onButtonClickListener;
            return this;
        }

        public BaseDialogFragment build(){
            BaseDialogFragment fragment = newInstance(
                    title, message, addCancelButton
            );
            if (onButtonClickListener != null){
                fragment.setOnButtonClickListener(onButtonClickListener);
            }
            return fragment;
        }
    }

    public interface OnButtonClickListener {
        void onPositiveClick();
        void onNegativeClick();
    }
}
