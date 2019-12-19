package com.mmm.mypupu.ui.main

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.searchHint
import com.mmm.mypupu.ui.fragment.HomeFragment
import com.mmm.mypupu.ui.fragment.SortFragment
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.container_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.text.FieldPosition
import java.util.*

class MainActivity :AppCompatActivity(),View.OnClickListener{
    val mStack = Stack <Fragment> ()
    val mSortFragment by lazy { SortFragment() }
    val mHomeFragment by lazy { HomeFragment() }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.color1), true)
        initFragment()
        changeFragment(0)
       llHome.setOnClickListener (this)
        llSort.setOnClickListener(this)

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
            R.id.llHome -> {
                ivHome.setImageResource(R.drawable.icon_home_tab_home_selected)
                ivSort.setImageResource(R.drawable.icon_home_tab_category_normal)
                tvHome.setTextColor(resources.getColor(R.color.color23))
                tvSort.setTextColor(resources.getColor(R.color.color6))
                changeFragment(0)
            }

            R.id.llSort -> {
                ivHome.setImageResource(R.drawable.icon_home_tab_home_normal)
                ivSort.setImageResource(R.drawable.icon_home_tab_category_selected)
                tvHome.setTextColor(resources.getColor(R.color.color6))
                tvSort.setTextColor(resources.getColor(R.color.color23))
                changeFragment(1)

            }
        }
    }


}
