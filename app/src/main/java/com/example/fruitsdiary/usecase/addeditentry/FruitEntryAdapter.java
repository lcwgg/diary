package com.example.fruitsdiary.usecase.addeditentry;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.ViewFruitEntryBinding;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.fruitsdiary.util.StringUtils.FRUIT_NUMBER_FORMAT;
import static com.example.fruitsdiary.util.StringUtils.VITAMIN_NUMBER_FORMAT;

public class FruitEntryAdapter extends RecyclerView.Adapter<FruitEntryAdapter.FruitEntryViewHolder> {

    private List<FruitEntry> mFruitEntryList;

    FruitEntryAdapter() {
        mFruitEntryList = new ArrayList<>();
    }

    void setFruitEntryList(@NonNull List<FruitEntry> fruitEntryList) {
        int size = fruitEntryList.size();
        for (int i = 0; i < size; i++) {
            fruitEntryList.get(i).setModified(false);
        }
        mFruitEntryList = fruitEntryList;
        notifyDataSetChanged();
    }

    void addFruitEntry(@NonNull FruitEntry fruitEntry) {
        fruitEntry.setModified(true);
        mFruitEntryList.add(fruitEntry);
        notifyItemInserted(mFruitEntryList.size() - 1);
    }

    void updateFruitEntry(@NonNull FruitEntry fruitEntry) {
        fruitEntry.setModified(true);
        int index = getFruitEntryIndex(fruitEntry);
        mFruitEntryList.set(index, fruitEntry);
        notifyItemChanged(index);
    }

    public List<FruitEntry> getFruitEntryList() {
        return mFruitEntryList;
    }

    boolean contains(FruitEntry fruitEntry) {
        return mFruitEntryList.contains(fruitEntry);
    }

    FruitEntry getFruitEntry(FruitEntry fruitEntry) {
        return mFruitEntryList.get(getFruitEntryIndex(fruitEntry));
    }

    private int getFruitEntryIndex(FruitEntry fruitEntry) {
        return mFruitEntryList.indexOf(fruitEntry);
    }

    @NonNull
    @Override
    public FruitEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewFruitEntryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.view_fruit_entry,
                viewGroup,
                false
        );
        return new FruitEntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitEntryViewHolder fruitEntryViewHolder, int i) {
        FruitEntry fruitEntry = mFruitEntryList.get(i);
        ViewFruitEntryBinding binding = fruitEntryViewHolder.binding;
        Context context = binding.getRoot().getContext();

        String fruitImageUrl = String.format(
                context.getString(R.string.fruit_image_url),
                fruitEntry.getImage()
        );

        Picasso.get()
                .load(fruitImageUrl)
                .placeholder(R.drawable.fruit_image_placeholder)
                .into(binding.fruitImage);

        setFruitName(context, fruitEntry, binding);
        setVitamins(context, fruitEntry, binding);

        if (fruitEntry.isModified()) {
            binding.getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
        } else {
            binding.getRoot().setBackgroundColor(Color.WHITE);
        }

    }

    private void setFruitName(Context context, FruitEntry fruitEntry, ViewFruitEntryBinding binding) {
        String fruitName = StringUtils.getCorrectFruitSpelling(
                context,
                fruitEntry.getAmount(),
                fruitEntry.getType()
        );
        binding.fruit.setText(
                String.format(FRUIT_NUMBER_FORMAT, fruitEntry.getAmount(), fruitName)
        );
    }

    private void setVitamins(Context context, FruitEntry fruitEntry, ViewFruitEntryBinding binding) {
        int vitamins = fruitEntry.getVitamins() * fruitEntry.getAmount();
        String vitaminText = context.getResources().getQuantityString(
                R.plurals.vitamins,
                vitamins
        );
        binding.vitamins.setText(
                String.format(VITAMIN_NUMBER_FORMAT, vitamins, vitaminText)
        );
    }

    @Override
    public int getItemCount() {
        return mFruitEntryList.size();
    }

    class FruitEntryViewHolder extends RecyclerView.ViewHolder {

        private ViewFruitEntryBinding binding;

        FruitEntryViewHolder(@NonNull ViewFruitEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
