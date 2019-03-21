package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import com.example.fruitsdiary.model.FruitEntry

interface OnSelectFruitListener {
    fun onFruitSelected(originalFruitEntry: FruitEntry?)
}
