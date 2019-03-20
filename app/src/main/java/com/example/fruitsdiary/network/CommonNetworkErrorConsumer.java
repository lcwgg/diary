package com.example.fruitsdiary.network;

import com.example.fruitsdiary.BaseView;
import com.example.fruitsdiary.exception.ErrorFactory;
import com.example.fruitsdiary.exception.FruitDiaryException;

import io.reactivex.functions.Consumer;

public class CommonNetworkErrorConsumer implements Consumer<Throwable> {

    private BaseView mView;

    public CommonNetworkErrorConsumer(BaseView view) {
        mView = view;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        FruitDiaryException exception = ErrorFactory.INSTANCE.createFromThrowable(throwable);
        mView.handleNetworkError(exception);
    }

}
