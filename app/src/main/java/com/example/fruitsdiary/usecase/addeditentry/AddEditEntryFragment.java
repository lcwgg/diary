package com.example.fruitsdiary.usecase.addeditentry;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.FruitsDiaryApplication;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentFruitEntryBinding;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;

public class AddEditEntryFragment extends Fragment {

    public static final String TAG = AddEditEntryFragment.class.getSimpleName();

    private FragmentFruitEntryBinding mBinding;
    private Entry mEntry;
    private @EntryState int mEntryState;

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
        FruitEntryAdapter adapter = new FruitEntryAdapter(FruitsDiaryApplication.get(getContext()));
        recyclerView.setAdapter(adapter);

        if (mEntry != null) {
            adapter.setFruitEntryList(mEntry.getFruitList());
        }
    }
}
