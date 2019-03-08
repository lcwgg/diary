package com.example.fruitsdiary.exception;

import com.example.fruitsdiary.R;

public class ErrorUnknownException extends FruitDiaryException {

    public ErrorUnknownException() {
        super();
        mMessage = R.string.error_unknown_message;
    }
}
