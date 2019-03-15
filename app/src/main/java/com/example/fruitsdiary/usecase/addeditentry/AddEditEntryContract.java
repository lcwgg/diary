package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;

import java.util.List;

public interface AddEditEntryContract {

    interface View extends BaseView {
        void setEntry(Entry entry);
    }

    interface Presenter extends BasePresenter<View> {
        void saveEntry();
        void deleteEntry();
        void loadEntry(int id);
    }
}
