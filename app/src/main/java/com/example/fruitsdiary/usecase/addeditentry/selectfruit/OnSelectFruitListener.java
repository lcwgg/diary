package com.example.fruitsdiary.usecase.addeditentry.selectfruit;

import android.support.annotation.Nullable;

import com.example.fruitsdiary.model.FruitEntry;

public interface OnSelectFruitListener {
    void onFruitSelected(@Nullable FruitEntry fruitEntry);
}
