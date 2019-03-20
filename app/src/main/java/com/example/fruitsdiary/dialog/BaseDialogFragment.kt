package com.example.fruitsdiary.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.example.fruitsdiary.exception.ErrorCommonException
import com.example.fruitsdiary.exception.FruitDiaryException
import com.example.fruitsdiary.util.StringUtils
import io.reactivex.annotations.NonNull

open class BaseDialogFragment : DialogFragment() {

    private lateinit var mTitle: String
    private lateinit var mMessage: String
    private var mHasCancelButton: Boolean = false
    private var mOnButtonClickListener: OnButtonClickListener? = null

    protected fun getDialogTag(): String = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = arguments?.getString(ARGS_TITLE) ?: StringUtils.EMPTY_STRING
        mMessage = arguments?.getString(ARGS_MESSAGE) ?: StringUtils.EMPTY_STRING
        mHasCancelButton = arguments?.getBoolean(ARGS_CANCEL_BUTTON) ?: false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    mOnButtonClickListener?.onPositiveClick()
                    dismiss()
                }
        if (mHasCancelButton) {
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                mOnButtonClickListener?.onNegativeClick()
                dismiss()
            }

        }
        return builder.create()
    }

    fun setOnButtonClickListener(onButtonClickListener: OnButtonClickListener?) {
        mOnButtonClickListener = onButtonClickListener
    }

    fun show(manager: FragmentManager) {
        show(manager, getDialogTag())
    }

    class Builder {
        private var title: String = StringUtils.EMPTY_STRING
        private var message: String = StringUtils.EMPTY_STRING
        private var addCancelButton: Boolean = false
        private var onButtonClickListener: OnButtonClickListener? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun addCancelButton(addCancelButton: Boolean): Builder {
            this.addCancelButton = addCancelButton
            return this
        }

        fun setError(context: Context, exception: FruitDiaryException): Builder {
            title = context.getString(exception.title)
            message = when (exception) {
                is ErrorCommonException -> exception.message ?: StringUtils.EMPTY_STRING
                else -> context.getString(exception.errorMessage)
            }
            return this
        }

        fun setOnButtonClickListener(onButtonClickListener: OnButtonClickListener): Builder {
            this.onButtonClickListener = onButtonClickListener
            return this
        }

        fun build(): BaseDialogFragment {
            val fragment = newInstance(
                    title, message, addCancelButton
            )
            fragment.setOnButtonClickListener(onButtonClickListener)
            return fragment
        }
    }

    interface OnButtonClickListener {
        fun onPositiveClick()

        fun onNegativeClick()
    }

    companion object {

        protected const val ARGS_TITLE = "ARGS_TITLE"
        protected const val ARGS_MESSAGE = "ARGS_MESSAGE"
        protected const val ARGS_CANCEL_BUTTON = "ARGS_CANCEL_BUTTON"

        private val TAG = BaseDialogFragment::class.java.simpleName

        fun newInstance(title: String, message: String, hasCancelButton: Boolean): BaseDialogFragment {
            val fragment = BaseDialogFragment()
            val args = Bundle(3)
            args.putString(ARGS_TITLE, title)
            args.putString(ARGS_MESSAGE, message)
            args.putBoolean(ARGS_CANCEL_BUTTON, hasCancelButton)
            fragment.arguments = args
            return fragment
        }
    }
}
