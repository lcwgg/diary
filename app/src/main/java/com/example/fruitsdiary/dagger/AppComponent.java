package com.example.fruitsdiary.dagger;

import com.example.fruitsdiary.usecase.addeditentry.FruitEntryAdapter;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.FruitAdapter;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment;
import com.example.fruitsdiary.usecase.diary.DiaryFragment;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(DiaryFragment fragment);
    void inject(SelectFruitFragment fragment);
}
