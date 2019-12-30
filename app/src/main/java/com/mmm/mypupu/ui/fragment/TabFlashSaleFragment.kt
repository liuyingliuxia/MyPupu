package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mmm.mypupu.ui.adapter.CrazyDiscountAdapter
import com.mmm.mypupu.ui.adapter.FlashSaleAdapter
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.util.mT
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*

class TabFlashSaleFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener{
    var step_refresh = 0
    private var list :ArrayList<Goods > = ArrayList ()
    private lateinit var flashSaleAdapter: FlashSaleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_flash_sale, container, false)
        list = getList()
        flashSaleAdapter = FlashSaleAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvFlashSale.layoutManager =linearLayoutManager
        mView.rvFlashSale.adapter = flashSaleAdapter
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.srlFlash.setOnRefreshListener(this)
        rvFlashSale.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                var isBottom = rvFlashSale.canScrollVertically(1)
                if ( isBottom == false && step_refresh > 3 ){
                    mT.t(context!! , "继续下滑加载更多")
                    list = getList()
                    flashSaleAdapter = FlashSaleAdapter(list, context!!)
                    linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    rvFlashSale.layoutManager =linearLayoutManager
                    rvFlashSale.adapter = flashSaleAdapter
                    step_refresh --
                }
                else if (isBottom == false && step_refresh <3   )
                    mT.t(context!! , "没有更多了")
            }

        })
    }

    fun getList(): ArrayList<Goods> {

        for (i in 0 until goodsImg.size) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    fun getList2( i :Int): ArrayList<Goods> {
        var zheng = 0 until goodsImg.size
        var reverse = zheng.reversed()
        for (i in reverse step i) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    fun getHalfList(): ArrayList<Goods> {

        for (i in goodsImg.size.div(2) until  goodsImg.size ) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))

        }
        return list
    }

    override fun onRefresh() {
        Handler().postDelayed(object :Runnable {
            override fun run() {
                list.clear()
                step_refresh ++
                list = getList2( step_refresh )
                flashSaleAdapter = FlashSaleAdapter(list, context!!)
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rvFlashSale.layoutManager =linearLayoutManager
                rvFlashSale.adapter = flashSaleAdapter
                mT.t(context!!, "刷新成功")
                srlFlash.isRefreshing = false
            }
        }, 500)
    }
}
