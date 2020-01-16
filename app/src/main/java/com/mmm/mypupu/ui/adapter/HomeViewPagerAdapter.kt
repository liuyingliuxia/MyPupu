package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.TabTitleBean
import com.mmm.mypupu.ui.fragment.TabCrazyDiscountFragment
import com.mmm.mypupu.ui.fragment.TabFlashSaleFragment
import com.mmm.mypupu.ui.fragment.TabFruitFragment
import com.mmm.mypupu.ui.fragment.TabRecommendFragment
import kotlinx.android.synthetic.main.toolbar_main.*


class HomeViewPagerAdapter(var context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val mTabTitleList: ArrayList<TabTitleBean> = arrayListOf()
    private val tabTitleBean1 = TabTitleBean(0, R.string.mmm_recommend)
    private val tabTitleBean2 = TabTitleBean(0, R.string.mmm_flash)
    private val tabTitleBean3 = TabTitleBean(0, R.string.mmm_crazy)
    private val tabTitleBean4 = TabTitleBean(0, R.string.mmm_fruit)

    init {
        mTabTitleList.add(tabTitleBean1)
        mTabTitleList.add(tabTitleBean2)
        mTabTitleList.add(tabTitleBean3)
        mTabTitleList.add(tabTitleBean4)
    }

    private var mCrazyFragment: Fragment = TabCrazyDiscountFragment()
    private var mFlashFragment: Fragment = TabFlashSaleFragment()
    private var mFruitFragment: Fragment = TabFruitFragment()
    private var mRecommend: Fragment = TabRecommendFragment()

    private var mFragmentList: MutableList<Fragment> = mutableListOf(mRecommend, mFlashFragment, mCrazyFragment, mFruitFragment)

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(mTabTitleList[position].titleId)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //  super.destroyItem(container, position, `object`)
    }
}