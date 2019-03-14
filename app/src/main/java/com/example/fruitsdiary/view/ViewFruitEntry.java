package com.example.fruitsdiary.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.ViewFruitEntryBinding;

public class ViewFruitEntry extends CardView {

    private ViewFruitEntryBinding mBinding;

    public ViewFruitEntry(@NonNull Context context) {
        super(context);
    }

    public ViewFruitEntry(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewFruitEntry(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.view_fruit_entry,
                this, true
        );
        int cardElevation = context.getResources().getDimensionPixelSize(R.dimen.card_elevation);
        setCardElevation(cardElevation);
    }
}
