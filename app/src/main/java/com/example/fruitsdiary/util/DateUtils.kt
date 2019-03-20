package com.example.fruitsdiary.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val SERVER_DATE_PATTERN = "yyyy-MM-dd"

    private val SERVER_DATE_FORMAT = SimpleDateFormat(SERVER_DATE_PATTERN)

    private val APP_DATE_FORMAT = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

     @JvmStatic fun getCurrentAppDate(): String {
            val date = getCurrentDate()
            return getAppStringDate(date)
        }

    @JvmStatic fun getCurrentServerDate(): String {
            val date = getCurrentDate()
            return SERVER_DATE_FORMAT.format(date)
        }

    private fun getCurrentDate(): Date  = Calendar.getInstance().time

    @JvmStatic fun convertServerDateToAppDate(serverDate: String): String {
        val date = getServerDate(serverDate)
        return getAppStringDate(date)
    }

    private fun getServerDate(serverDate: String): Date {
        return try {
            SERVER_DATE_FORMAT.parse(serverDate)
        } catch (e: ParseException) {
            Date()
        }
    }

    private fun getAppStringDate(date: Date): String {
        return APP_DATE_FORMAT.format(date)
    }
}
