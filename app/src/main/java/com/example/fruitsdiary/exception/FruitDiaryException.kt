package com.example.fruitsdiary.exception

import android.support.annotation.StringRes

import com.example.fruitsdiary.util.StringUtils

abstract class FruitDiaryException : Exception {

    @StringRes var title: Int = StringUtils.NO_STRING_DEFINED
    @StringRes var errorMessage: Int = StringUtils.NO_STRING_DEFINED

    constructor(@StringRes title: Int,
                @StringRes errorMessage: Int) : super() {
        this.title = title
        this.errorMessage = errorMessage
    }

    constructor() : super()

    constructor(message: String) : super(message)


}
