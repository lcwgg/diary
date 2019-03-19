package com.example.fruitsdiary.dagger

import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryFragment
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment
import com.example.fruitsdiary.usecase.diary.DiaryFragment

import dagger.Component

@Component(modules = [ApplicationModule::class, NetworkModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(fragment: DiaryFragment)
    fun inject(fragment: SelectFruitFragment)
    fun inject(fragment: AddEditEntryFragment)
}
