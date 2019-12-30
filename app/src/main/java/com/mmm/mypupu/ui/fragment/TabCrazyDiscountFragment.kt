package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.ui.adapter.CrazyDiscountAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.util.mT
import kotlinx.android.synthetic.main.fragment_sort_test.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*

class TabCrazyDiscountFragment : Fragment() {
    var num: Int = 0
    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var crazyDiscountAdapter: CrazyDiscountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_crazy_discount, container, false)
        list = getList()
        crazyDiscountAdapter = CrazyDiscountAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvCrazyDiscount.layoutManager =linearLayoutManager
        mView.rvCrazyDiscount.adapter = crazyDiscountAdapter
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCrazyDiscount.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //1代表底部,返回true表示没到底部,还可以滑
                var isBottom = rvCrazyDiscount.canScrollVertically(1)
                if ( isBottom == false && num ==0 ){
                    mT.t(context!! , "继续下滑加载更多")
                    list = getHalfList()
                    crazyDiscountAdapter = CrazyDiscountAdapter(list, context!!)
                    linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    rvCrazyDiscount.layoutManager =linearLayoutManager
                    rvCrazyDiscount.adapter = crazyDiscountAdapter
                    num ++
                }
                else if (isBottom == false && num > 0  )
                    mT.t(context!! , "没有更多了")
            }

        })

    }
    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size.div(2)) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    fun getHalfList(): MutableList<Goods> {

        for (i in goodsImg.size.div(2) until  goodsImg.size ) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))

        }
        return list
    }

}
