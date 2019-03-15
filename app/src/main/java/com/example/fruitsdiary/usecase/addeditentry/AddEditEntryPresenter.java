package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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

    void updateEntryDate(String date){
        mEntry.setDate(date);
    }

    void setFruitEntryList(List<FruitEntry> fruitEntryList){
        mEntry.setFruitList(fruitEntryList);
    }

    @Override
    public void saveEntry() {

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
                                mView.setEntry(entry);
                            }
                        }, new CommonNetworkErrorConsumer(mView){
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                super.accept(throwable);
                                // If there is an error, we use the entry loaded
                                // from the diary fragment
                                mEntry = mEntryFromDiary;
                                mView.setEntry(mEntry);
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
