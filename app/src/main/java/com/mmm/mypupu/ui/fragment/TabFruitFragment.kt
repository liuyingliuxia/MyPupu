package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FruitAdapter
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.fragment_tab_recommend.view.*

class TabFruitFragment: Fragment() {

    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_fruit, container, false)
        list = getList()
        fruitAdapter = FruitAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvFruit.layoutManager =linearLayoutManager
        mView.rvFruit.adapter = fruitAdapter
        return mView
    }



    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
            list.add( Goods( fruitImg[i], fruitTitle[i] , fruitSubtitle[i], fruitQuantity[i],fruitRemark[i] , fruitPrice[i], fruitOriPrice[i], goodsNum[i]))
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
