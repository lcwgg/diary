package com.example.fruitsdiary.usecase.diary

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.ViewEntryBinding
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.util.StringUtils
import java.util.*

class DiaryEntryAdapter(private val mOnItemClickListener: OnItemClickListener) : RecyclerView.Adapter<DiaryEntryAdapter.EntryViewHolder>() {

    private var mEntryList: List<Entry> = ArrayList()

    fun setEntryList(entryList: List<Entry>) {
        mEntryList = entryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EntryViewHolder {
        val binding = DataBindingUtil.inflate<ViewEntryBinding>(
                LayoutInflater.from(viewGroup.context),
                R.layout.view_entry,
                viewGroup,
                false
        )
        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(entryViewHolder: EntryViewHolder, position: Int) {

        val binding = entryViewHolder.binding
        val entry = mEntryList[position]
        val fruitEntryList = entry.fruitList
        val context = binding.root.context

        binding.fruitListLayout.removeAllViews()
        binding.entryDate.text = entry.date

        if (fruitEntryList.isEmpty()) {
            val fruitNumberView = TextView(context)
            fruitNumberView.setText(R.string.no_fruit_saved)
            binding.fruitListLayout.addView(fruitNumberView)
            binding.entryVitaminsSeparator.visibility = View.GONE
            binding.entryVitamins.visibility = View.GONE
        } else {
            setFruitList(context, fruitEntryList, binding)
            setVitamins(context, entry, binding)
        }

        entryViewHolder.bindClick(entry, mOnItemClickListener)
    }

    private fun setFruitList(context: Context, fruitEntryList: List<FruitEntry>, binding: ViewEntryBinding) {
        var fruitNumberView: TextView
        var fruitName: String
        for (fruitEntry in fruitEntryList) {
            fruitName = StringUtils.getCorrectFruitSpelling(
                    context,
                    fruitEntry.amount,
                    fruitEntry.type
            )
            fruitNumberView = TextView(context)
            fruitNumberView.text = "${fruitEntry.amount}  $fruitName"
            binding.fruitListLayout.addView(fruitNumberView)
        }
    }

    private fun setVitamins(context: Context, entry: Entry, binding: ViewEntryBinding) {
        val vitamins = entry.vitamins
        binding.entryVitaminsSeparator.visibility = View.VISIBLE
        binding.entryVitamins.visibility = View.VISIBLE
        val vitaminText = context.resources.getQuantityString(
                R.plurals.vitamins,
                vitamins
        )
        binding.entryVitamins.text = "$vitamins $vitaminText"
    }

    override fun getItemCount(): Int {
        return mEntryList.size
    }

    class EntryViewHolder(internal var binding: ViewEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindClick(entry: Entry, onItemClickListener: OnItemClickListener?) {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(entry)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(entry: Entry)
    }
}
