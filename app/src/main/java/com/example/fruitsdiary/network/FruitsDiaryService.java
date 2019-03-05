package com.example.fruitsdiary.network;

import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FruitsDiaryService {

    @GET("fruit/{id}")
    Observable<Fruit> getFruit(@Path("id")int id);

    @GET("fruit")
    Observable<List<Fruit>> getAllFruits();

    @GET("entries")
    Observable<List<Entry>> getAllEntries();
}
