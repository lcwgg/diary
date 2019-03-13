package com.example.fruitsdiary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entry {

    @SerializedName("id")
    int id;
    @SerializedName("date")
    String date;
    @SerializedName("fruit")
    List<EntryFruit> fruitList;
    int vitamins = 0;

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

    public List<EntryFruit> getFruitList() {
        return fruitList;
    }

    public void setFruitList(List<EntryFruit> fruitList) {
        this.fruitList = fruitList;
    }

    public int getVitamins() {
        return vitamins;
    }

    public void setVitamins(int vitamins) {
        this.vitamins = vitamins;
    }
}
