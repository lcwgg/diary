package com.example.fruitsdiary.usecase.addeditentry;

import android.support.annotation.NonNull;

import com.example.fruitsdiary.model.FruitEntry;

interface AddEditEntryManager {

    void saveEntry();

    void addOrUpdateFruitEntry(FruitEntry fruitEntry);

    void deleteFruitEntry(@NonNull FruitEntry fruitEntry);

    void deleteEntry();

    boolean contains(FruitEntry fruitEntry);

    FruitEntry getCorrespondingFruitEntry(FruitEntry fruitEntry);

    void showOverlay();

    void hideOverlay();
}
