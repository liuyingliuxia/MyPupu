package com.mmm.mypupu.ui.main

import android.content.res.Resources
import android.graphics.Color
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
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : FragmentActivity(), SwipeRefreshLayout.OnRefreshListener{

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)

        initViewPager()
        srlMain.setOnRefreshListener(this)
       // tbMain.setOnTabSelectedListener(this)
    }

    private fun initViewPager () {
        val ViewPagerAdapter  = ViewPagerAdapter (this , supportFragmentManager)
        vpMain.adapter = ViewPagerAdapter
        vpMain.setCurrentItem(0)
        tbMain.setupWithViewPager(vpMain)

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
