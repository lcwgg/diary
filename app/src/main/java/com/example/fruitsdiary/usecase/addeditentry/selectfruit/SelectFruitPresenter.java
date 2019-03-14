package com.example.fruitsdiary.usecase.addeditentry.selectfruit;

import com.example.fruitsdiary.data.fruit.FruitRepository;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.network.CommonNetworkErrorConsumer;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SelectFruitPresenter implements SelectFruitContract.Presenter {

    private final FruitRepository mFruitRepository;

    private SelectFruitContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    public SelectFruitPresenter(FruitRepository fruitRepository) {
        mFruitRepository = fruitRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(SelectFruitContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        populateFruitList();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void populateFruitList() {
        mCompositeDisposable.add(mFruitRepository.getFruits()
                .subscribe(new Consumer<List<Fruit>>() {
                    @Override
                    public void accept(List<Fruit> fruitList) throws Exception {
                        mView.showFruitList(fruitList);
                    }
                }, new CommonNetworkErrorConsumer(mView) {
                }));
    }
}
