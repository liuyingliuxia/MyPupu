package com.mmm.mypupu.ui.main

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.searchHint
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : FragmentActivity(), SwipeRefreshLayout.OnRefreshListener{

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.color1), true)

        initViewPager()
        srlMain.setOnRefreshListener(this)

        tvSearch.setOnClickListener{ v ->
            run {
                val intent  = Intent()
                intent.setClass(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }

        initBottomNav()
    }
    private fun initViewPager () {
        val ViewPagerAdapter  = ViewPagerAdapter (this , supportFragmentManager)
        vpMain.adapter = ViewPagerAdapter
        vpMain.setCurrentItem(0)
        tbMain.setupWithViewPager(vpMain)

    }

    private fun initBottomNav () {
        llHome.setOnClickListener { v ->
            run {
                ivHome.setImageResource(R.drawable.icon_home_tab_home_selected)
                ivSort.setImageResource(R.drawable.icon_home_tab_category_normal)
                tvHome.setTextColor(resources.getColor(R.color.color23))
                tvSort.setTextColor(resources.getColor(R.color.color6))
            }

            llSort.setOnClickListener { v ->
                run {
                    ivHome.setImageResource(R.drawable.icon_home_tab_home_normal)
                    ivSort.setImageResource(R.drawable.icon_home_tab_category_selected)
                    tvHome.setTextColor(resources.getColor(R.color.color6))
                    tvSort.setTextColor(resources.getColor(R.color.color23))
                }
            }

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
