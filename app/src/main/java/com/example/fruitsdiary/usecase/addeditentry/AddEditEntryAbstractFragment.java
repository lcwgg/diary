package com.example.fruitsdiary.usecase.addeditentry;

import android.support.v4.app.Fragment;

public class AddEditEntryAbstractFragment extends Fragment {

    protected AddEditEntryActivity.OnAddEditFlowListener mOnAddEditFlowListener;


    public void setOnAddEditFlowListener(AddEditEntryActivity.OnAddEditFlowListener onAddEditFlowListener) {
        mOnAddEditFlowListener = onAddEditFlowListener;
    }
}
