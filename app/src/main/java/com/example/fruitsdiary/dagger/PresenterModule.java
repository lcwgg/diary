package com.example.fruitsdiary.dagger;

import android.content.Context;

import com.example.fruitsdiary.data.EntryRepository;
import com.example.fruitsdiary.diary.DiaryPresenter;
import com.example.fruitsdiary.util.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    DiaryPresenter getDiaryPresenter(EntryRepository entryRepository){
        return new DiaryPresenter(entryRepository);
    }
}
