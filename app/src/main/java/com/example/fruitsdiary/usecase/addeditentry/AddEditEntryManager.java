package com.example.fruitsdiary.usecase.addeditentry;

import android.support.annotation.NonNull;

import com.example.fruitsdiary.model.FruitEntry;

public interface AddEditEntryManager {

    void updateEntryDate(String date);

    void saveEntry();

    void deleteFruitEntry(@NonNull FruitEntry fruitEntry);

    boolean contains(FruitEntry fruitEntry);

    FruitEntry getCorrespondingFruitEntry(FruitEntry fruitEntry);

    void addOrUpdateFruitEntry(FruitEntry fruitEntry);

    void showOverlay();

    void hideOverlay();
}
