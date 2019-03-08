package com.example.fruitsdiary.exception;

import com.example.fruitsdiary.R;

public class NoInternetConnectionException extends FruitDiaryException {

    public NoInternetConnectionException() {
        setErrorMessage(R.string.no_internet_dialog_message);
        setTitle(R.string.no_internet_dialog_title);
    }
}
