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
import com.example.fruitsdiary.databinding.FragmentAddEditEntryBinding;
import com.example.fruitsdiary.dialog.BaseDialogFragment;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;

import java.util.List;

import javax.inject.Inject;

public class AddEditEntryFragment extends Fragment
        implements AddEditEntryContract.View, AddEditEntryManager {

    public static final String TAG = AddEditEntryFragment.class.getSimpleName();

    @Inject
    AddEditEntryPresenter mPresenter;
    private FragmentAddEditEntryBinding mBinding;
    private OnAddFruitClickListener mOnAddFruitClickListener;
    @EntryState
    private int mEntryState;
    private FruitEntryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_entry, container, false);
        FruitsDiaryApplication.get(getContext()).getAppComponent().inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddEditEntryIntent intent = new AddEditEntryIntent(getActivity().getIntent());
        mEntryState = intent.getEntryState();
        Entry entry = intent.getEntry();
        mPresenter.setEntryFromDiary(entry);

        RecyclerView recyclerView = mBinding.fruitRecyclerview;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        mAdapter = new FruitEntryAdapter();
        recyclerView.setAdapter(mAdapter);

        mBinding.addFruitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddFruitClickListener != null) {
                    mOnAddFruitClickListener.onAddFruitClick();
                }
            }
        });

        // We need to load the entry only in the case where the user want to view it
        if (mEntryState == EntryState.VIEW) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void setEntry(Entry entry) {
        mBinding.addEditViewswitcher.showNext();
        List<FruitEntry> fruitEntryList = entry.getFruitList();
        mAdapter.setFruitEntryList(fruitEntryList);
        if (fruitEntryList.isEmpty()){
            mBinding.emptyEntry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateEntryDate(String date) {
        mPresenter.updateEntryDate(date);
    }

    @Override
    public void saveEntry() {
        mPresenter.setFruitEntryList(mAdapter.getFruitEntryList());
        mPresenter.saveEntry();
    }

    public void setOnAddFruitClickListener(OnAddFruitClickListener onAddFruitClickListener) {
        mOnAddFruitClickListener = onAddFruitClickListener;
    }

    @Override
    public void handleNetworkError(FruitDiaryException exception) {
        new BaseDialogFragment.Builder()
                .setError(getContext(), exception)
                .build().show(getChildFragmentManager());
    }

    @Override
    public boolean contains(FruitEntry fruitEntry) {
        return mAdapter.contains(fruitEntry);
    }

    @Override
    public FruitEntry getCorrespondingFruitEntry(FruitEntry fruitEntry) {
        return mAdapter.getFruitEntry(fruitEntry);
    }

    @Override
    public void addOrUpdateFruitEntry(FruitEntry fruitEntry) {
        if (mAdapter.contains(fruitEntry)) {
            mAdapter.updateFruitEntry(fruitEntry);
        } else {
            mAdapter.addFruitEntry(fruitEntry);
        }
        // in case the list was empty we hide the "warning empty" textview
        mBinding.emptyEntry.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter.setView(null);
    }

    @Override
    public void showOverlay() {
        mBinding.editFruitOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOverlay() {
        mBinding.editFruitOverlay.setVisibility(View.GONE);
    }

    public interface OnAddFruitClickListener {
        void onAddFruitClick();
    }
}
