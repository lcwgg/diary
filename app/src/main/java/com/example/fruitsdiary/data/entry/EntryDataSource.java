package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.DataSource;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class EntryDataSource extends DataSource {

    @Inject
    EntryDataSource(FruitsDiaryService service, SchedulerProvider provider) {
        super(service, provider);
    }

    /*
        Return the full list of entries
     */
    public Observable<List<Entry>> getAllEntries() {
        return Observable.defer(new Callable<ObservableSource<List<Entry>>>() {
            @Override
            public ObservableSource<List<Entry>> call() throws Exception {
                return mService.getAllEntries();
            }
        })
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
