package com.example.fruitsdiary.usecase.diary

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.fruitsdiary.FruitsDiaryApplication
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.FragmentDiaryBinding
import com.example.fruitsdiary.dialog.BaseDialogFragment
import com.example.fruitsdiary.exception.FruitDiaryException
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent
import com.example.fruitsdiary.usecase.home.HomeAbstractFragment

import javax.inject.Inject

/*
    Fragment responsible for showing the diary entries
    Redirects to the Edit Entry activity and the Add entry activity
 */
class DiaryFragment : HomeAbstractFragment(), DiaryContract.View {

    @Inject
    lateinit var mPresenter: DiaryPresenter

    private lateinit var mBinding: FragmentDiaryBinding

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: DiaryEntryAdapter

    private var mTodayEntry: Entry? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary, container, false)
        FruitsDiaryApplication[context!!].appComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = mBinding.entryRecyclerview
        val manager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = manager
        mAdapter = DiaryEntryAdapter(OnEntryClickListener())
        mRecyclerView.adapter = mAdapter

        mBinding.reloadButton.setOnClickListener {
            mBinding.diaryViewSwitcher.showPrevious()
            mBinding.diaryProgress.show()
            mPresenter.subscribe()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.unsubscribe()
    }

    override fun showEntries(entryList: List<Entry>) {
        mBinding.diaryProgress.hide()
        mAdapter.setEntryList(entryList)
    }

    override fun setTodayEntry(entry: Entry?) {
        mTodayEntry = entry
    }

    override fun handleNetworkError(exception: FruitDiaryException) {
        BaseDialogFragment.Builder()
                .setError(context!!, exception)
                .build().show(childFragmentManager)
        mBinding.diaryViewSwitcher.showNext()
    }

    override fun setFabAction(fab: FloatingActionButton) {
        fab.setImageResource(android.R.drawable.ic_input_add)
        fab.setOnClickListener {
            if (mTodayEntry == null) {
                startActivity(AddEditEntryIntent(context))
            } else {
                startActivity(AddEditEntryIntent(context, mTodayEntry))
                mTodayEntry = null // set to null so that if the today's entry is deleted, it's not kept in memory
            }
        }
    }

    override fun getFragmentName(): String {
        return FRAGMENT_NAME
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.setView(null)
    }

    private inner class OnEntryClickListener : DiaryEntryAdapter.OnItemClickListener {
        override fun onItemClick(entry: Entry) {
            startActivity(AddEditEntryIntent(context, entry))
        }
    }

    companion object {
        private const val FRAGMENT_NAME = "Diary"
    }
}
