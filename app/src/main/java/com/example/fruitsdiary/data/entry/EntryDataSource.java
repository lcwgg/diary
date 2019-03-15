package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.DataSource;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Response;
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
    Observable<List<Entry>> getAllEntries() {
        return mService.getAllEntries()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    /*
        Return a single entry
     */
    Observable<Entry> getEntry(int id) {
        return mService.getEntry(id)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    Observable<Response> addFruitToEntry(int entryId, int fruitId, int fruitAmount) {
        return mService.addFruitToEntry(entryId, fruitId, fruitAmount)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
