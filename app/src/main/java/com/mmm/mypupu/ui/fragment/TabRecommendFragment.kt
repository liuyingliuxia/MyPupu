package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*
import kotlinx.android.synthetic.main.toolbar_main.*

class TabRecommendFragment : Fragment(){
    var count = 0
    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var recommendAdapter: RecommendationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           return inflater.inflate(R.layout.fragment_tab_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val randNum = ( 0..10 ).random()
        list = Goods.newGoodsList(randNum)
        recommendAdapter = RecommendationAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvRecommend.layoutManager =linearLayoutManager
        rvRecommend.adapter = recommendAdapter

        srlRecommend.setOnRefreshListener{
            srlRecommend.postDelayed({
                srlRecommend.isRefreshing = false
            }, 50)
            list.clear()
            list.addAll(Goods.newGoodsList(7))
            recommendAdapter.notifyDataSetChanged()
        }

        rvRecommend.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                val isBottom = rvRecommend.canScrollVertically(1)
                if ( isBottom == false && count < 2 ){
                    mT.t(context!! , "继续下滑加载更多")
                    list.addAll(Goods.newGoodsList(3))
                    recommendAdapter.notifyDataSetChanged()
                    count ++
                    if ( count == 2 ){
                        mT.t(context!! , "没有更多了")
                        Log.e("次数", count.toString())
                    }
                }
            }
        })
    }

}
