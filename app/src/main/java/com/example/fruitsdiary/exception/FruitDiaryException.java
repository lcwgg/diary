package com.example.fruitsdiary.exception;

import android.support.annotation.StringRes;

import com.example.fruitsdiary.util.StringUtils;

public abstract class FruitDiaryException extends Exception {

    @StringRes
    protected int mTitle;
    @StringRes
    protected int mMessage;

    public FruitDiaryException() {
        mTitle = StringUtils.getNoStringDefined();
        mMessage = StringUtils.getNoStringDefined();
    }

    public FruitDiaryException(@StringRes int title, @StringRes int message) {
        mTitle = title;
        mMessage = message;
    }

    public FruitDiaryException(String message) {
        super(message);
    }

    @StringRes
    public int getTitle() {
        return mTitle;
    }

    public void setTitle(@StringRes int title) {
        mTitle = title;
    }

    @StringRes
    public int getErrorMessage() {
        return mMessage;
    }

    public void setErrorMessage(@StringRes int message) {
        mMessage = message;
    }
}
