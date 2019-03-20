package com.example.fruitsdiary.util

import android.content.Context
import com.example.fruitsdiary.R

object StringUtils {

    const val EMPTY_STRING = ""
    const val NO_STRING_DEFINED = -1
    const val FRUIT_NUMBER_FORMAT = "%1\$s %2\$s"
    const val VITAMIN_NUMBER_FORMAT = "%1\$s %2\$s"

    @JvmStatic
    fun getCorrectFruitSpelling(context: Context, quantity: Int, fruitName: String): String {
        if (isFruitNameException(fruitName) && quantity > 1) {
            return getFruitExceptionSpelling(fruitName)
        }
        val res = context.resources
        return res.getQuantityString(R.plurals.fruits, quantity, fruitName)
    }

    private fun getFruitExceptionSpelling(fruitName: String): String {
        if (fruitName == "cherry") {
            return "cherries"
        } else if (fruitName == "strawberry") {
            return "strawberries"
        }
        return EMPTY_STRING
    }

    private fun isFruitNameException(fruitName: String): Boolean {
        if (fruitName == "cherry") {
            return true
        } else if (fruitName == "strawberry") {
            return true
        }
        return false
    }
}
