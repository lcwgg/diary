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
import com.example.fruitsdiary.dialog.DatePickerFragment;
import com.example.fruitsdiary.model.Entry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;
import com.example.fruitsdiary.util.DateUtils;

import java.util.Calendar;

public class AddEditEntryActivity extends AppCompatActivity {

    private AddEditEntryFragment mAddEditEntryFragment;
    private ActionBar mActionBar;
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

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        if (mEntryState == EntryState.CREATE) {
            mActionBar.setTitle(DateUtils.getCurrentAppDate());
        } else {
            String date = DateUtils.convertServerDateToAppDate(mEntry.getDate());
            mActionBar.setTitle(date);
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
                showDatePickerDialog();
                return true;
            case R.id.action_delete:
                return true;
            case R.id.action_save:
                setEntryStateToView();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment dialog;
        if (mEntryState == EntryState.CREATE) {
            dialog = DatePickerFragment.newInstance();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getServerDate(mEntry.getDate()));
            dialog = DatePickerFragment.newInstance(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        dialog.setOnDateSelectedListener(new DatePickerFragment.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                mActionBar.setTitle(DateUtils.getAppStringDate(calendar.getTime()));
                setEntryStateToEdit();
            }
        });

        dialog.show(getSupportFragmentManager());
    }

    private void setEntryStateToEdit() {
        mEntryState = EntryState.EDIT;
        invalidateOptionsMenu();
    }
    private void setEntryStateToView() {
        mEntryState = EntryState.VIEW;
        invalidateOptionsMenu();
    }
}
