package com.example.fruitsdiary.util

import android.content.Context
import com.example.fruitsdiary.R

object StringUtils {


    @JvmStatic val emptyString = ""
    @JvmStatic val noStringDefined = -1
    @JvmStatic  val fruitNumberFormat = "%1\$s %2\$s"
    @JvmStatic  val vitaminNumberFormat = "%1\$s %2\$s"

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
        return emptyString
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
