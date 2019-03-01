package com.storiesswipeparallax.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private var mFragmentList = mutableListOf<Fragment>()
    private var mFragmentTitleList = mutableListOf<String?>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String?) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun addPages(fragments: MutableList<Fragment>){
        mFragmentList = fragments
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mFragmentTitleList.size < position) {
            ""
        } else
            mFragmentTitleList[position]
    }
}