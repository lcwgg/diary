package com.example.fruitsdiary.dagger;

import com.example.fruitsdiary.data.EntryDataSource;
import com.example.fruitsdiary.data.EntryRepository;
import com.example.fruitsdiary.data.FruitDataSource;
import com.example.fruitsdiary.diary.DiaryFragment;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface AppComponent {
    FruitsDiaryService service();
    SchedulerProvider schedulerProvider();
    FruitDataSource fruitDataSource();
    EntryDataSource entryDataSource();
    EntryRepository entryRepository();

    void inject(DiaryFragment fragment);
}
