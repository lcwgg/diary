package com.example.fruitsdiary.usecase.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.example.fruitsdiary.R
import com.example.fruitsdiary.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var mHomePagerAdapter: HomePagerAdapter

    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)

        val toolbar = binding.toolbarView.toolbar
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        mHomePagerAdapter = HomePagerAdapter(supportFragmentManager)

        mViewPager = binding.container
        mViewPager.adapter = mHomePagerAdapter
        binding.diaryTabLayout.setupWithViewPager(mViewPager)

        mHomePagerAdapter.getItem(VIEWPAGER_DEFAULT_POSITION).setFabAction(binding.fab)

        mViewPager.addOnPageChangeListener(object : OnPageChangedListener() {

            override fun onPageSelected(i: Int) {
                mHomePagerAdapter.getItem(i).setFabAction(binding.fab)
            }
        })
    }

    private abstract inner class OnPageChangedListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {

        }

        override fun onPageScrollStateChanged(i: Int) {

        }
    }

    companion object {
        private const val VIEWPAGER_DEFAULT_POSITION = 0
    }
}
