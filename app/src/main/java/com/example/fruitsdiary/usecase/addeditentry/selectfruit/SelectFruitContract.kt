package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import com.example.fruitsdiary.BasePresenter
import com.example.fruitsdiary.BaseView
import com.example.fruitsdiary.model.Fruit

interface SelectFruitContract {

    interface View : BaseView {
        fun showFruitList(fruitList: List<Fruit>)
    }

    interface Presenter : BasePresenter<View> {
        fun populateFruitList()
    }
}
