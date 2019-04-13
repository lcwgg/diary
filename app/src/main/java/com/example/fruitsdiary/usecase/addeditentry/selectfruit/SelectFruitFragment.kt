package com.example.fruitsdiary.usecase.addeditentry.selectfruit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.example.fruitsdiary.FruitsDiaryApplication
import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.FragmentSelectFruitBinding
import com.example.fruitsdiary.dialog.BaseDialogFragment
import com.example.fruitsdiary.exception.FruitDiaryException
import com.example.fruitsdiary.model.Fruit

import javax.inject.Inject

class SelectFruitFragment : Fragment(), SelectFruitContract.View {

    private lateinit var mBinding: FragmentSelectFruitBinding

    private lateinit var mSelectFruitAdapter: SelectFruitAdapter

    private var mSelectFruitListener: OnSelectFruitListener? = null

    @Inject
    lateinit var mPresenter: SelectFruitPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_fruit, container, false)
        FruitsDiaryApplication[context!!].appComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = mBinding.fruitListRecyclerview
        val layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        mSelectFruitAdapter = SelectFruitAdapter(object : SelectFruitAdapter.OnItemClickListener {
            override fun onItemClick(fruit: Fruit) {
                val fruitEntry = mPresenter.getFruitEntry(fruit)
                mSelectFruitListener?.onFruitSelected(fruitEntry)
            }
        })
        recyclerView.adapter = mSelectFruitAdapter

        mBinding.selectFruitLayout.setOnClickListener {
            mSelectFruitListener?.onFruitSelected(null)
        }
    }

    fun setSelectFruitListener(selectFruitListener: OnSelectFruitListener) {
        mSelectFruitListener = selectFruitListener
    }

    override fun showFruitList(fruitList: List<Fruit>) {
        mSelectFruitAdapter.fruitList = fruitList
    }

    override fun handleNetworkError(exception: FruitDiaryException) {
        BaseDialogFragment.Builder()
                .setError(context!!, exception)
                .setOnButtonClickListener(object : BaseDialogFragment.OnButtonClickListener {
                    override fun onPositiveClick() {
                        mSelectFruitListener?.onFruitSelected(null)
                    }

                    override fun onNegativeClick() {

                    }
                })
                .build().show(childFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.setView(null)
    }

    companion object {

        val TAG: String = SelectFruitFragment::class.java.simpleName
    }

}
