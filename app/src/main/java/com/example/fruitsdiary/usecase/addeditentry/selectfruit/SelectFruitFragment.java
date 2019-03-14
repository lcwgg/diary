package com.example.fruitsdiary.usecase.addeditentry.selectfruit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.FruitsDiaryApplication;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentSelectFruitBinding;
import com.example.fruitsdiary.dialog.BaseDialogFragment;
import com.example.fruitsdiary.exception.FruitDiaryException;
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryAbstractFragment;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryActivity;

import java.util.List;

import javax.inject.Inject;

public class SelectFruitFragment extends AddEditEntryAbstractFragment implements SelectFruitContract.View{

    public static final String TAG = SelectFruitFragment.class.getSimpleName();

    private FragmentSelectFruitBinding mBinding;

    private FruitAdapter mFruitAdapter;

    @Inject
    SelectFruitPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_fruit, container, false);
        FruitsDiaryApplication.get(getContext()).getAppComponent().inject(this);
        mPresenter.setView(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = mBinding.fruitListRecyclerview;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        mFruitAdapter = new FruitAdapter(new FruitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fruit fruit) {
                if (mOnAddEditFlowListener != null) {
                    FruitEntry fruitEntry = mPresenter.getFruitEntry(fruit);
                    mOnAddEditFlowListener.onSelectFruitDismissed(fruitEntry);
                }
            }
        });
        recyclerView.setAdapter(mFruitAdapter);

        mBinding.selectFruitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddEditFlowListener != null){
                    mOnAddEditFlowListener.onSelectFruitDismissed(null);
                }
            }
        });
    }

    @Override
    public void showFruitList(List<Fruit> fruitList) {
        mFruitAdapter.setFruitList(fruitList);
    }

    @Override
    public void handleNetworkError(FruitDiaryException exception) {
        new BaseDialogFragment.Builder()
                .setError(getContext(), exception)
                .setOnButtonClickListener(new BaseDialogFragment.OnButtonClickListener() {
                    @Override
                    public void onPositiveClick() {
                        if (mOnAddEditFlowListener != null){
                            mOnAddEditFlowListener.onSelectFruitDismissed(null);
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                })
                .build().show(getChildFragmentManager());
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
}
