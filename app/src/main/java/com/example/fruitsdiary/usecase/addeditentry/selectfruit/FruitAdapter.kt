package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.ViewFruitBinding
import com.example.fruitsdiary.model.Fruit
import com.squareup.picasso.Picasso
import java.util.*

class FruitAdapter(private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<FruitAdapter.FruitViewHolder>() {

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
        val context = binding.root.context

        setFruitImage(context, fruit.image, binding.fruitImage)

        binding.fruitName.text = fruit.type

        fruitViewHolder.bindClick(fruit)
    }

    private fun setFruitImage(context: Context, fruitImagePath: String?, fruitImageView: ImageView) {
        val fruitImageUrl = String.format(
                context.getString(R.string.fruit_image_url),
                fruitImagePath
        )

        Picasso.get()
                .load(fruitImageUrl)
                .placeholder(R.drawable.fruit_image_placeholder)
                .into(fruitImageView)
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
