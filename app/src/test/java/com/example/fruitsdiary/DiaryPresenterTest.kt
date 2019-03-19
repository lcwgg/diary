package com.example.fruitsdiary

import com.example.fruitsdiary.data.entry.EntryRepository
import com.example.fruitsdiary.exception.FruitDiaryException
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.usecase.diary.DiaryContract
import com.example.fruitsdiary.usecase.diary.DiaryPresenter
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(MockitoJUnitRunner::class)
class DiaryPresenterTest {

    @Mock
    internal var mEntryRepository: EntryRepository? = null

    private var mPresenter: DiaryPresenter = DiaryPresenter(mEntryRepository)

    @Mock
    private val mView: DiaryContract.View? = null

    private val mockEntryList: List<Entry>
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
        Mockito.`when`(mEntryRepository!!.allEntries).thenReturn(Observable.just(mockEntryList))
        mPresenter.populateEntries()
        Mockito.verify<DiaryContract.View>(mView).showEntries(Mockito.anyListOf(Entry::class.java))
    }

    @Test
    fun populateEntriesFailure() {
        Mockito.`when`(mEntryRepository!!.allEntries).thenReturn(Observable.error(Throwable()))
        mPresenter.populateEntries()
        Mockito.verify<DiaryContract.View>(mView).handleNetworkError(Mockito.any<FruitDiaryException>(FruitDiaryException::class.java))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mPresenter.setView(null)
    }
}