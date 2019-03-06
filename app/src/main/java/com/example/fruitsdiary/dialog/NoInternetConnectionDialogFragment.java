package com.example.fruitsdiary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.fruitsdiary.R;

public class NoInternetConnectionDialogFragment extends BaseDialogFragment {

    private static final String TAG = NoInternetConnectionDialogFragment.class.getSimpleName();

    public static NoInternetConnectionDialogFragment newInstance(String title, String message){
        NoInternetConnectionDialogFragment fragment = new NoInternetConnectionDialogFragment();
        Bundle args = new Bundle(2);
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected String getDialogTag() {
        return TAG;
    }

    public static class NoInternetConnectionBuilder extends Builder {

        private Context context;

        public NoInternetConnectionBuilder(Context context) {
            this.context = context;
        }

        @Override
        public NoInternetConnectionDialogFragment build() {
            NoInternetConnectionDialogFragment dialog = newInstance(
                    context.getString(R.string.no_internet_dialog_title),
                    context.getString(R.string.no_internet_dialog_message));
            return dialog;
        }
    }
}
