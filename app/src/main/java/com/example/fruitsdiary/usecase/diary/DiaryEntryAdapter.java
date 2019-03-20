package com.example.fruitsdiary.usecase.diary;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.ViewEntryBinding;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.EntryViewHolder> {

    private List<Entry> mEntryList;
    private OnItemClickListener mOnItemClickListener;

    public DiaryEntryAdapter(OnItemClickListener onItemClickListener) {
        mEntryList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    public void setEntryList(List<Entry> entryList) {
        mEntryList = entryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewEntryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.view_entry,
                viewGroup,
                false
        );
        return new EntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int position) {

        ViewEntryBinding binding = entryViewHolder.binding;
        Entry entry = mEntryList.get(position);
        List<FruitEntry> fruitEntryList = entry.getFruitList();
        Context context = binding.getRoot().getContext();

        binding.fruitListLayout.removeAllViews();
        binding.entryDate.setText(entry.getDate());

        if (fruitEntryList.isEmpty()) {
            TextView fruitNumberView = new TextView(context);
            fruitNumberView.setText(R.string.no_fruit_saved);
            binding.fruitListLayout.addView(fruitNumberView);
            binding.entryVitaminsSeparator.setVisibility(View.GONE);
            binding.entryVitamins.setVisibility(View.GONE);
        } else {
            setFruitList(context, fruitEntryList, binding);
            setVitamins(context, entry, binding);
        }

        entryViewHolder.bind(entry, mOnItemClickListener);
    }

    private void setFruitList(Context context, List<FruitEntry> fruitEntryList, ViewEntryBinding binding) {
        TextView fruitNumberView;
        FruitEntry fruitEntry;
        String fruitName;
        for (int i = 0; i < fruitEntryList.size(); i++) {
            fruitEntry = fruitEntryList.get(i);
            fruitName = StringUtils.INSTANCE.getCorrectFruitSpelling(
                    context,
                    fruitEntry.getAmount(),
                    fruitEntry.getType()
            );
            fruitNumberView = new TextView(context);
            fruitNumberView.setText(
                    String.format(StringUtils.FRUIT_NUMBER_FORMAT, fruitEntry.getAmount(), fruitName)
            );
            binding.fruitListLayout.addView(fruitNumberView);
        }
    }

    private void setVitamins(Context context, Entry entry, ViewEntryBinding binding) {
        int vitamins = entry.getVitamins();
        binding.entryVitaminsSeparator.setVisibility(View.VISIBLE);
        binding.entryVitamins.setVisibility(View.VISIBLE);
        String vitaminText = context.getResources().getQuantityString(
                R.plurals.vitamins,
                vitamins
        );
        binding.entryVitamins.setText(
                String.format(StringUtils.VITAMIN_NUMBER_FORMAT, vitamins, vitaminText)
        );
    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {

        ViewEntryBinding binding;

        public EntryViewHolder(ViewEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Entry entry, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(entry);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entry entry);
    }
}
