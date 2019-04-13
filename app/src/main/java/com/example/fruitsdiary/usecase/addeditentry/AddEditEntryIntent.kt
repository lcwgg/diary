package com.example.fruitsdiary.usecase.addeditentry

import android.content.Context
import android.content.Intent
import com.example.fruitsdiary.model.Entry
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState.Companion.CREATE
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState.Companion.VIEW

class AddEditEntryIntent : Intent {

    annotation class EntryState {
        companion object {
            const val CREATE = 0
            const val VIEW = 1
            const val EDIT = 2
        }
    }

    constructor(context: Context?) : super(context, AddEditEntryActivity::class.java) {
        putExtra(ARGS_ENTRY_STATE, CREATE)
    }

    constructor(context: Context?, entry: Entry?) : super(context, AddEditEntryActivity::class.java) {
        putExtra(ARGS_ENTRY_STATE, VIEW)
        putExtra(ARGS_ENTRY, entry)
    }

    constructor(intent: Intent) : super(intent)

    fun getEntry(): Entry = getParcelableExtra(ARGS_ENTRY)

    @EntryState
    fun getEntryState(): Int = getIntExtra(ARGS_ENTRY_STATE, VIEW)

    companion object {
        private const val ARGS_ENTRY = "ARGS_ENTRY"
        private const val ARGS_ENTRY_STATE = "ARGS_ENTRY_STATE"
    }
}
