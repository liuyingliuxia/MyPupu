package com.mmm.mypupu.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FruitAdapter
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.adapter.SearchResultAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.tvSearch
import kotlinx.android.synthetic.main.container_home.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*
import kotlinx.android.synthetic.main.item_filter.*
import kotlinx.android.synthetic.main.toolbar_main.*

class SearchResultFragment : Fragment(),View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private var rbClickTiems = 0
    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView =  inflater.inflate(R.layout.fragment_search_result, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srlSearch.setOnRefreshListener(this)

        cbInStock.setOnClickListener(this)
        rbPrice.setOnClickListener(this)
        rbDiscount.setOnClickListener(this)
        tvFilter.setOnClickListener(this)

        Log.e("输入的结果是", activity!!.actSearch.text.toString())

            list = getList()
            if (list.isNotEmpty()) {
                view.srlSearch.visibility = View.VISIBLE
                view.llNoGoods.visibility = View.GONE
                searchResultAdapter = SearchResultAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                view.rvSearchResult.layoutManager = linearLayoutManager
                view.rvSearchResult.adapter = searchResultAdapter
            }
            else {
                view.srlSearch.visibility = View.GONE
                view.llNoGoods.visibility = View.VISIBLE
            }
    }

   private fun getList(): MutableList<Goods> {
       val input =  activity!!.actSearch.text.toString()
       if (input.contentEquals("蘑菇")) {
               for (i in 0..2)
                   list.add(Goods(sResultImg[i], sResultTitle[i], sResultSubTitle[i], sResultQuantity[i], sResultRemark[i], sResultPrice[i], sResultOriPrice[i], goodsNum[i]))
        }

       else if(input.contentEquals("0")) {
                list.clear()
       }

       else{
           for (i in 0 until goodsImg.size) {
                list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i], goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
           }
       }

        return list
    }

    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                srlSearch.isRefreshing = false
            }
        },500)
    }


    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.cbInStock ->{
                if (cbInStock.isChecked == true) {
                    list.clear()
                    list = getInstockList()
                    searchResultAdapter = SearchResultAdapter(list, context!!)
                    linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    rvSearchResult.layoutManager = linearLayoutManager
                    rvSearchResult.adapter = searchResultAdapter
                }
                else
                {
                    list.clear()
                    list = getList()
                    searchResultAdapter = SearchResultAdapter(list, context!!)
                    linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    rvSearchResult.layoutManager = linearLayoutManager
                    rvSearchResult.adapter = searchResultAdapter
                }
            }
            R.id.rbPrice ->{
                Log.e("折扣 选中状态 ", rbDiscount.isChecked.toString())
                changedItemState3(rbPrice)

            }
            R.id.rbDiscount ->{
                Log.e("价格 选中状态 ", rbPrice.isChecked.toString())
                rbDiscount.isChecked = !rbPrice.isChecked
                changedItemState3(rbDiscount)
            }

            R.id.tvOk ->{
                Log.e("抽屉","快关！")
                if ( vdSearchFilter.isDrawerOpen) {
                    vdSearchFilter.closeDrawer()
                    Log.e("抽屉","关！")
                }
            }

            R.id.tvFilter ->{
                if ( vdSearchFilter.isDrawerOpen) {
                    vdSearchFilter.closeDrawer()
                }
                else{
                    vdSearchFilter.openDrawerView()
                }
            }
        }
    }

    fun getInstockList(): MutableList<Goods> {

        for (i in 0 until fruitImg.size step 3) {
            list.add( Goods( fruitImg[i], fruitTitle[i] , fruitSubtitle[i], fruitQuantity[i],fruitRemark[i] , fruitPrice[i], fruitOriPrice[i], goodsNum[i]))

        }
        return list
    }

    fun getPriceList1(): MutableList<Goods> {
        var zheng = 0 .. fruitImg.size.minus(1)
        var fan = zheng.reversed()
        for (i in fan) {
            list.add( Goods( fruitImg[i], fruitTitle[i] , fruitSubtitle[i], fruitQuantity[i],fruitRemark[i] , fruitPrice[i], fruitOriPrice[i], goodsNum[i]))

        }
        return list
    }

    //三种点击状态的切换
    fun changedItemState3 (rb :RadioButton){
        when (rbClickTiems){
            0 -> {
                //从小到大排序
                rbClickTiems ++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_top_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)

                list.clear()
                list = getPriceList1()
                searchResultAdapter = SearchResultAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rvSearchResult.layoutManager = linearLayoutManager
                rvSearchResult.adapter = searchResultAdapter
            }
            1 -> {
                //从大到小排序
                rbClickTiems ++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_bottom_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)

                list.clear()
                list = getList()
                searchResultAdapter = SearchResultAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rvSearchResult.layoutManager = linearLayoutManager
                rvSearchResult.adapter = searchResultAdapter
            }
            2 -> {
                //还原
                rbClickTiems = 0
                rb.setTextColor(resources.getColor(R.color.color5))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_no_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)

                list.clear()
                searchResultAdapter = SearchResultAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rvSearchResult.layoutManager = linearLayoutManager
                rvSearchResult.adapter = searchResultAdapter
            }
        }
    }

}