package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.fruit.FruitRepository;
import com.example.fruitsdiary.model.AddEntryBody;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class EntryRepository {

    private final EntryDataSource mEntryDataSource;

    private final FruitRepository mFruitRepository;

    @Inject
    EntryRepository(EntryDataSource entryDataSource, FruitRepository fruitRepository) {
        mEntryDataSource = entryDataSource;
        mFruitRepository = fruitRepository;
    }

    /**
     *   Get a single entry and apply transformation:
     *         - filter the fruit with amount == 0 (not displayed)
     *         - set the total vitamins for this entry
     *         - add the vitamins and image path to each fruitEntry
     * @param id the id of the entry to load
     * @return the Observable that will emit the entry
     */
    public Observable<Entry> getEntry(int id) {
        return mEntryDataSource.getEntry(id)
                .zipWith(mFruitRepository.getFruits(), new BiFunction<Entry, List<Fruit>, Entry>() {
                    @Override
                    public Entry apply(final Entry entry, final List<Fruit> fruitList) throws Exception {
                        List<Entry> entryList = new ArrayList<>();
                        entryList.add(entry);
                        filterEmptyFruitEntry(entryList);
                        updateEntryVitamins(entryList, fruitList);
                        updateEntryFruitVitaminsAndImage(entryList, fruitList);
                        return entry;
                    }
                });
    }

    /**
     * Get all entries and apply transformation:
     *         - filter the fruit with amount == 0 (not displayed)
     *         - set the total vitamins for this entry
     *         - add the vitamins and image path to each fruitEntry
     * @return an Observable the will emit the list of entries
     */
    public Observable<List<Entry>> getAllEntries() {
        return mEntryDataSource.getAllEntries()
                .zipWith(mFruitRepository.getFruits(), new BiFunction<List<Entry>, List<Fruit>, List<Entry>>() {
                    @Override
                    public List<Entry> apply(final List<Entry> entryList, final List<Fruit> fruitList) throws Exception {
                        filterEmptyFruitEntry(entryList);
                        updateEntryVitamins(entryList, fruitList);
                        updateEntryFruitVitaminsAndImage(entryList, fruitList);
                        Collections.reverse(entryList);
                        return entryList;
                    }
                });
    }

    private void updateEntriesData(List<Entry> entryList, List<Fruit> fruitList){
        filterEmptyFruitEntry(entryList);
        updateEntryVitamins(entryList, fruitList);
        updateEntryFruitVitaminsAndImage(entryList, fruitList);
    }
    /**
     * Set the specified fruit to the specified entry
     * @param entryId the entry to be modified
     * @param fruitEntry the id of the fruit to add
     * @return
     */
    public Observable<Response> addFruitToEntry(final int entryId, FruitEntry fruitEntry) {
        return mEntryDataSource.addFruitToEntry(entryId, fruitEntry.getId(), fruitEntry.getAmount());
    }

    public Observable<Response> deleteEntry(int id){
        return mEntryDataSource.deleteEntry(id);
    }

    public Observable<Entry> addEntry(Entry entry){
        AddEntryBody body = new AddEntryBody();
        body.setDate(entry.getDate());
        return mEntryDataSource.addEntry(body);
    }

    private void filterEmptyFruitEntry(List<Entry> entryList) {
        int entrySize = entryList.size();
        for (int i = 0; i < entrySize; i++) {
            final Entry entry = entryList.get(i);
            Observable.fromIterable(entry.getFruitList())
                    .filter(new Predicate<FruitEntry>() {
                        @Override
                        public boolean test(FruitEntry fruitEntry) throws Exception {
                            return fruitEntry.getAmount() != 0;
                        }
                    }).toList()
                    .subscribe(new Consumer<List<FruitEntry>>() {
                        @Override
                        public void accept(List<FruitEntry> fruitEntryList) throws Exception {
                            entry.setFruitList(fruitEntryList);
                        }
                    });
        }
    }

    private void updateEntryVitamins(final List<Entry> entryList, final List<Fruit> fruitList) {
        Observable.fromIterable(entryList)
                .subscribe(new Consumer<Entry>() {
                    @Override
                    public void accept(final Entry entry) throws Exception {
                        List<FruitEntry> fruitEntryList = entry.getFruitList();
                        int fruitListSize = fruitEntryList.size();
                        for (int i = 0; i < fruitListSize; i++) {
                            final FruitEntry fruitEntry = fruitEntryList.get(i);
                            getFilteredFruitObservable(fruitList, fruitEntry)
                                    .subscribe(new Consumer<Fruit>() {
                                        @Override
                                        public void accept(Fruit fruit) throws Exception {
                                            int currentVitamins = entry.getVitamins();
                                            entry.setVitamins(currentVitamins + (fruit.getVitamins() * fruitEntry.getAmount()));
                                        }
                                    });
                        }
                    }
                });
    }

    private void updateEntryFruitVitaminsAndImage(List<Entry> entryList, final List<Fruit> fruitList) {
        Observable.fromIterable(entryList)
                .flatMapIterable(new Function<Entry, Iterable<FruitEntry>>() {
                    @Override
                    public Iterable<FruitEntry> apply(Entry entry) throws Exception {
                        return entry.getFruitList();
                    }
                })
                .subscribe(new Consumer<FruitEntry>() {
                    @Override
                    public void accept(final FruitEntry fruitEntry) throws Exception {
                        setFruitVitaminsAndImage(fruitEntry, fruitList);
                    }
                });
    }

    private void setFruitVitaminsAndImage(final FruitEntry fruitEntry, List<Fruit> fruitList) {
        getFilteredFruitObservable(fruitList, fruitEntry).subscribe(new Consumer<Fruit>() {
            @Override
            public void accept(Fruit fruit) throws Exception {
                fruitEntry.setVitamins(fruit.getVitamins());
                fruitEntry.setImage(fruit.getImage());
            }
        });
    }

    private Observable<Fruit> getFilteredFruitObservable(List<Fruit> fruitList, final FruitEntry fruitEntry) {
        return Observable.fromIterable(fruitList)
                .filter(new Predicate<Fruit>() {
                    @Override
                    public boolean test(Fruit fruit) throws Exception {
                        return fruit.getType().equals(fruitEntry.getType());
                    }
                });
    }
}
