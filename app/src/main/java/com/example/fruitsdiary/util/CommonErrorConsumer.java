package com.example.fruitsdiary.util;

import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.exception.ErrorFactory;
import com.example.fruitsdiary.exception.FruitDiaryException;

import io.reactivex.functions.Consumer;

public abstract class CommonErrorConsumer implements Consumer<Throwable> {

    private BaseView mView;

    public CommonErrorConsumer(BaseView view) {
        mView = view;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        FruitDiaryException exception = ErrorFactory.createFromThrowable(throwable);
        mView.handleError(exception);
    }

}
