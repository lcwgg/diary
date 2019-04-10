package com.example.fruitsdiary.usecase.addeditentry

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.fruitsdiary.R
import com.example.fruitsdiary.model.FruitEntry
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState
import com.example.fruitsdiary.usecase.addeditentry.editfruit.EditFruitFragment
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.OnSelectFruitListener
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment
import com.example.fruitsdiary.util.DateUtils

class AddEditEntryActivity : AppCompatActivity(), AddEditEntryFragment.OnAddEditListener, OnSelectFruitListener, EditFruitFragment.OnEditFruitListener {

    private lateinit var mAddEditEntryManager: AddEditEntryManager
    private lateinit var mSelectFruitFragment: SelectFruitFragment
    private lateinit var mEditFruitFragment: EditFruitFragment
    @EntryState
    private var mEntryState: Int = EntryState.VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_entry)

        val intent = AddEditEntryIntent(intent)
        val entry  = intent.getEntry()
        mEntryState = intent.getEntryState()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (mEntryState == EntryState.CREATE) {
            actionBar?.title = DateUtils.getCurrentAppDate()
        } else {
            val date = DateUtils.convertServerDateToAppDate(entry.date)
            actionBar?.title = date
        }


        val addEditEntryFragment = AddEditEntryFragment()
        mAddEditEntryManager = addEditEntryFragment
        addEditEntryFragment.setOnAddEditListener(this)
        addEditEntryFragment.setOnSelectFruitListener(this)

        mEditFruitFragment = EditFruitFragment()
        mEditFruitFragment.setOnEditFruitListener(this)

        mSelectFruitFragment = SelectFruitFragment()
        mSelectFruitFragment.setSelectFruitListener(this)

        val manager = supportFragmentManager
        manager.beginTransaction()
                .add(R.id.fragment_container, addEditEntryFragment, AddEditEntryFragment.TAG)
                .commit()

    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        when (mEntryState) {
            EntryState.EDIT -> inflater.inflate(R.menu.menu_edit_entry, menu)
            EntryState.CREATE -> inflater.inflate(R.menu.menu_create_entry, menu)
            EntryState.VIEW -> inflater.inflate(R.menu.menu_view_entry, menu)
            else -> inflater.inflate(R.menu.menu_view_entry, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_delete -> {
                mAddEditEntryManager.deleteEntry()
                true
            }
            R.id.action_save -> {
                mAddEditEntryManager.saveEntry()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setEntryStateToEdit() {
        if (mEntryState != EntryState.EDIT) {
            mEntryState = EntryState.EDIT
            invalidateOptionsMenu()
        }
    }

    private fun setEntryStateToView() {
        if (mEntryState != EntryState.VIEW) {
            mEntryState = EntryState.VIEW
            invalidateOptionsMenu()
        }
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager
        when {
            manager.findFragmentByTag(SelectFruitFragment.TAG) != null -> removeSelectFruitFragment(manager)
            manager.findFragmentByTag(EditFruitFragment.TAG) != null -> removeEditFruitFragment(manager)
            else -> super.onBackPressed()
        }
    }

    override fun onAddFruitEntryClick() {
        val manager = supportFragmentManager
        if (manager.findFragmentByTag(SelectFruitFragment.TAG) == null) {
            addSelectFruitFragment(manager)
        }
    }

    override fun onEntrySaved() {
        setEntryStateToView()
    }

    override fun onFruitSelected(originalFruitEntry: FruitEntry?) {
        var fruitEntry: FruitEntry? = null
        val manager = supportFragmentManager
        if (originalFruitEntry == null) {
            removeSelectFruitFragment(manager)
        } else {
            // if the fruit entry already exists, we will edit the existing one instead
            if (mAddEditEntryManager.contains(originalFruitEntry)) {
                fruitEntry = mAddEditEntryManager.getCorrespondingFruitEntry(originalFruitEntry)
            }
            mEditFruitFragment.setFruitEntry(fruitEntry ?: originalFruitEntry)
            // remove fruit selection fragment to replace it with the fruit edition fragment
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                    .remove(mSelectFruitFragment)
                    .add(R.id.fragment_container, mEditFruitFragment, EditFruitFragment.TAG)
                    .commit()
            mAddEditEntryManager.showOverlay()
        }
    }

    override fun onFruitEdited(fruitEntry: FruitEntry?) {
        removeEditFruitFragment(supportFragmentManager)
        if (fruitEntry != null) {
            mAddEditEntryManager.addOrUpdateFruitEntry(fruitEntry)
            setEntryStateToEdit()
        }
    }

    override fun onFruitDeleted(fruitEntry: FruitEntry) {
        mAddEditEntryManager.deleteFruitEntry(fruitEntry)
        setEntryStateToEdit()
        removeEditFruitFragment(supportFragmentManager)
    }

    private fun addSelectFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0)
                .add(R.id.fragment_container, mSelectFruitFragment, SelectFruitFragment.TAG)
                .commit()
        mAddEditEntryManager.showOverlay()
    }

    private fun removeSelectFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mSelectFruitFragment)
                .commit()
        mAddEditEntryManager.hideOverlay()
    }

    private fun removeEditFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mEditFruitFragment)
                .commit()
        mAddEditEntryManager.hideOverlay()
    }

}
