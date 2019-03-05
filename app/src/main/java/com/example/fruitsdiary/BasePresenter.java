package com.example.fruitsdiary;

public interface BasePresenter<T extends BaseView> {

    void subscribe();
    void unsubscribe();
    void setView(T view);

}
