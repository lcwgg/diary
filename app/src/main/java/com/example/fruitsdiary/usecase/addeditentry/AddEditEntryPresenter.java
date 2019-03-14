package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.data.entry.EntryRepository;

import javax.inject.Inject;

public class AddEditEntryPresenter implements AddEditEntryContract.Presenter {

    private final EntryRepository mEntryRepository;

    @Inject
    public AddEditEntryPresenter(EntryRepository entryRepository) {
        mEntryRepository = entryRepository;
    }

    @Override
    public void saveEntry() {

    }

    @Override
    public void deleteEntry() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setView(AddEditEntryContract.View view) {

    }
}
