package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.ViewFruitBinding
import com.example.fruitsdiary.model.Fruit
import com.squareup.picasso.Picasso
import java.util.*

class SelectFruitAdapter(private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<SelectFruitAdapter.FruitViewHolder>() {

    var fruitList: List<Fruit> = ArrayList()
        set(fruitList) {
            field = fruitList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FruitViewHolder {
        val binding = DataBindingUtil.inflate<ViewFruitBinding>(
                LayoutInflater.from(viewGroup.context),
                R.layout.view_fruit,
                viewGroup,
                false
        )
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(fruitViewHolder: FruitViewHolder, i: Int) {
        val fruit = fruitList[i]
        val binding = fruitViewHolder.binding

        Picasso.get()
                .load(fruit.image)
                .placeholder(R.drawable.fruit_image_placeholder)
                .into(binding.fruitImage)

        binding.fruitName.text = fruit.type

        fruitViewHolder.bindClick(fruit)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    inner class FruitViewHolder(val binding: ViewFruitBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindClick(fruit: Fruit) {
            itemView.setOnClickListener { mOnItemClickListener.onItemClick(fruit) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(fruit: Fruit)
    }
}
