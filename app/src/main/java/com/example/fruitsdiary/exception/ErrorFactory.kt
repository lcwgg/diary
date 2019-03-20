package com.example.fruitsdiary.exception

import org.json.JSONObject

import java.io.IOException
import java.net.SocketTimeoutException

import okhttp3.ResponseBody
import retrofit2.HttpException

object ErrorFactory {

    private const val MESSAGE_FIELD_NAME = "message"

    fun createFromThrowable(throwable: Throwable): FruitDiaryException {
        return when (throwable) {
            is HttpException -> {
                val responseBody = throwable.response().errorBody()
                val errorMessage = getErrorMessage(responseBody)
                ErrorCommonException(errorMessage)
            }
            is SocketTimeoutException -> ErrorTimeoutException()
            is IOException -> NoInternetConnectionException()
            else -> ErrorUnknownException()
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            jsonObject.getString(MESSAGE_FIELD_NAME)
        } catch (e: Exception) {
            e.message ?: "Something went wrong"
        }

    }
}
