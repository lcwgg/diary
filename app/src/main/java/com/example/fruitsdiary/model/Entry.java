package com.example.fruitsdiary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Entry implements Parcelable{

    @SerializedName("id")
    int id;
    @SerializedName("date")
    String date;
    @SerializedName("fruit")
    List<FruitEntry> fruitList;
    int vitamins = 0;

    public Entry() {
    }

    public Entry(Parcel in) {
        getFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FruitEntry> getFruitList() {
        return fruitList;
    }

    public void setFruitList(List<FruitEntry> fruitList) {
        this.fruitList = fruitList;
    }

    public int getVitamins() {
        return vitamins;
    }

    public void setVitamins(int vitamins) {
        this.vitamins = vitamins;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.date);
        dest.writeList(this.fruitList);
        dest.writeInt(this.vitamins);
    }

    public void getFromParcel(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        fruitList = new ArrayList<>();
        in.readList(fruitList, FruitEntry.class.getClassLoader());
        this.vitamins = in.readInt();
    }
}
