package com.example.fruitsdiary.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FruitEntry implements Parcelable {

    @SerializedName("fruitId")
    int id;
    @SerializedName("fruitType")
    String type;
    @SerializedName("amount")
    int amount;
    int vitamins;
    String image;

    public FruitEntry() {
    }

    public FruitEntry(Parcel in) {
        getFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVitamins() {
        return vitamins;
    }

    public void setVitamins(int vitamins) {
        this.vitamins = vitamins;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FruitEntry createFromParcel(Parcel in) {
            return new FruitEntry(in);
        }

        @Override
        public FruitEntry[] newArray(int size) {
            return new FruitEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeInt(this.amount);
        dest.writeString(this.image);
        dest.writeInt(this.vitamins);
    }

    public void getFromParcel(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.amount = in.readInt();
        this.image = in.readString();
        this.vitamins = in.readInt();
    }

    public static FruitEntry fromFruit(@NonNull Fruit fruit){
        FruitEntry fruitEntry = new FruitEntry();
        fruitEntry.setId(fruit.getId());
        fruitEntry.setType(fruit.getType());
        fruitEntry.setImage(fruit.getImage());
        fruitEntry.setVitamins(fruit.getVitamins());
        return fruitEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitEntry that = (FruitEntry) o;
        return id == that.id &&
                vitamins == that.vitamins &&
                Objects.equals(type, that.type) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, vitamins, image);
    }
}
