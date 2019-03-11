package com.example.fruitsdiary.model;

import java.util.List;

public class Entry {

    int id;
    String date;
    List<EntryFruit> fruit;

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

    public List<EntryFruit> getFruit() {
        return fruit;
    }

    public void setFruit(List<EntryFruit> fruit) {
        this.fruit = fruit;
    }
}
