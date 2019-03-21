package com.example.fruitsdiary.data.entry

import com.example.fruitsdiary.data.fruit.FruitRepository
import com.example.fruitsdiary.model.*
import com.example.fruitsdiary.util.StringUtils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*
import javax.inject.Inject

class EntryRepository @Inject
constructor (private val mEntryDataSource: EntryDataSource, private val mFruitRepository: FruitRepository) {

    /**
     * Get all entries and apply transformation:
     * - filter the fruit with amount == 0 (not displayed)
     * - set the total vitamins for this entry
     * - add the vitamins and image path to each fruitEntry
     * @return an Observable the will emit the list of entries
     */
    fun getAllEntries(): Observable<MutableList<Entry>> = mEntryDataSource.getAllEntries()
            .zipWith(mFruitRepository.getFruits(), BiFunction { entryList, fruitList ->
                updateEntriesData(entryList, fruitList)
                entryList.reverse()
                return@BiFunction entryList
            })

    /**
     * Get a single entry and apply transformation:
     * - filter the fruit with amount == 0 (not displayed)
     * - set the total vitamins for this entry
     * - add the vitamins and image path to each fruitEntry
     * @param id the id of the entry to load
     * @return the Observable that will emit the entry
     */
    fun getEntry(id: Int): Observable<Entry> {
        return mEntryDataSource.getEntry(id)
                .zipWith(mFruitRepository.getFruits(), BiFunction { entry, fruitList ->
                    val entryList = ArrayList<Entry>()
                    entryList.add(entry)
                    updateEntriesData(entryList, fruitList)
                    entry
                })
    }

    private fun updateEntriesData(entryList: MutableList<Entry>, fruitList: List<Fruit>) {
        filterEmptyFruitEntry(entryList)
        updateEntryVitamins(entryList, fruitList)
        updateEntryFruitVitaminsAndImage(entryList, fruitList)
    }

    /**
     * Set the specified fruit to the specified entry
     * @param entryId the entry to be modified
     * @param fruitEntry the id of the fruit to add
     * @return
     */
    fun addFruitToEntry(entryId: Int, fruitEntry: FruitEntry): Observable<Response> {
        return mEntryDataSource.addFruitToEntry(entryId, fruitEntry.id, fruitEntry.amount)
    }

    fun deleteEntry(id: Int): Observable<Response> {
        return mEntryDataSource.deleteEntry(id)
    }

    fun addEntry(entry: Entry): Observable<Entry> {
        val body = AddEntryBody()
        body.date = entry.date
        return mEntryDataSource.addEntry(body)
    }

    private fun filterEmptyFruitEntry(entryList: MutableList<Entry>) {
        for (entry in entryList) {
            entry.fruitList = entry.fruitList.filter { it.amount > 0 }.toMutableList()
        }
    }

    private fun updateEntryVitamins(entryList: List<Entry>, fruitList: List<Fruit>) {
        entryList.forEach() { entry ->
            entry.fruitList.forEach() { fruitEntry ->
                val fruit = getFilteredFruitObservable(fruitList, fruitEntry)
                val currentVitamins = entry.vitamins
                entry.vitamins = currentVitamins + ((fruit?.vitamins ?: 0) * fruitEntry.amount)
            }
        }
    }

    private fun updateEntryFruitVitaminsAndImage(entryList: List<Entry>, fruitList: List<Fruit>) {
        entryList.flatMap { it.fruitList }.forEach() { setFruitVitaminsAndImage(it, fruitList) }
    }

    private fun setFruitVitaminsAndImage(fruitEntry: FruitEntry, fruitList: List<Fruit>) {
        val fruit = getFilteredFruitObservable(fruitList, fruitEntry)
        fruitEntry.vitamins = fruit?.vitamins ?: 0
        fruitEntry.image = fruit?.image ?: StringUtils.EMPTY_STRING
    }

    private fun getFilteredFruitObservable(fruitList: List<Fruit>, fruitEntry: FruitEntry): Fruit? {
        return fruitList.find { it.type == fruitEntry.type }
    }
}
