package com.mmm.mypupu.ui.main

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


class SortVpAdapter (var context: Context , fm: FragmentManager) : FragmentStatePagerAdapter (fm)  {
    private val mTabTitle = arrayOf(R.string.mmm_hotpot_festival, R.string.mmm_fruit, R.string.mmm_vegetables, R.string.mmm_eggs, R.string.mmm_seafood,
        R.string.mmm_condiment,R.string.mmm_breakfast,R.string.mmm_frozen,R.string.mmm_milk,R.string.mmm_drinks ,R.string.mmm_snacks,R.string.mmm_clean,
        R.string.mmm_makeup,R.string.mmm_mother,R.string.mmm_pets,R.string.mmm_import,R.string.mmm_specialty
        )

    var mCrazyFragment : Fragment = TabCrazyDiscountFragment()
    var mFlashFragment :Fragment = TabFlashSaleFragment()
    var mFruitFragment :Fragment = TabFruitFragment ()
    var mRecommend :Fragment = TabRecommendFragment ()

    var mFragmentList :MutableList <Fragment > = mutableListOf(mRecommend,mFlashFragment,mCrazyFragment,mFruitFragment)

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(mTabTitle[position])

    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

}