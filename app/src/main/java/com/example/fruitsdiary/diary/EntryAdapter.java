package com.example.fruitsdiary.diary;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.EntryViewBinding;
import com.example.fruitsdiary.model.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

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
    public void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int i) {
        entryViewHolder.textView.setText(mEntryList.get(i).getId() + "");
    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public EntryViewHolder(EntryViewBinding binding) {
            super(binding.getRoot());
            textView = binding.sectionLabel;
        }
    }
}
