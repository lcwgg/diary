package com.example.fruitsdiary.dagger

import com.example.fruitsdiary.data.entry.EntryRepository
import com.example.fruitsdiary.data.fruit.FruitRepository
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryPresenter
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitPresenter
import com.example.fruitsdiary.usecase.diary.DiaryPresenter

import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun getDiaryPresenter(entryRepository: EntryRepository) = DiaryPresenter(entryRepository)

    @Provides
    fun getSelectFruitPresenter(fruitRepository: FruitRepository) = SelectFruitPresenter(fruitRepository)

    @Provides
    fun getAddEditEntryPresenter(entryRepository: EntryRepository) = AddEditEntryPresenter(entryRepository)
}
