package com.mmm.mypupu.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.MainViewPagerAdapter
import com.mmm.mypupu.ui.fragment.HomeFragment
import com.mmm.mypupu.ui.fragment.SortFragment
import kotlinx.android.synthetic.main.activity_main_test.*
import java.util.*

class MainActivity :AppCompatActivity(),View.OnClickListener{
    val mStack = Stack <Fragment> ()
    val mSortFragment by lazy { SortFragment() }
    val mHomeFragment by lazy { HomeFragment() }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.color1), true)

        initViewPager()
        rbHome.setOnClickListener(this)
        rbSort.setOnClickListener(this)

    }

    private fun initViewPager () {
        val MainViewPagerAdapter  = MainViewPagerAdapter(this, supportFragmentManager)
        //设置预加载数 全部的fragment
        nvpMain.offscreenPageLimit = 2
        nvpMain.adapter = MainViewPagerAdapter
        nvpMain.currentItem = 0
    }

    private fun initFragment () {
        val manager = supportFragmentManager.beginTransaction()
        manager.add (R.id.llContainer,mHomeFragment)
        manager.add (R.id.llContainer,mSortFragment)
        manager.commit()
        mStack.add(mHomeFragment)
        mStack.add(mSortFragment)
    }

    private fun changeFragment (position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide (fragment)
        }
        manager.show (mStack[position])
        manager.commit()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.rbHome -> {
                nvpMain.currentItem = 0
            }

            R.id.rbSort -> {
                nvpMain.currentItem = 1
            }
        }
    }


}
