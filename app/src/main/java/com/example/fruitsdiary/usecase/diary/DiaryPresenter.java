package com.example.fruitsdiary.usecase.diary;

import android.util.Log;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer;
import com.example.fruitsdiary.util.DateUtils;

import java.io.Console;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class DiaryPresenter implements DiaryContract.Presenter {

    private final EntryRepository mEntryRepository;

    private DiaryContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    public DiaryPresenter(EntryRepository entryRepository) {
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
                    public void accept(List<Entry> entryList) throws Exception {
                        mView.showEntries(entryList);
                        saveTodayEntry(entryList);
                    }
                }, new CommonNetworkErrorConsumer(mView))
        );
    }

    private void saveTodayEntry(List<Entry> entryList){
        Observable.fromIterable(entryList)
                .filter(new Predicate<Entry>() {
                    @Override
                    public boolean test(Entry entry) throws Exception {
                        return entry.getDate().equals(DateUtils.getCurrentServerDate());
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Entry>>() {
                    @Override
                    public ObservableSource<? extends Entry> apply(Throwable throwable) throws Exception {
                        return Observable.empty(); // We don't want to do anything if there is no today's entry
                    }
                })
                .subscribe(new Consumer<Entry>() {
            @Override
            public void accept(Entry entry) throws Exception {
                mView.setTodayEntry(entry);
            }
        });
    }
}
