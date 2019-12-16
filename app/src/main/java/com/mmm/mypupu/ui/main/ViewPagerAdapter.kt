package com.mmm.mypupu.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.fragment.TabRecommendFragment

private val mTabTitle = arrayOf(
    R.string.mmm_recommend,
    R.string.mmm_flash,
    R.string.mmm_crazy,
    R.string.mmm_fruit
)
class ViewPagerAdapter (var context: Context , fm: FragmentManager) : FragmentPagerAdapter (fm)  {
    override fun getItem(position: Int): Fragment {
        return TabRecommendFragment.newInstance (position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(mTabTitle[position])
    }
    override fun getCount(): Int {
     return 4
    }

}