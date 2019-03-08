package com.example.fruitsdiary.exception;

import android.support.annotation.StringRes;

import com.example.fruitsdiary.R;

public class ErrorCommonException extends FruitDiaryException {

    public ErrorCommonException(String message) {
        super(message);
        setTitle(R.string.error_title);
    }
}
