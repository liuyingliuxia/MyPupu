package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.fragment.TabCrazyDiscountFragment
import com.mmm.mypupu.ui.fragment.TabFlashSaleFragment
import com.mmm.mypupu.ui.fragment.TabFruitFragment
import com.mmm.mypupu.ui.fragment.TabRecommendFragment
import kotlinx.android.synthetic.main.toolbar_main.*


class ViewPagerAdapter (var context: Context , fm: FragmentManager) : FragmentStatePagerAdapter (fm)  {
    private val mTabTitle = arrayOf(
        R.string.mmm_recommend,
        R.string.mmm_flash,
        R.string.mmm_crazy,
        R.string.mmm_fruit
    )

    var mCrazyFragment : Fragment = TabCrazyDiscountFragment()
    var mFlashFragment :Fragment = TabFlashSaleFragment()
    var mFruitFragment :Fragment = TabFruitFragment ()
    var mRecommend :Fragment = TabRecommendFragment ()

    var mFragmentList :MutableList <Fragment> = mutableListOf(mRecommend,mFlashFragment,mCrazyFragment,mFruitFragment)

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(mTabTitle[position])
    }

    override fun getCount(): Int {
     return mFragmentList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }
}