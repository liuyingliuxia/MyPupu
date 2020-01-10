package com.mmm.mypupu.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FruitAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.util.myUtil
import kotlinx.android.synthetic.main.drawer_filter.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*
import kotlinx.android.synthetic.main.item_filter.*
import kotlinx.android.synthetic.main.toolbar_fruit.*
import java.util.Collections.swap

class TabFruitFragment : Fragment(), View.OnClickListener {
    private var rbClickTiems = 0
    private val INIT_NUM = 5
    private var list: ArrayList<Goods> = ArrayList()
    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_tab_fruit, container, false)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = Goods.newFruitList(INIT_NUM)
        fruitAdapter = FruitAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvFruit.layoutManager = linearLayoutManager
        rvFruit.adapter = fruitAdapter
        val FruitSortList: ArrayList<LinearLayout> = arrayListOf(
            llFruitSort1, llFruitSort2, llFruitSort3, llFruitSort4, llFruitSort5,
            llFruitSort6, llFruitSort7, llFruitSort8, llFruitSort9, llFruitSort10
        )

        initListener()

        for (i in FruitSortList.indices) {
            FruitSortList[i].setOnClickListener {
                myUtil.talk(context!!, FruitSortList[i].tag.toString())
            }
        }
    }

    fun initListener() {
        cbInStock.setOnClickListener(this)
        rbPrice.setOnClickListener(this)
        rbDiscount.setOnClickListener(this)
        tvFilter.setOnClickListener(this)

        tvOk.setOnClickListener(this)
        rbLabelDiscount.setOnClickListener(this)
        rbLabelFlash.setOnClickListener(this)
        rbLabelHot.setOnClickListener(this)
        rbLabelNew.setOnClickListener(this)
        rbLabelNewman.setOnClickListener(this)
        rbLabelSpecial.setOnClickListener(this)
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cbInStock -> {
                if (cbInStock.isChecked == true) {
                    list.clear()
                    list.addAll(Goods.newFruitList(6))
                    fruitAdapter.notifyDataSetChanged()

                } else {
                    list.clear()
                    list.addAll(Goods.newFruitList(10))
                    fruitAdapter.notifyDataSetChanged()
                }
            }
            R.id.rbPrice -> {
                Log.e("折扣 选中状态 ", rbDiscount.isChecked.toString())
                changedItemState3(rbPrice)

            }
            R.id.rbDiscount -> {
                Log.e("价格 选中状态 ", rbPrice.isChecked.toString())
                rbDiscount.isChecked = !rbPrice.isChecked
                changedItemState3(rbDiscount)
            }

            R.id.tvOk -> {
                Log.e("抽屉", "快关！")
                if (vdFilter.isDrawerOpen) {
                    vdFilter.closeDrawer()
                    Log.e("抽屉", "关！")
                }
            }
            R.id.rbLabelNew -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelFlash -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelDiscount -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelNewman -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelSpecial -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.rbLabelHot -> {
                list.clear()
                fruitAdapter.notifyDataSetChanged()
            }
            R.id.tvFilter -> {
                if (vdFilter.isDrawerOpen) {
                    vdFilter.closeDrawer()
                } else {
                    vdFilter.openDrawerView()
                }
            }
        }
    }

    //三种点击状态的切换
    fun changedItemState3(rb: RadioButton) {
        when (rbClickTiems) {
            0 -> {
                //从小到大排序
                rbClickTiems++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_top_selected)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                rb.setCompoundDrawables(null, null, drawable, null)
                list.clear()
                list.addAll(Goods.newFruitList(10))
                fruitAdapter.notifyDataSetChanged()
            }
            1 -> {
                //从大到小排序
                rbClickTiems++
                rb.setTextColor(resources.getColor(R.color.color23))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_bottom_selected)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                rb.setCompoundDrawables(null, null, drawable, null)

                list.clear()
                list.addAll(Goods.newFruitList(10))
                fruitAdapter.notifyDataSetChanged()
            }
            2 -> {
                //还原
                rbClickTiems = 0
                rb.setTextColor(resources.getColor(R.color.color5))
                val drawable = resources.getDrawable(R.drawable.icon_indicate_arrow_no_selected)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                rb.setCompoundDrawables(null, null, drawable, null)

                list.clear()
                list.addAll(Goods.newFruitList(10))
                fruitAdapter.notifyDataSetChanged()
            }
        }
    }
}
