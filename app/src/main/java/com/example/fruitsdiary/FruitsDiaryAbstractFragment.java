package com.example.fruitsdiary;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

public abstract class FruitsDiaryAbstractFragment extends Fragment {

    public abstract String getFragmentName();

    public abstract void setFabAction(FloatingActionButton fab);
}
