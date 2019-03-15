package com.example.fruitsdiary.usecase.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity {

    private static final int VIEWPAGER_DEFAULT_POSITION = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private HomePagerAdapter mHomePagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = binding.container;
        mViewPager.setAdapter(mHomePagerAdapter);

        binding.diaryTabLayout.setupWithViewPager(mViewPager);

        mHomePagerAdapter.getItem(VIEWPAGER_DEFAULT_POSITION).setFabAction(binding.fab);

        mViewPager.addOnPageChangeListener(new OnPageChangedListener() {

            @Override
            public void onPageSelected(int i) {
                mHomePagerAdapter.getItem(i).setFabAction(binding.fab);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    private abstract class OnPageChangedListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
