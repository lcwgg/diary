package com.example.fruitsdiary.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val SERVER_DATE_PATTERN = "yyyy-MM-dd"

    private val SERVER_DATE_FORMAT = SimpleDateFormat(SERVER_DATE_PATTERN)

    private val APP_DATE_FORMAT = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

    @JvmStatic val currentAppDate: String
        get() {
            val date = currentDate
            return getAppStringDate(date)
        }

    @JvmStatic val currentServerDate: String
        get() {
            val date = currentDate
            return SERVER_DATE_FORMAT.format(date)

        }

    private val currentDate: Date
        get() = Calendar.getInstance().time

    @JvmStatic fun convertServerDateToAppDate(serverDate: String): String {
        val date = getServerDate(serverDate)
        return getAppStringDate(date)
    }

    private fun getServerDate(serverDate: String): Date {
        try {
            return SERVER_DATE_FORMAT.parse(serverDate)
        } catch (e: ParseException) {
            return Date()
        }

    }

    private fun getAppStringDate(date: Date): String {
        return APP_DATE_FORMAT.format(date)
    }
}
