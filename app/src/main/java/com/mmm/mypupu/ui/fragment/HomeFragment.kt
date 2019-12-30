package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.data.searchHint
import com.mmm.mypupu.ui.adapter.ViewPagerAdapter
import com.mmm.mypupu.ui.data.goodsPrice
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.container_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*


class HomeFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        srlMain.setOnRefreshListener(this)

        tvSearch.setOnClickListener{
           kotlin.run {
                val intent  = Intent()
                intent.setClass(this@HomeFragment.context!!, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initViewPager () {
        val ViewPagerAdapter  = ViewPagerAdapter(context!!, getChildFragmentManager())
        //设置预加载数 全部的fragment
        vpMain.offscreenPageLimit = 4
        vpMain.adapter = ViewPagerAdapter
        vpMain.currentItem = 0
        tbMain.setupWithViewPager(vpMain)

    }
    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                var i :Int = (0 until searchHint.size).random()
                tvSearch.text = searchHint [i]

                srlMain.isRefreshing = false
            }
        },1000)
    }


}
