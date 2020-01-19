package com.mmm.mypupu.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SearchResultAdapter
import com.mmm.mypupu.ui.bean.GoodsBean
import com.mmm.mypupu.ui.bean.SearchResultBean
import com.mmm.mypupu.util.MyUtil
import com.mmm.mypupu.util.MyUtil.Companion.changedItemState3
import kotlinx.android.synthetic.main.drawer_filter.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.*
import kotlinx.android.synthetic.main.item_filter.*

class SearchResultFragment : Fragment() {
    private var rbClickTiems = 0
    private var resultBean = SearchResultBean()
    val randNum = (1..14).random()
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        srlSearch.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        srlSearch.isRefreshing = false
                    }
                }, 500)
            }
        })

        //随机生成 0~14条的假数据
        val state = (0..1).random()

        if (state == 0)// 1/2的概率无结果
        {
            view.srlSearch.visibility = View.GONE
            view.llNoGoods.visibility = View.VISIBLE
        } else {
            resultBean.goodsList = GoodsBean.newGoodsList(randNum)
            view.srlSearch.visibility = View.VISIBLE
            view.llNoGoods.visibility = View.GONE
            searchResultAdapter = SearchResultAdapter(resultBean.goodsList!!, context!!)
            linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            view.rvSearchResult.layoutManager = linearLayoutManager
            view.rvSearchResult.adapter = searchResultAdapter
        }
    }

    private fun initListener() {
        val rbList = arrayListOf<RadioButton>(rbLabelSpecial ,rbLabelNewman , rbLabelNew , rbLabelHot , rbLabelFlash , rbLabelDiscount)
        cbInStock.setOnClickListener(click)
        rbPrice.setOnClickListener(click)
        rbDiscount.setOnClickListener(click)
        tvFilter.setOnClickListener(click)
        cbInStock.setOnClickListener(click)
        rbPrice.setOnClickListener(click)
        rbDiscount.setOnClickListener(click)
        tvFilter.setOnClickListener(click)
        tvReset.setOnClickListener(click)
        tvOk.setOnClickListener(click)

        for ( i in rbList.indices)
            rbList[i].setOnClickListener(click)
    }
    
    val click = View.OnClickListener { v ->
        when (v?.id) {
            R.id.cbInStock -> {
                if (cbInStock.isChecked) {
                    resultBean.goodsList!!.clear()
                    resultBean.goodsList!!.addAll(GoodsBean.newFruitList(6))
                    searchResultAdapter.notifyDataSetChanged()

                } else {
                    resultBean.goodsList!!.clear()
                    resultBean.goodsList!!.addAll(GoodsBean.newFruitList(10))
                    searchResultAdapter.notifyDataSetChanged()
                }
            }
            R.id.rbPrice -> {
                Log.e("折扣 选中状态 ", rbDiscount.isChecked.toString())
                changedItemState3(rbPrice , activity!! )

            }
            R.id.rbDiscount -> {
                Log.e("价格 选中状态 ", rbPrice.isChecked.toString())
                rbDiscount.isChecked = !rbPrice.isChecked
                changedItemState3(rbDiscount , activity!!)
            }

            R.id.tvOk -> {
                    vdSearchFilter.closeDrawer()
            }


            R.id.rbLabelNew -> {
                srlSearch.visibility = View.GONE
                llNoGoods.visibility = View.VISIBLE
                MyUtil.talk(context!! ,"点击了${rbLabelNew.text}" )
            }
            R.id.rbLabelFlash -> {
                srlSearch.visibility = View.GONE
                llNoGoods.visibility = View.VISIBLE
            }
            R.id.rbLabelDiscount -> {
                resultBean.goodsList!!.clear()
                searchResultAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelNewman -> {
                srlSearch.visibility = View.GONE
                llNoGoods.visibility = View.VISIBLE
            }
            R.id.rbLabelSpecial -> {
                srlSearch.visibility = View.GONE
                llNoGoods.visibility = View.VISIBLE
            }
            R.id.rbLabelHot -> {
                srlSearch.visibility = View.GONE
                llNoGoods.visibility = View.VISIBLE
            }
            
            R.id.tvFilter -> {
                if (vdSearchFilter.isDrawerOpen) {
                    vdSearchFilter.closeDrawer()
                } else {
                    vdSearchFilter.openDrawerView()
                }
            }
            R.id.tvReset -> {
                val rbList = arrayListOf<RadioButton>(rbLabelSpecial ,rbLabelNewman , rbLabelNew , rbLabelHot , rbLabelFlash , rbLabelDiscount)
                for ( i in rbList.indices) {
                    rbList[i].isChecked = false
                }
                resultBean.goodsList = GoodsBean.newGoodsList(randNum)
                searchResultAdapter.notifyDataSetChanged()
            }
        }
    }

}