package com.example.fruitsdiary.network

import com.example.fruitsdiary.model.AddEntryBody
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.model.Response

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FruitsDiaryService {

    @GET("fruit")
    fun getAllFruits(): Observable<List<Fruit>>

    @GET("entries")
    fun getAllEntries(): Observable<MutableList<Entry>>

    @POST("entries")
    fun addEntry(@Body entry: AddEntryBody): Observable<Entry>

    @GET("entry/{id}")
    fun getEntry(@Path("id") id: Int): Observable<Entry>

    @POST("entry/{entryId}/fruit/{fruitId}")
    fun addFruitToEntry(
            @Path("entryId") id: Int,
            @Path("fruitId") fruitId: Int,
            @Query("amount") fruitAmount: Int
    ): Observable<Response>

    @DELETE("entry/{id}")
    fun deleteEntry(@Path("id") id: Int): Observable<Response>
}
