package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.fruit.FruitRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.model.Fruit;

import java.util.ArrayList;
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

    public Observable<Entry> getEntry(int id){
        return mEntryDataSource.getEntry(id)
                .zipWith(mFruitRepository.getFruits(), new BiFunction<Entry, List<Fruit>, Entry>() {
            @Override
            public Entry apply(final Entry entry, final List<Fruit> fruitList) throws Exception {
                List<Entry> entries = new ArrayList<>();
                entries.add(entry);
                updateEntryVitamins(entries, fruitList);
                updateEntryFruitVitaminsAndImage(entries, fruitList);
                return entry;
            }
        });
    }

    public Observable<List<Entry>> getAllEntries() {
        return mEntryDataSource.getAllEntries()
                .zipWith(mFruitRepository.getFruits(), new BiFunction<List<Entry>, List<Fruit>, List<Entry>>() {
                    @Override
                    public List<Entry> apply(final List<Entry> entries, final List<Fruit> fruitList) throws Exception {
                        updateEntryVitamins(entries, fruitList);
                        updateEntryFruitVitaminsAndImage(entries, fruitList);
                        return entries;
                    }
                });
    }

    private void updateEntryVitamins(final List<Entry> entries, final List<Fruit> fruitList){
        Observable.fromIterable(entries)
                .subscribe(new Consumer<Entry>() {
                    @Override
                    public void accept(final Entry entry) throws Exception {
                        List<FruitEntry> fruitEntryList = entry.getFruitList();
                        int fruitListSize = fruitEntryList.size();
                        for (int i=0; i< fruitListSize; i++)   {
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

    private void updateEntryFruitVitaminsAndImage(List<Entry> entries, final List<Fruit> fruitList){
        Observable.fromIterable(entries)
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

    private void setFruitVitaminsAndImage(final FruitEntry fruitEntry, List<Fruit> fruitList){
        getFilteredFruitObservable(fruitList, fruitEntry). subscribe(new Consumer<Fruit>() {
            @Override
            public void accept(Fruit fruit) throws Exception {
                fruitEntry.setVitamins(fruit.getVitamins());
                fruitEntry.setImage(fruit.getImage());
            }
        });
    }

    private Observable<Fruit> getFilteredFruitObservable(List<Fruit> fruitList, final FruitEntry fruitEntry){
        return Observable.fromIterable(fruitList)
                .filter(new Predicate<Fruit>() {
                    @Override
                    public boolean test(Fruit fruit) throws Exception {
                        return fruit.getType().equals(fruitEntry.getType());
                    }
                });
    }
}
