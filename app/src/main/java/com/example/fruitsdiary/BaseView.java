package com.example.fruitsdiary;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
