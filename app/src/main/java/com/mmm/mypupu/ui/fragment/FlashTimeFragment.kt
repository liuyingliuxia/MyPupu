package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FlashSaleAdapter
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_flash_time.view.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*

class FlashTimeFragment : Fragment() {

    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var flashAdapter: FlashSaleAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_flash_time, container, false)
        list = getList()
        flashAdapter = FlashSaleAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvTime.layoutManager = linearLayoutManager
        mView.rvTime.adapter = flashAdapter
        return mView
    }

    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
            list.add(Goods(goodsImg[i], goodsTitle[i], goodsSubtitle[i], goodsQuantity[i], goodsRemark[i], goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }
}
