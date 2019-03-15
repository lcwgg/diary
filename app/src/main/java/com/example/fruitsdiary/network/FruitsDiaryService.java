package com.example.fruitsdiary.network;

import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.model.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FruitsDiaryService {

    @GET("fruit")
    Observable<List<Fruit>> getAllFruits();

    @GET("entries")
    Observable<List<Entry>> getAllEntries();

    @GET("entry/{id}")
    Observable<Entry> getEntry(@Path("id")int id);

    @POST("entry/{entryId}/fruit/{fruitId}")
    Observable<Response> addFruitToEntry(
            @Path("entryId") int id,
            @Path("fruitId") int fruitId,
            @Query("amount") int fruitAmount
    );

    @DELETE("entry/{id}")
    Observable<Response> deleteEntry(@Path("id")int id);
}
