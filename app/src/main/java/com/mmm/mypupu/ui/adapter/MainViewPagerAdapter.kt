package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.fragment.*
import kotlinx.android.synthetic.main.toolbar_main.*


class MainViewPagerAdapter (var context: Context , fm: FragmentManager) : FragmentStatePagerAdapter (fm)  {

    var mHomeFragment : Fragment = HomeFragment()
    var mSortFragment :Fragment = SortFragment()
    var mFragmentList :MutableList <Fragment> = mutableListOf(mHomeFragment , mSortFragment)

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }


    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //  super.destroyItem(container, position, `object`)
    }
}