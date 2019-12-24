package com.mmm.mypupu.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.adapter.SearchResultAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.tvSearch
import kotlinx.android.synthetic.main.container_home.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*
import kotlinx.android.synthetic.main.toolbar_main.*

class SearchResultFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{
    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    var  key : String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView =  inflater.inflate(R.layout.fragment_search_result, container, false)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srlSearch.setOnRefreshListener(this)
        if (arguments?.getString("Key").isNullOrEmpty())
             Log.e("警告：","传过来的值为空")
        else
        {
            key = arguments?.getString("Key")!!
            Log.e("传过来的值为",key)
            //根据搜索关键字展示数据
            list = getList()
            if (list.isNotEmpty()) {
                srlSearch.visibility = View.VISIBLE
                llNoGoods.visibility = View.INVISIBLE
                searchResultAdapter = SearchResultAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                view.rvSearchResult.layoutManager = linearLayoutManager
                view.rvSearchResult.adapter = searchResultAdapter
            }
        }
    }

   private fun getList(): MutableList<Goods> {
        for (i in 0 until goodsImg.size) {
//            if ( goodsTitle.contains(key)){
                list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i], goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
//            }
//            else
//              list.clear()
        }
        Log.e("获取的结果长度",list.size.toString())

        return list
    }

    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                srlMain.isRefreshing = false
            }
        },2000)
    }
}