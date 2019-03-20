package com.example.fruitsdiary.usecase.diary

import com.example.fruitsdiary.BasePresenter
import com.example.fruitsdiary.BaseView
import com.example.fruitsdiary.model.Entry

interface DiaryContract {

    interface View : BaseView {
        fun showEntries(entryList: List<Entry>)

        fun setTodayEntry(entry: Entry?)
    }

    interface Presenter : BasePresenter<View> {
        fun populateEntries()
    }
}
