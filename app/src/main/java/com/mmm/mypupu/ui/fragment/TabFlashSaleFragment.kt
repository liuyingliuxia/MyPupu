package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.ui.adapter.FlashSaleAdapter
import com.mmm.mypupu.ui.bean.GoodsBean
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.*

class TabFlashSaleFragment : Fragment(){
    var INIT_LIST_NUM = 5
    //var LOAD_COUNT = 0
    private var list :ArrayList<GoodsBean > = ArrayList ()
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
        list = GoodsBean.newGoodsList(INIT_LIST_NUM)
        flashSaleAdapter = FlashSaleAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvFlashSale.layoutManager =linearLayoutManager
        rvFlashSale.adapter = flashSaleAdapter
        //下拉刷新
         srlFlash.setOnRefreshListener{
            srlFlash.postDelayed({
                srlFlash.isRefreshing = false
            }, 50)
            list.clear()
            list.addAll(GoodsBean.newGoodsList(7))
            flashSaleAdapter.notifyDataSetChanged()
        }

        rvFlashSale.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                val isBottom = rvFlashSale.canScrollVertically(1)
                if ( isBottom == false && list.size < 50 ){
                    list.addAll(GoodsBean.newGoodsList(5))
                    rvFlashSale.postDelayed({
                        flashSaleAdapter.notifyDataSetChanged()
                    }, 1000)
                    Log.e("数据长度" ,list.size.toString())

                }
            }
        })
    }

}
