package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import com.example.fruitsdiary.data.fruit.FruitRepository
import com.example.fruitsdiary.model.Fruit
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class SelectFruitPresenter(private val mFruitRepository: FruitRepository) : SelectFruitContract.Presenter {

    private var mView: SelectFruitContract.View? = null

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: SelectFruitContract.View?) {
        mView = view
    }

    override fun subscribe() {
        populateFruitList()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun populateFruitList() {
        mCompositeDisposable.add(mFruitRepository.getFruits()
                .subscribe(Consumer { fruitList -> mView?.showFruitList(fruitList) },
                        CommonNetworkErrorConsumer(mView)))
    }

    fun getFruitEntry(fruit: Fruit): FruitEntry {
        val fruitEntry = FruitEntry.fromFruit(fruit)
        fruitEntry.amount = 1 // minimum amount
        return fruitEntry
    }
}
