package com.example.fruitsdiary.usecase.addeditentry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsdiary.FruitsDiaryApplication
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.FragmentAddEditEntryBinding
import com.example.fruitsdiary.dialog.BaseDialogFragment
import com.example.fruitsdiary.exception.FruitDiaryException
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.OnSelectFruitListener
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddEditEntryFragment : Fragment(), AddEditEntryContract.View, AddEditEntryManager {

    @Inject
    lateinit var mPresenter: AddEditEntryPresenter
    private lateinit var mBinding: FragmentAddEditEntryBinding
    private var mOnAddEditListener: OnAddEditListener? = null
    private var mOnSelectFruitListener: OnSelectFruitListener? = null
    private lateinit var mAdapter: AddEditEntryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_entry, container, false)
        FruitsDiaryApplication[context!!].appComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = AddEditEntryIntent(activity!!.intent)
        val entryState = intent.getEntryState()
        val entry = intent.getEntry()
        mPresenter.setEntryFromDiary(entry)

        val recyclerView = mBinding.fruitRecyclerview
        val manager = LinearLayoutManager(context)
        recyclerView.layoutManager = manager
        mAdapter = AddEditEntryAdapter(object : AddEditEntryAdapter.OnItemClickListener {
            override fun onItemClick(fruitEntry: FruitEntry) {
                mOnSelectFruitListener?.onFruitSelected(fruitEntry)
            }
        })
        recyclerView.adapter = mAdapter

        mBinding.addFruitFab.setOnClickListener {
            mOnAddEditListener?.onAddFruitEntryClick()
        }

        // We need to load the entry only in the case where the user want to view it
        if (entryState == EntryState.VIEW) {
            mPresenter.subscribe()
        }

        // hide the progress bar if the entry is being created
        // and we set the current date
        if (entryState == EntryState.CREATE) {
            mBinding.emptyEntry.visibility = View.VISIBLE
            mBinding.addEditViewswitcher.showNext()
        }
    }

    override fun updateEntryView(entry: Entry) {
        mBinding.addEditViewswitcher.showNext()
        val fruitEntryList = entry.fruitList
        setAdapterFruitEntryList()
        if (fruitEntryList.isEmpty()) {
            mBinding.emptyEntry.visibility = View.VISIBLE
        } else {
            mBinding.emptyEntry.visibility = View.GONE
        }
    }

    override fun onEntrySaved(fruitEntryList: List<FruitEntry>) {
        setAdapterFruitEntryList()
        mOnAddEditListener?.onEntrySaved()
        Snackbar.make(mBinding.root, R.string.fruits_saved, Snackbar.LENGTH_SHORT).show()
    }

    override fun deleteFruitEntry(fruitEntry: FruitEntry) {
        mAdapter.removeFruitEntry(fruitEntry)
        mPresenter.removeFruitEntry(fruitEntry)
    }

    override fun saveEntry() {
        mPresenter.saveEntry()
    }

    override fun deleteEntry() {
        mPresenter.deleteEntry()
    }

    override fun onEntryDeleted() {
        val builder = BaseDialogFragment.Builder()
                .setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_dialog_message))
                .addCancelButton(true)
                .setOnButtonClickListener(object : BaseDialogFragment.OnButtonClickListener {
                    override fun onPositiveClick() {
                        activity!!.finish()
                    }

                    override fun onNegativeClick() {

                    }
                })
        builder.build().show(childFragmentManager)
    }

    fun setOnAddEditListener(onAddEditListener: OnAddEditListener) {
        mOnAddEditListener = onAddEditListener
    }

    fun setOnSelectFruitListener(onSelectFruitListener: OnSelectFruitListener) {
        mOnSelectFruitListener = onSelectFruitListener
    }

    override fun handleNetworkError(exception: FruitDiaryException) {
        BaseDialogFragment.Builder()
                .setError(context!!, exception)
                .build().show(childFragmentManager)
    }

    override fun contains(fruitEntry: FruitEntry): Boolean {
        return mPresenter.contains(fruitEntry)
    }

    override fun getCorrespondingFruitEntry(fruitEntry: FruitEntry): FruitEntry? {
        return mPresenter.getCorrespondingFruitEntry(fruitEntry)
    }

    override fun addOrUpdateFruitEntry(fruitEntry: FruitEntry) {
        if (mPresenter.contains(fruitEntry)) {
            mPresenter.updateFruitEntry(fruitEntry)
        } else {
            mPresenter.addFruitEntry(fruitEntry)
        }
        setAdapterFruitEntryList()
        // in case the list was empty we hide the "warning empty" textview
        mBinding.emptyEntry.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
        mPresenter.setView(null)
    }

    override fun showOverlay() {
        mBinding.editFruitOverlay.visibility = View.VISIBLE
    }

    override fun hideOverlay() {
        mBinding.editFruitOverlay.visibility = View.GONE
    }

    private fun setAdapterFruitEntryList() {
        mAdapter.fruitEntryList = mPresenter.filterFruitEntryList()
    }

    interface OnAddEditListener {
        fun onAddFruitEntryClick()

        fun onEntrySaved()
    }

    companion object {

        val TAG: String = AddEditEntryFragment::class.java.simpleName
    }
}
