package com.example.fruitsdiary.diary;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.model.Entry;

import java.util.List;

public interface DiaryContract {

    interface View extends BaseView {
        void showEntries(List<Entry> entryList);
    }

    interface Presenter extends BasePresenter<View> {
        void populateEntries();
    }
}
