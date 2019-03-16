package com.example.fruitsdiary.dagger;

import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryFragment;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment;
import com.example.fruitsdiary.usecase.diary.DiaryFragment;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(DiaryFragment fragment);
    void inject(SelectFruitFragment fragment);
    void inject(AddEditEntryFragment fragment);
}
