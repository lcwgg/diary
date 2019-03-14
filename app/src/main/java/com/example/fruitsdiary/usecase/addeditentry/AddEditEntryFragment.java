package com.example.fruitsdiary.usecase.addeditentry;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentFruitEntryBinding;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;

public class AddEditEntryFragment extends AddEditEntryAbstractFragment {

    public static final String TAG = AddEditEntryFragment.class.getSimpleName();

    private FragmentFruitEntryBinding mBinding;
    private Entry mEntry;
    @EntryState
    private int mEntryState;
    private FruitEntryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddEditEntryIntent intent = new AddEditEntryIntent(getActivity().getIntent());
        mEntryState = intent.getEntryState();
        if (mEntryState == EntryState.VIEW) {
            mEntry = intent.getEntry();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fruit_entry, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = mBinding.fruitRecyclerview;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        mAdapter = new FruitEntryAdapter();
        recyclerView.setAdapter(mAdapter);

        if (mEntryState == EntryState.VIEW) {
            mAdapter.setFruitEntryList(mEntry.getFruitList());
        }

        mBinding.addFruitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddEditFlowListener != null) {
                    mOnAddEditFlowListener.onAddFruitClick();
                }
            }
        });
    }

    public boolean contains(FruitEntry fruitEntry){
        return mAdapter.contains(fruitEntry);
    }

    public FruitEntry getCurrentFruitEntry(FruitEntry fruitEntry){
        return mAdapter.getFruitEntry(fruitEntry);
    }

    public void addOrUpdateFruitEntry(FruitEntry fruitEntry) {
        if (mAdapter.contains(fruitEntry)){
            mAdapter.updateFruitEntry(fruitEntry);
        } else {
            mAdapter.addFruitEntry(fruitEntry);
        }
    }

    public void showOverlay() {
        mBinding.editFruitOverlay.setVisibility(View.VISIBLE);
    }

    public void hideOverlay() {
        mBinding.editFruitOverlay.setVisibility(View.GONE);
    }
}
