package com.example.fruitsdiary.exception;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorFactory {

    private static final String MESSAGE_FIELD_NAME = "message";

    public static FruitDiaryException createFromThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            ResponseBody responseBody = ((HttpException)throwable).response().errorBody();
            String errorMessage = getErrorMessage(responseBody);
            return new ErrorCommonException(errorMessage);
        } else if (throwable instanceof SocketTimeoutException) {
            return new ErrorTimeoutException();
        } else if (throwable instanceof IOException) {
            return new NoInternetConnectionException();
        } else {
            return new ErrorUnknownException();
        }
    }

    private static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString(MESSAGE_FIELD_NAME);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
