package com.example.fruitsdiary.diary;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.data.EntryRepository;
import com.example.fruitsdiary.model.Entry;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class DiaryPresenter implements DiaryContract.Presenter{

    private final EntryRepository mEntryRepository;

    private DiaryContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    public DiaryPresenter(EntryRepository entryRepository){
        mEntryRepository = entryRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(DiaryContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        populateEntries();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void populateEntries() {
        mCompositeDisposable.add(mEntryRepository.getAllEntries()
                .subscribe(new Consumer<List<Entry>>() {
                    @Override
                    public void accept(List<Entry> entries) throws Exception {
                        mView.showEntries(entries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );
    }

}
