package com.example.fruitsdiary.usecase.diary

import com.example.fruitsdiary.data.entry.EntryRepository
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer
import com.example.fruitsdiary.util.DateUtils

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

class DiaryPresenter(private val mEntryRepository: EntryRepository) : DiaryContract.Presenter {

    private var mView: DiaryContract.View? = null

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun setView(view: DiaryContract.View?) {
        mView = view
    }

    override fun subscribe() {
        populateEntries()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun populateEntries() {
        mCompositeDisposable.add(mEntryRepository.getAllEntries()
                .subscribe(Consumer { entryList ->
                    mView?.showEntries(entryList)
                    saveTodayEntry(entryList)
                }, CommonNetworkErrorConsumer(mView))
        )
    }

    private fun saveTodayEntry(entryList: List<Entry>) {
        mView?.setTodayEntry(entryList.find { it.date == DateUtils.getCurrentServerDate() })
    }
}
