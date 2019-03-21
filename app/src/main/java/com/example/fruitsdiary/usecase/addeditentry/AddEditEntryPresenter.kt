package com.example.fruitsdiary.usecase.addeditentry

import com.example.fruitsdiary.data.entry.EntryRepository
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.model.Response
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*
import javax.inject.Inject

class AddEditEntryPresenter @Inject
constructor(private val mEntryRepository: EntryRepository) : AddEditEntryContract.Presenter {

    private var mView: AddEditEntryContract.View? = null

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private var mEntry: Entry = Entry()

    private var mEntryFromDiary: Entry? = null

    init {
        mEntry.id = ENTRY_NOT_CREATED_ID
        mEntry.fruitList = ArrayList()
    }

    override fun saveEntry() {
        if (mEntry.id == ENTRY_NOT_CREATED_ID) {
            addEntry()
        } else {
            editEntry()
        }
    }

    private fun addEntry() {
        mCompositeDisposable.add(
                mEntryRepository.addEntry(mEntry)
                        .flatMapMaybe { (id) ->
                            mEntry.id = id
                            if (mEntry.fruitList.isEmpty()) {
                                Maybe.just(Response())
                            } else editFruitEntryListObservable()
                        }
                        .subscribe(
                                SaveEntryConsumer(),
                                CommonNetworkErrorConsumer(mView))
        )
    }

    private fun editEntry() {
        mCompositeDisposable.add(
                editFruitEntryListObservable()
                        .subscribe(
                                SaveEntryConsumer(),
                                CommonNetworkErrorConsumer(mView))
        )
    }

    private fun editFruitEntryListObservable(): Maybe<Response> {
        return Observable.fromIterable(mEntry.fruitList)
                .flatMap { fruitEntry -> mEntryRepository.addFruitToEntry(mEntry.id, fruitEntry) }
                .lastElement() // subscribe to only the last item
    }

    override fun deleteEntry() {
        mCompositeDisposable.add(
                mEntryRepository.deleteEntry(mEntry.id)
                        .subscribe(Consumer { mView?.onEntryDeleted() }, CommonNetworkErrorConsumer(mView))
        )
    }

    override fun getEntry(id: Int) {
        mCompositeDisposable.add(
                mEntryRepository.getEntry(id)
                        .subscribe(Consumer { entry ->
                            mEntry = entry
                            // TODO replace mEntryFromDiary by mEntry when the API will work
                            mEntry = mEntryFromDiary ?: Entry()
                            setFruitEntryToNotModified()
                            mView?.updateEntryView(mEntry)
                        }, object : CommonNetworkErrorConsumer(mView) {
                            override fun accept(throwable: Throwable) {
                                super.accept(throwable)
                                // If there is an error, we use the entry loaded
                                // from the diary fragment
                                mEntry = mEntryFromDiary ?: Entry()
                                setFruitEntryToNotModified()
                                mView?.updateEntryView(mEntry)
                            }
                        })
        )
    }

    fun setEntryFromDiary(entryFromDiary: Entry) {
        mEntryFromDiary = entryFromDiary
    }

    fun removeFruitEntry(fruitEntry: FruitEntry) {
        mEntry.fruitList.find { it.id == fruitEntry.id }?.amount = 0
    }

    fun addFruitEntry(fruitEntry: FruitEntry) {
        fruitEntry.isModified = true
        mEntry.fruitList.add(fruitEntry)
    }

    fun updateFruitEntry(fruitEntry: FruitEntry) {
        fruitEntry.isModified = true
        val index = getFruitEntryIndex(fruitEntry)
        mEntry.fruitList[index] = fruitEntry
    }

    fun filterFruitEntryList(): MutableList<FruitEntry> {
        return mEntry.fruitList.filter { it.amount > 0 }.toMutableList()
    }

    override fun subscribe() {
        // we get the id from the Entry passed by the DiaryFragment
        getEntry(mEntryFromDiary!!.id)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun setView(view: AddEditEntryContract.View?) {
        mView = view
    }

    private fun setFruitEntryToNotModified() {
        mEntry.fruitList.forEach { it.isModified = false }
    }

    operator fun contains(fruitEntry: FruitEntry): Boolean {
        return mEntry.fruitList.contains(fruitEntry)
    }

    fun getCorrespondingFruitEntry(fruitEntry: FruitEntry): FruitEntry? {
        return mEntry.fruitList.find { it.id == fruitEntry.id }
    }

    private fun getFruitEntryIndex(fruitEntry: FruitEntry): Int {
        return mEntry.fruitList.indexOf(fruitEntry)
    }

    private inner class SaveEntryConsumer : Consumer<Response> {
        @Throws(Exception::class)
        override fun accept(response: Response) {
            setFruitEntryToNotModified()
            mView?.onEntrySaved(mEntry.fruitList)
        }
    }

    companion object {

        private const val ENTRY_NOT_CREATED_ID = -1
    }
}
