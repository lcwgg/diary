package com.example.fruitsdiary;

import com.example.fruitsdiary.exception.FruitDiaryException;

public interface BaseView {
    void handleNetworkError(FruitDiaryException exception);
}
