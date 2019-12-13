package com.mmm.mypupu.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_main.*

class MainActivity : FragmentActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        srlMain.setOnRefreshListener { object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
               Handler().postDelayed(object :Runnable {
                   override fun run() {
                       srlMain.isRefreshing = false
                   }
               },3000)
            }
          }

        tvRecommend.setOnClickListener(this)
        tvFlash.setOnClickListener(this)
        tvCrazy.setOnClickListener(this)
        tvFruit.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
       when (v?.id) {
           R.id.tvRecommend -> {
               TODO()
           }
       }
    }
}
