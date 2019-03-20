package com.example.fruitsdiary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fruit(val id: Int, val type: String, val image: String, val vitamins: Int = 0) : Parcelable {
}
