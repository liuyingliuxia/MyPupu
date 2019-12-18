package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmm.mypupu.ui.adapter.CrazyDiscountAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*

class TabCrazyDiscountFragment : Fragment() {

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

    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i]))
        }
        return list
    }

    companion object {

        @JvmStatic
        fun newInstance(sectionNumber: Int): TabCrazyDiscountFragment {
            return TabCrazyDiscountFragment().apply {
                arguments = Bundle().apply {
                    putInt(" ", sectionNumber)
                }
            }
        }
    }

}
