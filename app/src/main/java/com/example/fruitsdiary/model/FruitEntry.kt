package com.example.fruitsdiary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FruitEntry(
        @SerializedName("fruitId") var id: Int,
        @SerializedName("fruitType") var type: String,
        @SerializedName("amount") var amount: Int) : Parcelable {

    @IgnoredOnParcel
    var vitamins: Int = 0
    @IgnoredOnParcel
    var image: String? = null
    @IgnoredOnParcel
    var isModified = false

    companion object {

        fun fromFruit(fruit: Fruit): FruitEntry {
            val fruitEntry = FruitEntry(fruit.id, fruit.type, 0)
            fruitEntry.image = fruit.image
            fruitEntry.vitamins = fruit.vitamins
            return fruitEntry
        }
    }
}
