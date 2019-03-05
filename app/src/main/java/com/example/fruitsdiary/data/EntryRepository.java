package com.example.fruitsdiary.data;

import com.example.fruitsdiary.model.Entry;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class EntryRepository {

    private final EntryDataSource mEntryDataSource;

    @Inject
    EntryRepository(EntryDataSource entryDataSource) {
        mEntryDataSource = entryDataSource;
    }
    /*
        Return the entries one by one
    */
    public Observable<Entry> getEntries() {
        return mEntryDataSource.getAllEntries()
                .flatMap(new Function<List<Entry>, ObservableSource<Entry>>() {
                    @Override
                    public ObservableSource<Entry> apply(List<Entry> entries) throws Exception {
                        return Observable.fromIterable(entries);
                    }
                });
    }

    public Observable<List<Entry>> getAllEntries() {
        return mEntryDataSource.getAllEntries();
    }
}
