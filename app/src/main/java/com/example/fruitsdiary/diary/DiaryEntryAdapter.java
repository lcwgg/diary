package com.example.fruitsdiary.diary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.EntryViewHolder> {

    private static final String FRUIT_NUMBER_FORMAT = "%1$s %2$s";
    private static final String VITAMIN_NUMBER_FORMAT = "%1$s %2$s";

    private List<Entry> mEntryList;

    public DiaryEntryAdapter() {
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
        List<EntryFruit> entryFruitList = entry.getFruitList();
        Context context = entryViewHolder.dateTextView.getContext();
        TextView fruitNumberView;
        EntryFruit entryFruit;
        String fruitName;

        entryViewHolder.fruitListLayout.removeAllViews();
        entryViewHolder.dateTextView.setText(entry.getDate());

        if (entryFruitList.isEmpty()) {
            fruitNumberView = new TextView(context);
            fruitNumberView.setText(R.string.no_fruit_saved);
            entryViewHolder.fruitListLayout.addView(fruitNumberView);
            entryViewHolder.vitaminsSeparatorView.setVisibility(View.GONE);
            entryViewHolder.vitaminsTextView.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < entryFruitList.size(); i++) {
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

            int vitamins = entry.getVitamins();
            entryViewHolder.vitaminsSeparatorView.setVisibility(View.VISIBLE);
            entryViewHolder.vitaminsTextView.setVisibility(View.VISIBLE);
            String vitaminText = context.getResources().getQuantityString(
                    R.plurals.vitamins,
                    vitamins
            );
            entryViewHolder.vitaminsTextView.setText(
                    String.format(VITAMIN_NUMBER_FORMAT, vitamins, vitaminText)
            );
        }
    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView vitaminsTextView;
        View vitaminsSeparatorView;
        LinearLayout fruitListLayout;

        public EntryViewHolder(EntryViewBinding binding) {
            super(binding.getRoot());
            dateTextView = binding.entryDate;
            vitaminsTextView = binding.entryVitamins;
            vitaminsSeparatorView = binding.entryVitaminsSeparator;
            fruitListLayout = binding.fruitList;
        }
    }
}
