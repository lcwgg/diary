package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.model.Response;
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AddEditEntryPresenter implements AddEditEntryContract.Presenter {

    private AddEditEntryContract.View mView;

    private final EntryRepository mEntryRepository;

    private CompositeDisposable mCompositeDisposable;

    private Entry mEntry;

    private Entry mEntryFromDiary;

    @Inject
    public AddEditEntryPresenter(EntryRepository entryRepository) {
        mEntryRepository = entryRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    void setEntryFromDiary(Entry entryFromDiary) {
        mEntryFromDiary = entryFromDiary;
    }

    void updateEntryDate(String date) {
        mEntry.setDate(date);
    }

    void setFruitEntryList(List<FruitEntry> fruitEntryList) {
        mEntry.setFruitList(fruitEntryList);
    }

    @Override
    public void saveEntry() {
        mCompositeDisposable.add(
                Observable.fromIterable(mEntry.getFruitList())
                        .flatMap(new Function<FruitEntry, ObservableSource<Response>>() {
                            @Override
                            public ObservableSource<Response> apply(FruitEntry fruitEntry) throws Exception {
                                return mEntryRepository.addFruitToEntry(mEntry.getId(), fruitEntry);
                            }
                        })
                        .lastElement() // subscribe to only the last item
                        .subscribe(new Consumer<Response>() {
                            @Override
                            public void accept(Response response) throws Exception {
                                mView.onEntrySaved(mEntry.getFruitList());
                            }
                        }, new CommonNetworkErrorConsumer(mView))
        );
    }

    @Override
    public void deleteEntry() {

    }

    @Override
    public void loadEntry(int id) {
        mCompositeDisposable.add(
                mEntryRepository.getEntry(id)
                        .subscribe(new Consumer<Entry>() {
                            @Override
                            public void accept(Entry entry) throws Exception {
                                mEntry = entry;
                                // TODO replace mEntryFromDiary by mEntry when the API will work
                                mEntry = mEntryFromDiary;
                                mView.updateEntryView(mEntry);
                            }
                        }, new CommonNetworkErrorConsumer(mView) {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                super.accept(throwable);
                                // If there is an error, we use the entry loaded
                                // from the diary fragment
                                mEntry = mEntryFromDiary;
                                mView.updateEntryView(mEntry);
                            }
                        })
        );
    }

    @Override
    public void subscribe() {
        // we only saved the entry from the DiaryFragment, so we get the id from it
        loadEntry(mEntryFromDiary.getId());
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(AddEditEntryContract.View view) {
        mView = view;
    }
}
