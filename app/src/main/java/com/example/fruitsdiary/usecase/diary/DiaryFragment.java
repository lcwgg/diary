package com.example.fruitsdiary.usecase.diary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.FruitsDiaryApplication;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentDiaryBinding;
import com.example.fruitsdiary.dialog.BaseDialogFragment;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent;
import com.example.fruitsdiary.usecase.home.HomeAbstractFragment;

import java.util.List;

import javax.inject.Inject;

/*
    Fragment responsible for showing the diary entries
    Redirects to the Edit Entry activity and the Add entry activity
 */
public class DiaryFragment extends HomeAbstractFragment implements DiaryContract.View {

    public static final int FRAGMENT_POSITION = 0;

    private static final String FRAGMENT_NAME = "Diary";

    @Inject
    DiaryPresenter mPresenter;

    private FragmentDiaryBinding mBinding;

    private RecyclerView mRecyclerView;

    private DiaryEntryAdapter mAdapter;

    private Entry mTodayEntry = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false);
        FruitsDiaryApplication.Companion.get(getContext()).getAppComponent().inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = mBinding.entryRecyclerview;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new DiaryEntryAdapter(new OnEntryClickListener());
        mRecyclerView.setAdapter(mAdapter);

        mBinding.reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.diaryViewSwitcher.showPrevious();
                mBinding.diaryProgress.show();
                mPresenter.subscribe();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showEntries(List<Entry> entryList) {
        mBinding.diaryProgress.hide();
        mAdapter.setEntryList(entryList);
    }

    @Override
    public void setTodayEntry(Entry entry) {
        mTodayEntry = entry;
    }

    @Override
    public void handleNetworkError(FruitDiaryException exception) {
        new BaseDialogFragment.Builder()
                .setError(getContext(), exception)
                .build().show(getChildFragmentManager());
        mBinding.diaryViewSwitcher.showNext();
    }

    @Override
    public void setFabAction(FloatingActionButton fab) {
        fab.setImageResource(android.R.drawable.ic_input_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTodayEntry == null) {
                    startActivity(new AddEditEntryIntent(getContext()));
                } else {
                    startActivity(new AddEditEntryIntent(getContext(), mTodayEntry));
                    mTodayEntry = null; // set to null so that if the today's entry is deleted, it's not kept in memory
                }
            }
        });
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.setView(null);
    }

    private class OnEntryClickListener implements DiaryEntryAdapter.OnItemClickListener {
        @Override
        public void onItemClick(Entry entry) {
            startActivity(new AddEditEntryIntent(getContext(), entry));
        }
    }
}
