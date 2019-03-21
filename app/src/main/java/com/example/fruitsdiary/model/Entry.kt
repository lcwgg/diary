package com.example.fruitsdiary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Entry(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("date") var date: String = "",
        @SerializedName("fruit") var fruitList: MutableList<FruitEntry> = ArrayList() ): Parcelable {

    @IgnoredOnParcel
    var vitamins = 0
}
