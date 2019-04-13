package com.example.fruitsdiary.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Fruit(
        @PrimaryKey val id: Int,
        @ColumnInfo(name ="type") val type: String,
        @ColumnInfo(name ="image") val image: String,
        @ColumnInfo(name ="vitamins") val vitamins: Int = 0) : Parcelable
