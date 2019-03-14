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
import com.example.fruitsdiary.model.Fruit;
import com.example.fruitsdiary.model.FruitEntry;
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryIntent.EntryState;
import com.example.fruitsdiary.usecase.addeditentry.editfruit.EditFruitFragment;
import com.example.fruitsdiary.usecase.addeditentry.selectfruit.SelectFruitFragment;
import com.example.fruitsdiary.util.DateUtils;

import java.util.Calendar;

public class AddEditEntryActivity extends AppCompatActivity {

    private AddEditEntryFragment mAddEditEntryFragment;
    private SelectFruitFragment mSelectFruitFragment;
    private EditFruitFragment mEditFruitFragment;
    private ActionBar mActionBar;
    private Entry mEntry;
    private @EntryState
    int mEntryState;
    private OnAddEditFlow mOnAddEditFlow;

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
        mAddEditEntryFragment.setOnAddEditFlowListener(new OnAddEditFlow());
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, mAddEditEntryFragment, AddEditEntryFragment.TAG)
                .commit();

        mOnAddEditFlow = new OnAddEditFlow();
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

    public class OnAddEditFlow implements OnAddEditFlowListener {

        public void onAddFruitClick() {
            FragmentManager manager = getSupportFragmentManager();
            if (mSelectFruitFragment == null) {
                mSelectFruitFragment = new SelectFruitFragment();
                mSelectFruitFragment.setOnAddEditFlowListener(mOnAddEditFlow);
            }
            if (manager.findFragmentByTag(SelectFruitFragment.TAG) == null) {
                addSelectFruitFragment(manager);
            }
        }

        public void onSelectFruitDismissed(@Nullable Fruit fruit) {
            FragmentManager manager = getSupportFragmentManager();
            if (fruit == null) {
                removeSelectFruitFragment(manager);
            } else {
                if (mEditFruitFragment == null) {
                    mEditFruitFragment = EditFruitFragment.newInstance(FruitEntry.fromFruit(fruit));
                    mEditFruitFragment.setOnAddEditFlowListener(mOnAddEditFlow);
                }
                // remove fruit selection fragment to replace it with the fruit edition fragment
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                        .remove(mSelectFruitFragment)
                        .add(R.id.fragment_container, mEditFruitFragment, EditFruitFragment.TAG)
                        .commit();
            }
        }


        @Override
        public void onEditFruitDismissed(@Nullable FruitEntry fruitEntry) {
            removeEditFruitFragment(getSupportFragmentManager());
            if (fruitEntry != null) {
                mAddEditEntryFragment.addFruitEntry(fruitEntry);
            }
        }
    }

    private void addSelectFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0)
                .add(R.id.fragment_container, mSelectFruitFragment, SelectFruitFragment.TAG)
                .commit();
        mAddEditEntryFragment.showOverlay();
    }

    private void removeSelectFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mSelectFruitFragment)
                .commit();
        mAddEditEntryFragment.hideOverlay();
    }

    private void removeEditFruitFragment(FragmentManager manager) {
        manager.beginTransaction()
                .setCustomAnimations(0, R.anim.slide_down)
                .remove(mEditFruitFragment)
                .commit();
        mAddEditEntryFragment.hideOverlay();
    }

    public interface OnAddEditFlowListener {
        void onAddFruitClick();

        void onSelectFruitDismissed(@Nullable Fruit fruit);

        void onEditFruitDismissed(@Nullable FruitEntry fruitEntry);
    }
}
