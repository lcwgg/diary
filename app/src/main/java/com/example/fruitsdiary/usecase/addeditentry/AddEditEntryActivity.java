package com.example.fruitsdiary.usecase.addeditentry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;
import com.example.fruitsdiary.util.DateUtils;

public class AddEditEntryActivity extends AppCompatActivity {

    private AddEditEntryFragment mAddEditEntryFragment;
    private Entry mEntry;
    private @EntryState
    int mEntryState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_entry);

        AddEditEntryIntent intent = new AddEditEntryIntent(getIntent());
        mEntry = intent.getEntry();
        mEntryState = intent.getEntryState();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (mEntryState == EntryState.CREATE) {
            actionBar.setTitle(DateUtils.getCurrentAppDate());
        } else {
            String date = DateUtils.convertServerDateToAppDate(mEntry.getDate());
            actionBar.setTitle(date);
        }
        mAddEditEntryFragment = new AddEditEntryFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(mAddEditEntryFragment, AddEditEntryFragment.TAG)
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
            case R.id.action_change_date:
                return true;
            case R.id.action_delete:
                return true;
            case R.id.action_save:
                mEntryState = EntryState.VIEW;
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
