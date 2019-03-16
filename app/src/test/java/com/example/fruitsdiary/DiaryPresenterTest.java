package com.example.fruitsdiary;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.usecase.diary.DiaryContract;
import com.example.fruitsdiary.usecase.diary.DiaryPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class DiaryPresenterTest {

    @Mock
    EntryRepository mEntryRepository;

    private DiaryPresenter mPresenter;

    @Mock
    private DiaryContract.View mView;

    @Before
    public void setUp() throws Exception {
        mPresenter = new DiaryPresenter(mEntryRepository);
        mPresenter.setView(mView);
    }

    @Test
    public void populateEntriesSuccess() {
        Mockito.when(mEntryRepository.getAllEntries()).thenReturn(Observable.just(getMockEntryList()));
        mPresenter.populateEntries();
        Mockito.verify(mView).showEntries(Mockito.anyList());
    }

    @Test
    public void populateEntriesFailure() {
        Mockito.when(mEntryRepository.getAllEntries()).thenReturn(Observable.<List<Entry>>error(new Throwable()));
        mPresenter.populateEntries();
        Mockito.verify(mView).handleNetworkError(Mockito.any(FruitDiaryException.class));
    }

    @After
    public void tearDown() throws Exception {
        mPresenter.setView(null);
    }

    private List<Entry> getMockEntryList(){
        List<Entry> entryList = new ArrayList<>();
        Entry entry1 = new Entry();
        Entry entry2 = new Entry();
        Entry entry3 = new Entry();
        entry1.setDate("2019-12-02");
        entry2.setDate("2019-12-03");
        entry2.setDate("2019-12-04");
        entryList.add(entry1);
        entryList.add(entry2);
        entryList.add(entry3);
        return entryList;
    }
}