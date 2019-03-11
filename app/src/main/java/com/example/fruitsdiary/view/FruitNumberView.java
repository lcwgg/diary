package com.example.fruitsdiary.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fruitsdiary.R;

public class FruitNumberView extends ConstraintLayout {

    private TextView mFruitNumber;
    private TextView mFruitName;
    private TextView mNoFruit;

    public FruitNumberView(Context context) {
        super(context);
        init(context);
    }

    public FruitNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FruitNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view =
                LayoutInflater.from(context).inflate(R.layout.view_fruit_number, this, true);
        mFruitName = view.findViewById(R.id.fruit_name);
        mFruitNumber = view.findViewById(R.id.fruit_number);
        mNoFruit = view.findViewById(R.id.no_fruit);
    }

    public void setFruit(FruitView fruitView){
        if (fruitView.fruitNumber > 0) {
            mFruitNumber.setVisibility(VISIBLE);
            mFruitName.setVisibility(VISIBLE);
            mNoFruit.setVisibility(GONE);
            mFruitNumber.setText(String.valueOf(fruitView.fruitNumber));
            mFruitName.setText(fruitView.fruitName);
        }
    }

    public void setNoFruit(){
        mFruitNumber.setVisibility(GONE);
        mFruitName.setVisibility(GONE);
        mNoFruit.setVisibility(VISIBLE);
    }

    public static class FruitView {
        int fruitNumber;
        String fruitName;

        public FruitView(int fruitNumber, String fruitName) {
            this.fruitNumber = fruitNumber;
            this.fruitName = fruitName;
        }

    }
}
