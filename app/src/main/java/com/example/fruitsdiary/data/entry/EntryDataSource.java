package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.DataSource;
import com.example.fruitsdiary.model.AddEntryBody;
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
     * @return the full list of entries
     */
    Observable<List<Entry>> getAllEntries() {
        return getService().getAllEntries()
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui());
    }

    /**
     * Get a single entry
     *
     * @param id the id of the entry to load
     * @return return a single entry
     */
    Observable<Entry> getEntry(int id) {
        return getService().getEntry(id)
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui());
    }

    /**
     * Add fruit to an entry
     *
     * @param entryId     the id of the entry to update
     * @param fruitId     the id of the fruit to set
     * @param fruitAmount the amount of fruit set
     * @return
     */
    Observable<Response> addFruitToEntry(int entryId, int fruitId, int fruitAmount) {
        return getService().addFruitToEntry(entryId, fruitId, fruitAmount)
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui());
    }

    /**
     * Delete an entry
     *
     * @param id the id of the entry to delete
     * @return
     */
    Observable<Response> deleteEntry(int id) {
        return getService().deleteEntry(id)
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui());
    }

    Observable<Entry> addEntry(AddEntryBody body) {
        return getService().addEntry(body)
                .subscribeOn(getProvider().io())
                .observeOn(getProvider().ui());
    }
}
