package com.example.fruitsdiary.usecase.addeditentry;

import android.support.annotation.NonNull;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.model.Response;
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class AddEditEntryPresenter implements AddEditEntryContract.Presenter {

    private static final int ENTRY_NOT_CREATED_ID = -1;

    private AddEditEntryContract.View mView;

    private final EntryRepository mEntryRepository;

    private CompositeDisposable mCompositeDisposable;

    private Entry mEntry;

    private Entry mEntryFromDiary;

    @Inject
    public AddEditEntryPresenter(EntryRepository entryRepository) {
        mEntryRepository = entryRepository;
        mCompositeDisposable = new CompositeDisposable();
        mEntry = new Entry();
        mEntry.setId(ENTRY_NOT_CREATED_ID);
        mEntry.setFruitList(new ArrayList<FruitEntry>());
    }

    @Override
    public void saveEntry() {
        if (mEntry.getId() == ENTRY_NOT_CREATED_ID) {
            addEntry();
        } else {
            editEntry();
        }
    }

    private void addEntry() {
        mCompositeDisposable.add(
                mEntryRepository.addEntry(mEntry)
                        .flatMapMaybe(new Function<Entry, MaybeSource<Response>>() {
                            @Override
                            public MaybeSource<Response> apply(Entry entry) throws Exception {
                                mEntry.setId(entry.getId());
                                return editFruitEntryListObservable();
                            }
                        })
                        .subscribe(
                                new SaveEntryConsumer(),
                                new CommonNetworkErrorConsumer(mView))
        );
    }

    private void editEntry() {
        mCompositeDisposable.add(
                editFruitEntryListObservable()
                        .subscribe(
                                new SaveEntryConsumer(),
                                new CommonNetworkErrorConsumer(mView))
        );
    }

    private Maybe<Response> editFruitEntryListObservable() {
        return Observable.fromIterable(mEntry.getFruitList())
                .flatMap(new Function<FruitEntry, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(FruitEntry fruitEntry) throws Exception {
                        return mEntryRepository.addFruitToEntry(mEntry.getId(), fruitEntry);
                    }
                })
                .lastElement(); // subscribe to only the last item
    }

    @Override
    public void deleteEntry() {
        mCompositeDisposable.add(
                mEntryRepository.deleteEntry(mEntry.getId())
                        .subscribe(new Consumer<Response>() {
                            @Override
                            public void accept(Response response) throws Exception {
                                mView.onEntryDeleted();
                            }
                        }, new CommonNetworkErrorConsumer(mView))
        );
    }

    @Override
    public void getEntry(int id) {
        mCompositeDisposable.add(
                mEntryRepository.getEntry(id)
                        .subscribe(new Consumer<Entry>() {
                            @Override
                            public void accept(Entry entry) throws Exception {
                                mEntry = entry;
                                // TODO replace mEntryFromDiary by mEntry when the API will work
                                mEntry = mEntryFromDiary;
                                setFruitEntryToNotModified();
                                mView.updateEntryView(mEntry);
                            }
                        }, new CommonNetworkErrorConsumer(mView) {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                super.accept(throwable);
                                // If there is an error, we use the entry loaded
                                // from the diary fragment
                                mEntry = mEntryFromDiary;
                                setFruitEntryToNotModified();
                                mView.updateEntryView(mEntry);
                            }
                        })
        );
    }

    void setEntryFromDiary(Entry entryFromDiary) {
        mEntryFromDiary = entryFromDiary;
    }

    void updateEntryDate(String date) {
        mEntry.setDate(date);
    }

    void removeFruitEntry(@NonNull FruitEntry fruitEntry) {
        int index = mEntry.getFruitList().indexOf(fruitEntry);
        mEntry.getFruitList().get(index).setAmount(0);
    }

    void addFruitEntry(@NonNull FruitEntry fruitEntry) {
        fruitEntry.setModified(true);
        mEntry.getFruitList().add(fruitEntry);
    }

    void updateFruitEntry(@NonNull FruitEntry fruitEntry) {
        fruitEntry.setModified(true);
        int index = getFruitEntryIndex(fruitEntry);
        mEntry.getFruitList().set(index, fruitEntry);
    }

    void filterFruitEntryList(final AddEditEntryFragment.OnFruitEntryListFilteredListener listener) {
        Observable.fromIterable(new ArrayList<>(mEntry.getFruitList()))
                .filter(new Predicate<FruitEntry>() {
                    @Override
                    public boolean test(FruitEntry fruitEntry) throws Exception {
                        return fruitEntry.getAmount() != 0;
                    }
                })
                .toList()
                .subscribe(new Consumer<List<FruitEntry>>() {
                    @Override
                    public void accept(List<FruitEntry> fruitEntryList) throws Exception {
                        listener.onFruitEntryListFiltered(fruitEntryList);
                    }
                });

    }

    @Override
    public void subscribe() {
        // we only saved the entry from the DiaryFragment, so we get the id from it
        getEntry(mEntryFromDiary.getId());
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(AddEditEntryContract.View view) {
        mView = view;
    }

    private void setFruitEntryToNotModified() {
        List<FruitEntry> fruitEntryList = mEntry.getFruitList();
        int size = fruitEntryList.size();
        for (int i = 0; i < size; i++) {
            fruitEntryList.get(i).setModified(false);
        }
    }

    boolean contains(FruitEntry fruitEntry) {
        return mEntry.getFruitList().contains(fruitEntry);
    }

    FruitEntry getFruitEntry(FruitEntry fruitEntry) {
        return mEntry.getFruitList().get(getFruitEntryIndex(fruitEntry));
    }

    private int getFruitEntryIndex(FruitEntry fruitEntry) {
        return mEntry.getFruitList().indexOf(fruitEntry);
    }

    private class SaveEntryConsumer implements Consumer<Response> {
        @Override
        public void accept(Response response) throws Exception {
            mView.onEntrySaved(mEntry.getFruitList());
        }
    }
}
