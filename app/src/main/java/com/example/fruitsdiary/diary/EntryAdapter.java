package com.example.fruitsdiary.diary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.EntryViewBinding;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.EntryFruit;
import com.example.fruitsdiary.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private static final String FRUIT_NUMBER_FORMAT = "%1$s %2$s";

    private List<Entry> mEntryList;

    public EntryAdapter(){
        mEntryList = new ArrayList<>();
    }

    public void setEntryList(List<Entry> entryList) {
        mEntryList = entryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        EntryViewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.entry_view,
                viewGroup,
                false
        );
        return new EntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int position) {

        Entry entry = mEntryList.get(position);
        entryViewHolder.dateTextView.setText(entry.getDate());
        List<EntryFruit> entryFruitList = entry.getFruit();
        Context context = entryViewHolder.dateTextView.getContext();
        TextView fruitNumberView;
        EntryFruit entryFruit;
        String fruitName;

        if (entryFruitList.isEmpty()){
            fruitNumberView = new TextView(context);
            fruitNumberView.setText(R.string.no_fruit_saved);
            entryViewHolder.fruitListLayout.addView(fruitNumberView);
        } else {
            for (int i=0; i<entryFruitList.size(); i++){
                entryFruit = entryFruitList.get(i);
                fruitName = StringUtils.getCorrectFruitSpelling(
                        context,
                        entryFruit.getAmount(),
                        entryFruit.getFruitType()
                );
                fruitNumberView = new TextView(context);
                fruitNumberView.setText(
                        String.format(FRUIT_NUMBER_FORMAT, entryFruit.getAmount(), fruitName)
                );
                entryViewHolder.fruitListLayout.addView(fruitNumberView);
            }
        }
    }

    private void addFruitToView(int count, List<EntryFruit> entryFruitList){

    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        LinearLayout fruitListLayout;

        public EntryViewHolder(EntryViewBinding binding) {
            super(binding.getRoot());
            dateTextView = binding.entryDate;
            fruitListLayout = binding.fruitList;

        }
    }
}
