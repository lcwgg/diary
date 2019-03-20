package com.example.fruitsdiary.usecase.addeditentry.editfruit

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.FragmentEditFruitBinding
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.util.StringUtils

class EditFruitFragment : Fragment() {

    private lateinit var mBinding: FragmentEditFruitBinding
    private lateinit var mFruitEntry: FruitEntry
    private var mOnEditFruitListener: OnEditFruitListener? = null
    private var mIsPlural: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIsPlural = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_fruit, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.fruitAmount.addTextChangedListener(FruitNumberWatcher())

        val onDismissClick = View.OnClickListener {
            mOnEditFruitListener?.onFruitEdited(null)
        }

        // Dismiss the fragment if the user clicks outside the edit box
        mBinding.editFruitLayout.setOnClickListener(onDismissClick)
        mBinding.cancelAddFruit.setOnClickListener(onDismissClick)
        mBinding.doneAddFruit.setOnClickListener {
            if (mOnEditFruitListener != null) {
                mFruitEntry.amount = getFruitAmount()
                mOnEditFruitListener?.onFruitEdited(mFruitEntry)
            }
        }

        mBinding.addFruitsAction.setOnClickListener {
            var amount = getFruitAmount()
            setFruitAmount(++amount)
        }

        mBinding.removeFruitsAction.setOnClickListener {
            var amount = getFruitAmount()
            if (amount > FRUIT_MINIMUM_AMOUNT) {
                setFruitAmount(--amount)
            }
        }

        mBinding.deleteFruit.setOnClickListener { mOnEditFruitListener?.onFruitDeleted(mFruitEntry) }
    }

    override fun onResume() {
        super.onResume()
        setFruitView()
    }

    fun setFruitEntry(fruitEntry: FruitEntry) {
        mFruitEntry = fruitEntry
    }

    fun setOnEditFruitListener(onEditFruitListener: OnEditFruitListener) {
        mOnEditFruitListener = onEditFruitListener
    }

    private fun setFruitView() {
        val amount = mFruitEntry.amount
        mBinding.fruitName.text = StringUtils.getCorrectFruitSpelling(context!!, amount, mFruitEntry.type)
        setFruitAmount(amount)
    }

    private fun getFruitAmount(): Int {
        val amount = mBinding.fruitAmount.text.toString()
        return amount.toInt()
    }

    private fun setFruitAmount(amount: Int) = mBinding.fruitAmount.setText(amount.toString())

    interface OnEditFruitListener {
        fun onFruitEdited(fruitEntry: FruitEntry?)
        fun onFruitDeleted(fruitEntry: FruitEntry)
    }

    private inner class FruitNumberWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (!s.toString().isEmpty()) {
                val number = s.toString().toInt()
                if (number > FRUIT_MINIMUM_AMOUNT && !mIsPlural) {
                    mIsPlural = true
                    mBinding.fruitName.text = StringUtils.getCorrectFruitSpelling(context!!, number, mFruitEntry.type)
                } else if (number <= FRUIT_MINIMUM_AMOUNT) {
                    mIsPlural = false
                    mBinding.fruitName.text = mFruitEntry.type
                }
            }
        }
    }

    companion object {

        val TAG : String = EditFruitFragment::class.java.simpleName

        private const val FRUIT_MINIMUM_AMOUNT = 1
    }

}
