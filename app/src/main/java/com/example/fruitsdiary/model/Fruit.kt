package com.example.fruitsdiary.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Fruit(
        @PrimaryKey var id: Int,
        @ColumnInfo(name ="type") var type: String,
        @ColumnInfo(name ="image") var image: String,
        @ColumnInfo(name ="vitamins") var vitamins: Int = 0) : Parcelable

fun Fruit.toFruitEntry(): FruitEntry {
    val fruitEntry = FruitEntry(id, type, 0)
    fruitEntry.image = image
    fruitEntry.vitamins = vitamins
    return fruitEntry
}
