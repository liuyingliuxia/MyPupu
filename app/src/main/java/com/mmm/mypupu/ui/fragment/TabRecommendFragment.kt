package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.adapter.SearchInputAutoAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.widgets.SaveHistory
import com.mmm.mypupu.util.mT
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.tvSearch
import kotlinx.android.synthetic.main.container_home.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*
import kotlinx.android.synthetic.main.toolbar_main.*

class TabRecommendFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{

    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var recommendAdapter: RecommendationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_recommend, container, false)
        list = getList()
        recommendAdapter = RecommendationAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvRecommend.layoutManager =linearLayoutManager
        mView.rvRecommend.adapter = recommendAdapter
          return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  view.srlRecommend.setProgressViewOffset(false , 0 , TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 24F,
            resources.displayMetrics).toInt())*/

        view.srlRecommend.setOnRefreshListener(this)
        view.srlRecommend.isRefreshing = false

    }

    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
           list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    fun getReverseList(): MutableList<Goods> {
        var zheng = 0 until goodsImg.size
        var reverse = zheng.reversed()
        for (i in reverse ) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                list.clear()
                list = getReverseList()
                recommendAdapter = RecommendationAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rvRecommend.layoutManager =linearLayoutManager
                rvRecommend.adapter = recommendAdapter
                mT.t(context!!, "刷新成功")
                srlRecommend.isRefreshing = false
            }
        }, 500)
    }

}
