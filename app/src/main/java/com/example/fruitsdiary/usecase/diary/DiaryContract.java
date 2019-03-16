package com.example.fruitsdiary.usecase.diary;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.model.Entry;

import java.util.List;

public interface DiaryContract {

    interface View extends BaseView {
        void showEntries(List<Entry> entryList);

        void setTodayEntry(Entry entry);
    }

    interface Presenter extends BasePresenter<View> {
        void populateEntries();
    }
}
