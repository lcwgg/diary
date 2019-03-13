package com.example.fruitsdiary.usecase.home;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fruitsdiary.FruitsDiaryAbstractFragment;
import com.example.fruitsdiary.usecase.about.AboutFragment;
import com.example.fruitsdiary.usecase.diary.DiaryFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class FruitsDiaryPagerAdapter extends FragmentPagerAdapter {

    private static final int MAX_PAGE = 2;

    private FruitsDiaryAbstractFragment[] mFragmentsArray;

    public FruitsDiaryPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentsArray = new FruitsDiaryAbstractFragment[MAX_PAGE];
        mFragmentsArray[DiaryFragment.FRAGMENT_POSITION] = new DiaryFragment();
        mFragmentsArray[AboutFragment.FRAGMENT_POSITION] = AboutFragment.newInstance(AboutFragment.FRAGMENT_POSITION + 1);
    }

    @Override
    public FruitsDiaryAbstractFragment getItem(int position) {
       return mFragmentsArray[position];
    }

    @Override
    public int getCount() {
        return MAX_PAGE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsArray[position].getFragmentName();
    }
}
