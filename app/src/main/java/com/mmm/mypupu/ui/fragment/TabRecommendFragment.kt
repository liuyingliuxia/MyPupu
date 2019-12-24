package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*

class TabRecommendFragment : Fragment() {

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

    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
           list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
        }
        return list
    }

    companion object {

        @JvmStatic
        fun newInstance(sectionNumber: Int): TabRecommendFragment {
            return TabRecommendFragment().apply {
                arguments = Bundle().apply {
                    putInt(" ", sectionNumber)
                }
            }
        }
    }

}
