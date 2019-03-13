package com.example.fruitsdiary.diary;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fruitsdiary.FruitsDiaryAbstractFragment;
import com.example.fruitsdiary.FruitsDiaryApplication;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentDiaryBinding;
import com.example.fruitsdiary.dialog.BaseDialogFragment;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.getfruits.GetFruitsBroadcastReceiver;
import com.example.fruitsdiary.getfruits.GetFruitsIntentService;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;

import java.util.List;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiaryFragment extends FruitsDiaryAbstractFragment implements DiaryContract.View {

    public static final int FRAGMENT_POSITION = 0;

    private static final String FRAGMENT_NAME = "Diary";

    @Inject
    DiaryPresenter mPresenter;

    private FragmentDiaryBinding mBinding;

    private RecyclerView mRecyclerView;

    private DiaryEntryAdapter mAdapter;

    private GetFruitsBroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false);
        FruitsDiaryApplication.get(getContext()).getAppComponent().inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReceiver = new GetFruitsBroadcastReceiver(new GetFruitsBroadcastReceiver.OnFruitListReceived() {
            @Override
            public void onFruitListReceived(List<Fruit> fruitList) {
                Toast.makeText(getContext(), "Got fruits", Toast.LENGTH_SHORT).show();
            }
        });
        getActivity().startService(new Intent(getContext(), GetFruitsIntentService.class));
        mRecyclerView = mBinding.entryRecyclerview;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new DiaryEntryAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mBinding.reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.diaryViewSwitcher.showPrevious();
                mBinding.diaryProgress.show();
                mPresenter.populateEntries();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        IntentFilter filter = new IntentFilter(GetFruitsBroadcastReceiver.ACTION_GET_FRUITS);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void showEntries(List<Entry> entryList) {
        mBinding.diaryProgress.hide();
        mAdapter.setEntryList(entryList);
    }

    @Override
    public void handleError(FruitDiaryException exception) {
        new BaseDialogFragment.Builder()
                .setError(getContext(), exception)
                .build().show(getChildFragmentManager());
        mBinding.diaryViewSwitcher.showNext();
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
}
