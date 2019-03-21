package com.example.fruitsdiary.usecase.diary

import com.example.fruitsdiary.data.entry.EntryRepository
import com.example.fruitsdiary.model.Entry
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(MockitoJUnitRunner::class)
class DiaryPresenterTest {

    @Mock
    lateinit var mEntryRepository: EntryRepository

    private lateinit var mPresenter: DiaryPresenter

    @Mock
    lateinit var mView: DiaryContract.View

    private val mockEntryList: MutableList<Entry>
        get() {
            val entryList = ArrayList<Entry>()
            val entry1 = Entry()
            val entry2 = Entry()
            val entry3 = Entry()
            entry1.date = "2019-12-02"
            entry2.date = "2019-12-03"
            entry2.date = "2019-12-04"
            entryList.add(entry1)
            entryList.add(entry2)
            entryList.add(entry3)
            return entryList
        }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mPresenter = DiaryPresenter(mEntryRepository)
        mPresenter.setView(mView)
    }

    @Test
    fun populateEntriesSuccess() {
        Mockito.`when`(mEntryRepository.getAllEntries()).thenReturn(Observable.just(mockEntryList))
        mPresenter.populateEntries()
        Mockito.verify<DiaryContract.View>(mView).showEntries(Mockito.anyListOf(Entry::class.java))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mPresenter.setView(null)
    }
}