package com.example.fruitsdiary.model;

import com.google.gson.annotations.SerializedName;

public class EntryFruit {

    @SerializedName("fruitId")
    int fruitId;
    @SerializedName("fruitType")
    String fruitType;
    @SerializedName("amount")
    int amount;
    int vitamins;

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
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
}
