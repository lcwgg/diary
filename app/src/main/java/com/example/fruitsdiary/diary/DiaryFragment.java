package com.example.fruitsdiary.diary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruitsdiary.FruitsDiaryAbstractFragment;
import com.example.fruitsdiary.FruitsDiaryApplication;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.data.EntryDataSource;
import com.example.fruitsdiary.data.EntryRepository;
import com.example.fruitsdiary.data.FruitDataSource;
import com.example.fruitsdiary.databinding.FragmentDiaryBinding;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.network.FruitsDiaryService;
import com.example.fruitsdiary.util.SchedulerProvider;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiaryFragment extends FruitsDiaryAbstractFragment implements DiaryContract.View {

    public static final int FRAGMENT_POSITION = 0;

    private static final String FRAGMENT_NAME = "Diary";

    private FragmentDiaryBinding mBinding;

    private FruitsDiaryService mService;

    private RecyclerView mRecyclerView;

    private EntryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        mRecyclerView = mBinding.entryRecyclerview;
        LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new EntryAdapter();
        mRecyclerView.setAdapter(mAdapter);

        EntryRepository repository = FruitsDiaryApplication.get(context).getAppComponent().entryRepository();

        repository.getAllEntries().subscribe(new Consumer<List<Entry>>() {
            @Override
            public void accept(List<Entry> entries) throws Exception {
                mAdapter.setEntryList(entries);
            }
        });
    }

    @Override
    public void setPresenter(DiaryContract.Presenter presenter) {

    }

    @Override
    public void showEntries() {

    }

    @Override
    public void showEntriesLoadError() {

    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }
}
