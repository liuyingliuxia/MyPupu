package com.mmm.mypupu.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmm.mypupu.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmm.mypupu.ui.adapter.CrazyDiscountAdapter
import com.mmm.mypupu.ui.adapter.FlashSaleAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_tab_crazy_discount.view.*
import kotlinx.android.synthetic.main.fragment_tab_flash_sale.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*

class TabFlashSaleFragment : Fragment() {

    private var list :MutableList<Goods > = ArrayList ()
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

    fun getList(): MutableList<Goods> {

        for (i in 0 until goodsImg.size) {
            list.add( Goods( goodsImg[i], goodsTitle[i] , goodsSubtitle[i], goodsQuantity[i],goodsRemark[i] , goodsPrice[i], goodsOriginPrice[i], goodsNum[i]))
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
