package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.mmm.mypupu.util.myUtil
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*

class TabFlashSaleFragment : Fragment(){
    var INIT_LIST_NUM = 5
    var LOAD_COUNT = 0
    private var list :ArrayList<Goods > = ArrayList ()
    private lateinit var flashSaleAdapter: FlashSaleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_flash_sale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = Goods.newGoodsList(INIT_LIST_NUM)
        flashSaleAdapter = FlashSaleAdapter(list, context!!,LOAD_COUNT)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvFlashSale.layoutManager =linearLayoutManager
        rvFlashSale.adapter = flashSaleAdapter
        //下拉刷新
         srlFlash.setOnRefreshListener{
            srlFlash.postDelayed({
                srlFlash.isRefreshing = false
            }, 50)
             LOAD_COUNT = 0
            list.clear()
            list.addAll(Goods.newGoodsList(7))
            flashSaleAdapter.notifyDataSetChanged()
        }

        rvFlashSale.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                val isBottom = rvFlashSale.canScrollVertically(1)
                if ( isBottom == false && LOAD_COUNT < 3 ){
                    list.addAll(Goods.newGoodsList(5))
                    LOAD_COUNT ++
                    rvFlashSale.postDelayed({
                        flashSaleAdapter.notifyDataSetChanged()
                    }, 1000)
                    Log.e("数据长度" ,list.size.toString())

                }
                else if ( isBottom == false && LOAD_COUNT == 3 ){
                    myUtil.talk(context!!,"请检查网络！")
                    //myUtil.talk(context!! , "到底了哦~" + "共有"+ list.size + "条数据"+ " ,"+ LOAD_COUNT.toString() )
                 //   flashSaleAdapter.notifyDataSetChanged()
                    Log.e("数据长度/到底了" ,list.size.toString())
                    LOAD_COUNT ++
                }
            }
        })
    }

}
