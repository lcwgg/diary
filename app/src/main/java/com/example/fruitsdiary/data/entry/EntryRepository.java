package com.example.fruitsdiary.data.entry;

import com.example.fruitsdiary.data.fruit.FruitRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.EntryFruit;
import com.example.fruitsdiary.model.Fruit;

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

    public Observable<List<Entry>> getAllEntries() {
        return mEntryDataSource.getAllEntries()
                .zipWith(mFruitRepository.getFruits(), new BiFunction<List<Entry>, List<Fruit>, List<Entry>>() {
                    @Override
                    public List<Entry> apply(final List<Entry> entries, final List<Fruit> fruitList) throws Exception {
                        updateEntryVitamins(entries, fruitList);
                        updateEntryFruitVitamins(entries, fruitList);
                        return entries;
                    }
                });
    }

    private void updateEntryVitamins(final List<Entry> entries, final List<Fruit> fruitList){
        Observable.fromIterable(entries)
                .subscribe(new Consumer<Entry>() {
                    @Override
                    public void accept(final Entry entry) throws Exception {
                        List<EntryFruit> entryFruitList = entry.getFruitList();
                        int fruitListSize = entryFruitList.size();
                        for (int i=0; i< fruitListSize; i++)   {
                            final EntryFruit entryFruit = entryFruitList.get(i);
                            getFilteredFruitObservable(fruitList,entryFruit)
                            .subscribe(new Consumer<Fruit>() {
                                @Override
                                public void accept(Fruit fruit) throws Exception {
                                    int currentVitamins = entry.getVitamins();
                                    entry.setVitamins(currentVitamins + (fruit.getVitamins() * entryFruit.getAmount()));
                                }
                            });
                        }
                    }
                });
    }

    private void updateEntryFruitVitamins(List<Entry> entries, final List<Fruit> fruitList){
        Observable.fromIterable(entries)
                .flatMapIterable(new Function<Entry, Iterable<EntryFruit>>() {
                    @Override
                    public Iterable<EntryFruit> apply(Entry entry) throws Exception {
                        return entry.getFruitList();
                    }
                })
                .subscribe(new Consumer<EntryFruit>() {
                    @Override
                    public void accept(final EntryFruit entryFruit) throws Exception {
                        setFruitVitamins(entryFruit, fruitList);
                    }
                });
    }

    private void setFruitVitamins(final EntryFruit entryFruit, List<Fruit> fruitList){
        getFilteredFruitObservable(fruitList, entryFruit). subscribe(new Consumer<Fruit>() {
            @Override
            public void accept(Fruit fruit) throws Exception {
                entryFruit.setVitamins(fruit.getVitamins());
            }
        });
    }

    private Observable<Fruit> getFilteredFruitObservable(List<Fruit> fruitList, final EntryFruit entryFruit){
        return Observable.fromIterable(fruitList)
                .filter(new Predicate<Fruit>() {
                    @Override
                    public boolean test(Fruit fruit) throws Exception {
                        return fruit.getType().equals(entryFruit.getFruitType());
                    }
                });
    }
}
