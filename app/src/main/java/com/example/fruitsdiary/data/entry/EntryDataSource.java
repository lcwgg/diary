package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.DataSource;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EntryDataSource extends DataSource {

    @Inject
    EntryDataSource(FruitsDiaryService service, SchedulerProvider provider) {
        super(service, provider);
    }

    /*
        Return the full list of entries
     */
    public Observable<List<Entry>> getAllEntries() {
        return mService.getAllEntries()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    public Observable<Entry> getEntry(int id) {
        return mService.getEntry(id)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
