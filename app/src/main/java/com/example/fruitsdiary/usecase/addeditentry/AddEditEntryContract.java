package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;

import java.util.List;

interface AddEditEntryContract {

    interface View extends BaseView {
        void updateEntryView(Entry entry);
        void onEntrySaved(List<FruitEntry> fruitEntryList);
        void onEntryDeleted();
    }

    interface Presenter extends BasePresenter<View> {
        void saveEntry();
        void deleteEntry();
        void getEntry(int id);
    }
}
