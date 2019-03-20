package com.example.fruitsdiary.usecase.addeditentry

import com.example.fruitsdiary.model.FruitEntry

interface AddEditEntryManager {

    fun saveEntry()

    fun addOrUpdateFruitEntry(fruitEntry: FruitEntry)

    fun deleteFruitEntry(fruitEntry: FruitEntry)

    fun deleteEntry()

    operator fun contains(fruitEntry: FruitEntry): Boolean

    fun getCorrespondingFruitEntry(fruitEntry: FruitEntry): FruitEntry?

    fun showOverlay()

    fun hideOverlay()
}
