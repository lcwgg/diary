package com.example.fruitsdiary.model;

import java.util.List;

public class Entry {

    int id;
    String date;
    List<EntryFruit> fruitList;

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
}
