package com.example.fruitsdiary.data.entry

import com.example.fruitsdiary.data.DataSource
import com.example.fruitsdiary.model.AddEntryBody
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.Response
import com.example.fruitsdiary.network.FruitsDiaryService
import com.example.fruitsdiary.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class EntryDataSource @Inject
internal constructor(service: FruitsDiaryService, provider: SchedulerProvider) : DataSource(service, provider) {

    /**
     * @return the full list of entries
     */
    fun getAllEntries(): Observable<List<Entry>> = service.getAllEntries()
            .subscribeOn(provider.io())
            .observeOn(provider.ui())

    /**
     * Get a single entry
     *
     * @param id the id of the entry to load
     * @return return a single entry
     */
    fun getEntry(id: Int): Observable<Entry> = service.getEntry(id)
            .subscribeOn(provider.io())
            .observeOn(provider.ui())

    /**
     * Add fruit to an entry
     *
     * @param entryId     the id of the entry to update
     * @param fruitId     the id of the fruit to set
     * @param fruitAmount the amount of fruit set
     * @return
     */
    fun addFruitToEntry(entryId: Int, fruitId: Int, fruitAmount: Int): Observable<Response> =
            service.addFruitToEntry(entryId, fruitId, fruitAmount)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())

    /**
     * Delete an entry
     *
     * @param id the id of the entry to delete
     * @return
     */
    fun deleteEntry(id: Int): Observable<Response> = service.deleteEntry(id)
            .subscribeOn(provider.io())
            .observeOn(provider.ui())

    /**
     * Create an entry on the server
     * @param body the body of the entry to create
     * @return the newly created entry
     */
    fun addEntry(body: AddEntryBody): Observable<Entry> = service.addEntry(body)
            .subscribeOn(provider.io())
            .observeOn(provider.ui())
}
