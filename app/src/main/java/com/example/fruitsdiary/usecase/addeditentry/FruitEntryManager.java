package com.example.fruitsdiary.usecase.addeditentry;

import com.example.fruitsdiary.model.FruitEntry;

public interface FruitEntryManager {
    boolean contains(FruitEntry fruitEntry);

    FruitEntry getCorrespondingFruitEntry(FruitEntry fruitEntry);

    void addOrUpdateFruitEntry(FruitEntry fruitEntry);

    void showOverlay();

    void hideOverlay();
}
