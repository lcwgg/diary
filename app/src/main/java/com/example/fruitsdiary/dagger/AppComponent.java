package com.example.fruitsdiary.dagger;

import com.example.fruitsdiary.usecase.diary.DiaryFragment;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(DiaryFragment fragment);
}
