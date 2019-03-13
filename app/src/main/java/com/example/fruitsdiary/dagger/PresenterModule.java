package com.example.fruitsdiary.dagger;

import com.example.fruitsdiary.data.entry.EntryRepository;
import com.example.fruitsdiary.usecase.diary.DiaryPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    DiaryPresenter getDiaryPresenter(EntryRepository entryRepository){
        return new DiaryPresenter(entryRepository);
    }
}
