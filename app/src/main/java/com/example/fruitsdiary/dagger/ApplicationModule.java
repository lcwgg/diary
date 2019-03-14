package com.example.fruitsdiary.dagger;

import android.content.Context;

import com.example.fruitsdiary.util.SchedulerProvider;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context.getApplicationContext();
    }

    @Provides
    Context getContext() {
        return mContext;
    }

    @Provides
    SchedulerProvider getSchedulerProvider(){
        return new SchedulerProvider();
    }

}
