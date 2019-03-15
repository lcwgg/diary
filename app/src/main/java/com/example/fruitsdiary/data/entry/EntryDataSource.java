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

    /**
     *
     * @return the full list of entries
     */
    Observable<List<Entry>> getAllEntries() {
        return mService.getAllEntries()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    /**
     * Get a single entry
     * @param id the id of the entry to load
     * @return return a single entry
     */
    Observable<Entry> getEntry(int id) {
        return mService.getEntry(id)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    /**
     * Add fruit to an entry
     * @param entryId the id of the entry to update
     * @param fruitId the id of the fruit to set
     * @param fruitAmount the amount of fruit set
     * @return
     */
    Observable<Response> addFruitToEntry(int entryId, int fruitId, int fruitAmount) {
        return mService.addFruitToEntry(entryId, fruitId, fruitAmount)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }

    /**
     * Delete an entry
     * @param id the id of the entry to delete
     * @return
     */
    Observable<Response> deleteEntry(int id){
        return mService.deleteEntry(id)
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui());
    }
}
