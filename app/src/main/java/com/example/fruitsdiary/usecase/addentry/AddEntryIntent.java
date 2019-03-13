package com.example.fruitsdiary.usecase.addentry;

import android.content.Context;
import android.content.Intent;

public class AddEntryIntent extends Intent {

    public AddEntryIntent(Context packageContext) {
        super(packageContext, AddEntryActivity.class);
    }
}
