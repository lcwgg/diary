package com.example.fruitsdiary.usecase.addeditentry

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.ViewFruitEntryBinding
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.util.StringUtils
import com.squareup.picasso.Picasso
import java.util.*

class AddEditEntryAdapter constructor(private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<AddEditEntryAdapter.FruitEntryViewHolder>() {

    var fruitEntryList: MutableList<FruitEntry> = ArrayList()
        set(fruitList) {
            field = fruitList
            notifyDataSetChanged()
        }

    fun removeFruitEntry(fruitEntry: FruitEntry) {
        val index = getFruitEntryIndex(fruitEntry)
        fruitEntryList.removeAt(index)
        notifyItemRemoved(index)
    }

    private fun getFruitEntryIndex(fruitEntry: FruitEntry): Int {
        return fruitEntryList.indexOf(fruitEntry)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FruitEntryViewHolder {
        val binding = DataBindingUtil.inflate<ViewFruitEntryBinding>(
                LayoutInflater.from(viewGroup.context),
                R.layout.view_fruit_entry,
                viewGroup,
                false
        )
        return FruitEntryViewHolder(binding)
    }

    override fun onBindViewHolder(fruitEntryViewHolder: FruitEntryViewHolder, i: Int) {
        val fruitEntry = fruitEntryList[i]
        val binding = fruitEntryViewHolder.binding
        val context = binding.root.context

        setFruitImage(context, fruitEntry.image, binding.fruitImage)
        setFruitName(context, fruitEntry, binding.fruit)
        setVitamins(context, fruitEntry, binding.vitamins)

        fruitEntryViewHolder.bindClick(fruitEntry)

        if (fruitEntry.isModified) {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey))
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
        }

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

    private fun setFruitName(context: Context, fruitEntry: FruitEntry, fruitTextView: TextView) {
        val fruitName = StringUtils.getCorrectFruitSpelling(
                context,
                fruitEntry.amount,
                fruitEntry.type
        )
        fruitTextView.text = "${fruitEntry.amount}  $fruitName"
    }

    private fun setVitamins(context: Context, fruitEntry: FruitEntry, vitaminTextView: TextView) {
        val vitamins = fruitEntry.vitamins * fruitEntry.amount
        val vitaminText = context.resources.getQuantityString(
                R.plurals.vitamins,
                vitamins
        )
        vitaminTextView.text = "$vitamins $vitaminText"
    }

    override fun getItemCount(): Int {
        return fruitEntryList.size
    }

    inner class FruitEntryViewHolder(val binding: ViewFruitEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindClick(fruitEntry: FruitEntry) {
            itemView.setOnClickListener { mOnItemClickListener.onItemClick(fruitEntry) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(fruitEntry: FruitEntry)
    }
}
