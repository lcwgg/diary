package com.example.fruitsdiary.usecase.addeditentry.editfruit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentEditFruitBinding;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryAbstractFragment;
import com.example.fruitsdiary.util.StringUtils;

public class EditFruitFragment extends AddEditEntryAbstractFragment {

    public static final String TAG = EditFruitFragment.class.getSimpleName();

    private static final String ARGS_FRUIT_ENTRY = "ARGS_FRUIT_ENTRY";
    private static final int FRUIT_MINIMUM_AMOUNT = 1;

    private FragmentEditFruitBinding mBinding;
    private FruitEntry mFruitEntry;
    private boolean mIsPlural;

    public static EditFruitFragment newInstance(@NonNull FruitEntry fruitEntry) {
        EditFruitFragment fragment = new EditFruitFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_FRUIT_ENTRY, fruitEntry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFruitEntry = getArguments().getParcelable(ARGS_FRUIT_ENTRY);
        mIsPlural = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_fruit, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.fruitName.setText(mFruitEntry.getType());
        setFruitAmount(FRUIT_MINIMUM_AMOUNT);
        mBinding.fruitAmount.addTextChangedListener(new FruitNumberWatcher());

        View.OnClickListener onDismissClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddEditFlowListener != null){
                    mOnAddEditFlowListener.onEditFruitDismissed(null);
                }
            }
        };

        mBinding.editFruitLayout.setOnClickListener(onDismissClick);
        mBinding.cancelAddFruit.setOnClickListener(onDismissClick);
        mBinding.doneAddFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddEditFlowListener != null){
                    mFruitEntry.setAmount(getFruitAmount());
                    mOnAddEditFlowListener.onEditFruitDismissed(mFruitEntry);
                }
            }
        });

        mBinding.addFruitsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = getFruitAmount();
                setFruitAmount(++amount);
            }
        });

        mBinding.removeFruitsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = getFruitAmount();
                if (amount > FRUIT_MINIMUM_AMOUNT) {
                    setFruitAmount(--amount);
                }
            }
        });

    }

    private int getFruitAmount(){
        String amount = mBinding.fruitAmount.getText().toString();
        return Integer.valueOf(amount);
    }

    private void setFruitAmount(int amount){
        mBinding.fruitAmount.setText(String.valueOf(amount));
    }

    private class FruitNumberWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty()) {
                int number = Integer.valueOf(s.toString());
                if (number > FRUIT_MINIMUM_AMOUNT && !mIsPlural) {
                    mIsPlural = true;
                    mBinding.fruitName.setText(
                            StringUtils.getCorrectFruitSpelling(getContext(), number, mFruitEntry.getType()));
                } else if (number <= FRUIT_MINIMUM_AMOUNT){
                    mIsPlural = false;
                    mBinding.fruitName.setText(mFruitEntry.getType());
                }
            }
        }
    }

}
