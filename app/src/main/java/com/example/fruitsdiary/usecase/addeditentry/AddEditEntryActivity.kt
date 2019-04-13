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
        when {
            supportFragmentManager.findFragmentByTag(SelectFruitFragment.TAG) != null ->
                removeSelectFruitFragment(supportFragmentManager)
            supportFragmentManager.findFragmentByTag(EditFruitFragment.TAG) != null ->
                removeEditFruitFragment(supportFragmentManager)
            else -> super.onBackPressed()
        }
    }

    override fun onAddFruitEntryClick() {
        if (supportFragmentManager.findFragmentByTag(SelectFruitFragment.TAG) == null) {
            addSelectFruitFragment(supportFragmentManager)
        }
    }

    override fun onEntrySaved() {
        setEntryStateToView()
    }

    override fun onFruitSelected(originalFruitEntry: FruitEntry?) {
        var fruitEntry: FruitEntry? = null
        if (originalFruitEntry == null) {
            removeSelectFruitFragment(supportFragmentManager)
        } else {
            // if the fruit entry already exists, we will edit the existing one instead
            if (mAddEditEntryManager.contains(originalFruitEntry)) {
                fruitEntry = mAddEditEntryManager.getCorrespondingFruitEntry(originalFruitEntry)
            }
            mEditFruitFragment.setFruitEntry(fruitEntry ?: originalFruitEntry)
            // remove fruit selection fragment to replace it with the fruit edition fragment
            supportFragmentManager.beginTransaction()
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

    companion object {
        private const val NO_ANIMATION = 0
    }

    private fun addSelectFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, NO_ANIMATION)
                .add(R.id.fragment_container, mSelectFruitFragment, SelectFruitFragment.TAG)
                .commit()
        mAddEditEntryManager.showOverlay()
    }

    private fun removeSelectFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(NO_ANIMATION, R.anim.slide_down)
                .remove(mSelectFruitFragment)
                .commit()
        mAddEditEntryManager.hideOverlay()
    }

    private fun removeEditFruitFragment(manager: FragmentManager) {
        manager.beginTransaction()
                .setCustomAnimations(NO_ANIMATION, R.anim.slide_down)
                .remove(mEditFruitFragment)
                .commit()
        mAddEditEntryManager.hideOverlay()
    }

}
