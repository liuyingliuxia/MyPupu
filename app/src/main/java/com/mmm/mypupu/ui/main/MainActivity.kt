package com.mmm.mypupu.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.View
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.githang.statusbar.StatusBarCompat
import com.google.android.material.tabs.TabLayout
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.searchHint
import com.mmm.mypupu.ui.fragment.TabCrazyDiscountFragment
import com.mmm.mypupu.ui.fragment.TabFlashSaleFragment
import com.mmm.mypupu.ui.fragment.TabFruitFragment
import com.mmm.mypupu.ui.fragment.TabRecommendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class MainActivity : FragmentActivity(),View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private val mStack = Stack <Fragment > ()
    var mTitleList :MutableList <String> = arrayListOf()
    var mCrazyFragment : Fragment = TabCrazyDiscountFragment()
    var mFlashFragment :Fragment = TabFlashSaleFragment()
    var mFruitFragment :Fragment =TabFruitFragment ()
    var mRecommend :Fragment = TabRecommendFragment ()
    var mFragmentList :MutableList <Fragment > = mutableListOf(mCrazyFragment,mFlashFragment,mFruitFragment,mRecommend)


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true);
        val ViewPagerAdapter  = ViewPagerAdapter (this , supportFragmentManager)
        val viewPager : ViewPager = findViewById(R.id.vpMain)
        viewPager.adapter = ViewPagerAdapter
        val tabs :TabLayout = findViewById (R.id.tbMain)
        tabs.setupWithViewPager(viewPager)

        srlMain.setOnRefreshListener(this)

        initView()

    }

    private fun initFragment () {
        val manager = supportFragmentManager.beginTransaction()

    }

    private fun initView () {

        //设置ViewPager
       // mViewPagerAdapter = ViewPagerAdapter(context )
        //设置监听

        mTitleList.add (getString(R.string.mmm_recommend))
        mTitleList.add (getString(R.string.mmm_flash))
        mTitleList.add (getString(R.string.mmm_crazy))
        mTitleList.add (getString(R.string.mmm_fruit))
    }

    override fun onClick(v: View?) {
       when (v?.id) {

       }
    }

    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                var i = (0 until searchHint.size).random()
                tvSearch.text = searchHint [i]
                srlMain.isRefreshing = false
            }
        },2000)
    }
}
