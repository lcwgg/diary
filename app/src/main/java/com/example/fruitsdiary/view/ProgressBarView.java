package com.example.fruitsdiary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.fruitsdiary.R;

public class ProgressBarView extends FrameLayout {

    private ProgressBar mProgressBar;

    public ProgressBarView(Context context) {
        super(context);
        init(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view =
                LayoutInflater.from(context).inflate(R.layout.view_progress_bar, this, true);
        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    public void show(){
        mProgressBar.setVisibility(VISIBLE);
    }

    public void hide(){
        mProgressBar.setVisibility(GONE);
    }

}
