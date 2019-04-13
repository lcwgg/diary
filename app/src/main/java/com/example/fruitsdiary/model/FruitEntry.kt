package com.example.fruitsdiary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FruitEntry(
        @SerializedName("fruitId") var id: Int,
        @SerializedName("fruitType") var type: String,
        @SerializedName("amount") var amount: Int,
        var vitamins: Int = 0,
        var image: String? = null) : Parcelable {

    @IgnoredOnParcel
    var isModified = false
}
