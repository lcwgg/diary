package com.example.fruitsdiary.usecase.home


import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fruitsdiary.usecase.about.AboutFragment
import com.example.fruitsdiary.usecase.diary.DiaryFragment

class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentsArray: Array<HomeAbstractFragment> = arrayOf(DiaryFragment(), AboutFragment())

    override fun getItem(position: Int): HomeAbstractFragment {
        return mFragmentsArray[position]
    }

    override fun getCount(): Int {
        return MAX_PAGE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentsArray[position].getFragmentName()
    }

    companion object {
        private const val MAX_PAGE = 2
    }
}
