package com.example.fruitsdiary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Fruit implements Parcelable {

    int id;
    String type;
    String image;
    int vitamins;

    public Fruit(Parcel in) {
        this.getFromParcel(in);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVitamins() {
        return vitamins;
    }

    public void setVitamins(int vitamins) {
        this.vitamins = vitamins;
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Fruit createFromParcel(Parcel in) {
            return new Fruit(in);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
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
        dest.writeString(this.image);
        dest.writeInt(this.vitamins);
    }

    public void getFromParcel(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.image = in.readString();
        this.vitamins = in.readInt();
    }
}
