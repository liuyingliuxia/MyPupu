package com.mmm.mypupu.ui.fragment


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SortCatalogAdapter
import kotlinx.android.synthetic.main.fragment_sort.view.*
import kotlinx.android.synthetic.main.item_sort_catalog.*

class SortFragment : Fragment() {
    private val mItem :ArrayList<String> = arrayListOf("火锅节","水果", "蔬菜", "肉禽蛋", "海鲜水产", "粮油调味料", "早点", "熟食冻品", "牛奶面包", "酒水冲饮", "休闲零食",
        "日用清洁" ,"护理美妆", "母婴", "鲜花宠物", "进口商品 ","地方特产"," ")
    private lateinit var sortCatalogAdapter: SortCatalogAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化左侧 目录
        val mView = inflater.inflate(R.layout.fragment_sort, container, false)
        sortCatalogAdapter = SortCatalogAdapter(context!!, mItem)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvSortItem.layoutManager =linearLayoutManager
        mView.rvSortItem.adapter = sortCatalogAdapter
        sortCatalogAdapter.setOnItemClick(object : SortCatalogAdapter.OnItemClickListener{
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun OnItemClick(view: View, position: Int) {
               // view.background = resources.getDrawable(R.color.color1)
            }
        })
        return mView
    }


}
