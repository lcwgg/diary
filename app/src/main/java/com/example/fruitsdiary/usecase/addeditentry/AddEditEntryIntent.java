package com.example.fruitsdiary.usecase.addeditentry;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.fruitsdiary.model.Entry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState.CREATE;
import static com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState.VIEW;
import static com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState.EDIT;

public class AddEditEntryIntent extends Intent {

    @IntDef({CREATE, VIEW, EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EntryState {
        int CREATE = 0;
        int VIEW = 1;
        int EDIT = 2;
    }

    private static final String ARGS_ENTRY = "ARGS_ENTRY";
    private static final String ARGS_ENTRY_STATE = "ARGS_ENTRY_STATE";

    public AddEditEntryIntent(Context context) {
        super(context, AddEditEntryActivity.class);
        putExtra(ARGS_ENTRY_STATE, CREATE);
    }

    public AddEditEntryIntent(Context context, @Nullable Entry entry) {
        super(context, AddEditEntryActivity.class);
        putExtra(ARGS_ENTRY_STATE, VIEW);
        putExtra(ARGS_ENTRY, entry);
    }

    public AddEditEntryIntent(Intent intent) {
        super(intent);
    }

    public Entry getEntry() {
        return getParcelableExtra(ARGS_ENTRY);
    }

    @EntryState
    public int getEntryState(){
        return getIntExtra(ARGS_ENTRY_STATE, VIEW);
    }
}
