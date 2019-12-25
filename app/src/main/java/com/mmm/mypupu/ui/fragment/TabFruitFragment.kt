package com.mmm.mypupu.ui.fragment


import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FruitAdapter
import com.mmm.mypupu.ui.adapter.RecommendationAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.widgets.VerticalDrawerLayout
import kotlinx.android.synthetic.main.fragment_tab_fruit.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.item_filter.*
import kotlinx.android.synthetic.main.item_fruit.*

class TabFruitFragment: Fragment(),View.OnClickListener {
    private var rbClickTiems = 0
    private var list :MutableList<Goods > = ArrayList ()
    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_fruit, container, false)
        list = getList()
        fruitAdapter = FruitAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvFruit.layoutManager =linearLayoutManager
        mView.rvFruit.adapter = fruitAdapter
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvInStock.setOnClickListener(this)
        rbPrice.setOnClickListener(this)
        rbDiscount.setOnClickListener(this)
        tvFilter.setOnClickListener(this)
    }


    fun getList(): MutableList<Goods> {

        for (i in 0 until fruitImg.size) {
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

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
      when (v?.id){
          R.id.tvInStock ->{
            rvFruit.refreshDrawableState()
          }
          R.id.rbPrice ->{
              Log.e("折扣 选中状态 ", rbDiscount.isChecked.toString())
              changedItemState3(rbPrice)
              rvFruit.refreshDrawableState()
          }
          R.id.rbDiscount ->{
              Log.e("价格 选中状态 ", rbPrice.isChecked.toString())
              rbDiscount.isChecked = !rbPrice.isChecked
              changedItemState3(rbDiscount)
              rvFruit.refreshDrawableState()
          }

          R.id.tvOk ->{
              vdFilter.closeDrawer()
          }

          R.id.tvFilter ->{
            if ( vdFilter.isDrawerOpen) {
                vdFilter.closeDrawer()
            }
              else{
                vdFilter.openDrawerView()
            }
          }
      }
    }
        //三种点击状态的切换
    fun changedItemState3 (rb :RadioButton){
        when (rbClickTiems){
            0 -> {
                rbClickTiems ++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_top_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
            1 -> {
                rbClickTiems ++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_bottom_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
            2 -> {
                rbClickTiems = 0
                rb.setTextColor(resources.getColor(R.color.color5))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_no_selected)
                drawable.setBounds(0,0,drawable.minimumWidth,drawable.minimumHeight)
                rb.setCompoundDrawables(null,null,drawable,null)
            }
        }
    }
}
