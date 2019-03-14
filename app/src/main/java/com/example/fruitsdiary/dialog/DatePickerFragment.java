package com.example.fruitsdiary.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = DatePickerFragment.class.getSimpleName();

    private static final String ARGS_YEAR = "ARGS_YEAR";
    private static final String ARGS_MONTH = "ARGS_MONTH";
    private static final String ARGS_DAY = "ARGS_DAY";
    private static final String ARGS_NO_DATE = "ARGS_NO_DATE";

    private OnDateSelectedListener mOnDateSelectedListener;

    public static DatePickerFragment newInstance(int year, int month, int day){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_YEAR, year);
        args.putInt(ARGS_MONTH, month);
        args.putInt(ARGS_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    public static DatePickerFragment newInstance(){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS_NO_DATE, true);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        mOnDateSelectedListener = onDateSelectedListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year;
        int month;
        int day;
        Bundle args = getArguments();
        if (args.getBoolean(ARGS_NO_DATE)) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            year = args.getInt(ARGS_YEAR);
            month = args.getInt(ARGS_MONTH);
            day = args.getInt(ARGS_DAY);
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (mOnDateSelectedListener != null){
            mOnDateSelectedListener.onDateSelected(year, month, day);
        }
    }

    public void show(FragmentManager manager){
        show(manager, TAG);
    }

    public interface OnDateSelectedListener{
        void onDateSelected(int year, int month, int day);
    }
}
