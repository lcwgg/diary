package com.example.fruitsdiary.exception;

import com.example.fruitsdiary.R;

public class ErrorTimeoutException extends FruitDiaryException {

    public ErrorTimeoutException() {
        super();
        mMessage = R.string.error_timeout_message;
    }
}
