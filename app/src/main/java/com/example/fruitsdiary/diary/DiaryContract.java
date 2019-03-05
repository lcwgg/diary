package com.example.fruitsdiary.diary;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;

public interface DiaryContract {

    interface View extends BaseView<Presenter> {
        void showEntries();

        void showEntriesLoadError();
    }

    interface Presenter extends BasePresenter {
        void populateEntries();
    }
}
