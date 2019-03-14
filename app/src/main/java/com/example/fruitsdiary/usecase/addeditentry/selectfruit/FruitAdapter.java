package com.example.fruitsdiary.usecase.addeditentry.selectfruit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.ViewFruitBinding;
import com.example.fruitsdiary.model.Fruit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {

    private List<Fruit> mFruitList;
    private OnItemClickListener mOnItemClickListener;

    public FruitAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mFruitList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;

    }

    public void setFruitList(List<Fruit> fruitList) {
        mFruitList = fruitList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewFruitBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.view_fruit,
                viewGroup,
                false
        );
        return new FruitViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder fruitViewHolder, int i) {
        Fruit fruit = mFruitList.get(i);
        ViewFruitBinding binding = fruitViewHolder.binding;
        Context context = binding.getRoot().getContext();

        String fruitImageUrl = String.format(
                context.getString(R.string.fruit_image_url),
                fruit.getImage()
        );

        Picasso.get()
                .load(fruitImageUrl)
                .placeholder(R.drawable.fruit_image_placeholder)
                .into(binding.fruitImage);

        binding.fruitName.setText(fruit.getType());

        fruitViewHolder.bind(fruit);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    class FruitViewHolder extends RecyclerView.ViewHolder{

        private ViewFruitBinding binding;

        FruitViewHolder(@NonNull ViewFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Fruit fruit){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(fruit);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Fruit fruit);
    }
}
