package com.example.fruitsdiary.diary;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.data.EntryRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DiaryPresenter implements DiaryContract.Presenter {

    private final EntryRepository mEntryRepository;

    private CompositeDisposable mCompositeDisposable;

    @Inject
    public DiaryPresenter(EntryRepository entryRepository){
        mEntryRepository = entryRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void populateEntries() {

    }
}
