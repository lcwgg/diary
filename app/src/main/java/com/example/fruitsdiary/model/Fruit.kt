package com.example.fruitsdiary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fruit(var id: Int, var type: String, var image: String, var vitamins: Int) : Parcelable {
}
