package com.example.fruitsdiary;

import com.example.fruitsdiary.exception.FruitDiaryException;

public interface BaseView {
    void handleError(FruitDiaryException exception);
}
