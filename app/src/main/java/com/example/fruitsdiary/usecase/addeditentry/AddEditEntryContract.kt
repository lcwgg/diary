package com.example.fruitsdiary.usecase.addeditentry

import com.example.fruitsdiary.BasePresenter
import com.example.fruitsdiary.BaseView
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.FruitEntry

interface AddEditEntryContract {

    interface View : BaseView {
        fun updateEntryView(entry: Entry)
        fun onEntrySaved(fruitEntryList: List<FruitEntry>)
        fun onEntryDeleted()
    }

    interface Presenter : BasePresenter<View> {
        fun saveEntry()
        fun deleteEntry()
        fun getEntry(id: Int)
    }
}
