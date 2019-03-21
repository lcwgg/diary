package com.example.fruitsdiary.usecase.addeditentry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;
import com.example.fruitsdiary.usecase.addeditentry.editfruit.EditFruitFragment;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.OnSelectFruitListener;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment;
import com.example.fruitsdiary.util.DateUtils;

public class AddEditEntryActivity extends AppCompatActivity
        implements AddEditEntryFragment.OnAddEditListener,
        OnSelectFruitListener,
        EditFruitFragment.OnEditFruitListener {

    private AddEditEntryManager mAddEditEntryManager;
    private SelectFruitFragment mSelectFruitFragment;
    private EditFruitFragment mEditFruitFragment;
    private @EntryState
    int mEntryState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_entry);

        AddEditEntryIntent intent = new AddEditEntryIntent(getIntent());
        Entry entry = intent.getEntry();
        mEntryState = intent.getEntryState();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (mEntryState == EntryState.CREATE) {
            actionBar.setTitle(DateUtils.getCurrentAppDate());
        } else {
            String date = DateUtils.convertServerDateToAppDate(entry.getDate());
            actionBar.setTitle(date);
        }


        AddEditEntryFragment addEditEntryFragment = new AddEditEntryFragment();
        mAddEditEntryManager = addEditEntryFragment;
        addEditEntryFragment.setOnAddEditListener(this);
        addEditEntryFragment.setOnSelectFruitListener(this);

        mEditFruitFragment = new EditFruitFragment();
        mEditFruitFragment.setOnEditFruitListener(this);

        mSelectFruitFragment = new SelectFruitFragment();
        mSelectFruitFragment.setSelectFruitListener(this);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, addEditEntryFragment, AddEditEntryFragment.TAG)
                .commit();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        switch (mEntryState) {
            case EntryState.EDIT:
                inflater.inflate(R.menu.menu_edit_entry, menu);
                break;
            default:
            case EntryState.CREATE:
                inflater.inflate(R.menu.menu_create_entry, menu);
                break;
            case EntryState.VIEW:
                inflater.inflate(R.menu.menu_view_entry, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                mAddEditEntryManager.deleteEntry();
                return true;
            case R.id.action_save:
                mAddEditEntryManager.saveEntry();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setEntryStateToEdit() {
        if (mEntryState != EntryState.EDIT) {
            mEntryState = EntryState.EDIT;
            invalidateOptionsMenu();
        }
    }

    private void setEntryStateToView() {
        if (mEntryState != EntryState.VIEW) {
            mEntryState = EntryState.VIEW;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag(SelectFruitFragment.TAG) != null) {
            removeSelectFruitFragment(manager);
        } else if (manager.findFragmentByTag(EditFruitFragment.TAG) != null) {
            removeEditFruitFragment(manager);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAddFruitEntryClick() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag(SelectFruitFragment.TAG) == null) {
            addSelectFruitFragment(manager);
        }
    }

    @Override
    public void onEntrySaved() {
        setEntryStateToView();
    }

    @Override
    public void onFruitSelected(@Nullable FruitEntry fruitEntry) {
        FragmentManager manager = getSupportFragmentManager();
        if (fruitEntry == null) {
            removeSelectFruitFragment(manager);
        } else {
            if (mAddEditEntryManager.contains(fruitEntry)) {
                fruitEntry = mAddEditEntryManager.getCorrespondingFruitEntry(fruitEntry);
            }
            mEditFruitFragment.setFruitEntry(fruitEntry);
            // remove fruit selection fragment to replace it with the fruit edition fragment
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                    .remove(mSelectFruitFragment)
                    .add(R.id.fragment_container, mEditFruitFragment, EditFruitFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onFruitEdited(@Nullable FruitEntry fruitEntry) {
        removeEditFruitFragment(getSupportFragmentManager());
        if (fruitEntry != null) {
            mAddEditEntryManager.addOrUpdateFruitEntry(fruitEntry);
            setEntryStateToEdit();
        }
    }

    @Override
    public void onFruitDeleted(@NonNull FruitEntry fruitEntry) {
        mAddEditEntryManager.deleteFruitEntry(fruitEntry);
        setEntryStateToEdit();
        removeEditFruitFragment(getSupportFragmentManager());
    }

    private void addSelectFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0)
                .add(R.id.fragment_container, mSelectFruitFragment, SelectFruitFragment.TAG)
                .commit();
        mAddEditEntryManager.showOverlay();
    }

    private void removeSelectFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mSelectFruitFragment)
                .commit();
        mAddEditEntryManager.hideOverlay();
    }

    private void removeEditFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mEditFruitFragment)
                .commit();
        mAddEditEntryManager.hideOverlay();
    }

}
