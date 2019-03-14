package com.example.fruitsdiary.usecase.addeditentry.selectfruit;

import com.example.fruitsdiary.BasePresenter;
import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;

import java.util.List;

public interface SelectFruitContract {

    interface View extends BaseView {
        void showFruitList(List<Fruit> fruitList);
    }

    interface Presenter extends BasePresenter<View> {
        void populateFruitList();
    }
}
