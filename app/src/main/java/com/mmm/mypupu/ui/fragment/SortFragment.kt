package com.mmm.mypupu.ui.fragment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SortCatalogAdapter
import com.mmm.mypupu.ui.adapter.SortContentAdapter
import com.mmm.mypupu.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_sort.*
import kotlinx.android.synthetic.main.fragment_sort.view.*

class SortFragment : Fragment() {
    private val mItem :ArrayList<String> = arrayListOf("火锅节","水果", "蔬菜", "肉禽蛋", "海鲜水产", "粮油调味料", "早点", "熟食冻品", "牛奶面包", "酒水冲饮", "休闲零食",
        "日用清洁" ,"护理美妆", "母婴", "鲜花宠物", "进口商品 ","地方特产","") //最后一位留给空白

    private val mContentImg :ArrayList<Int > = arrayListOf(R.drawable.sort1,R.drawable.sort2,R.drawable.sort3,R.drawable.sort4,R.drawable.sort5,R.drawable.sort6,R.drawable.sort7,
        R.drawable.sort8,R.drawable.sort9,R.drawable.sort10,R.drawable.sort11,R.drawable.sort12,R.drawable.sort13,R.drawable.sort14,R.drawable.sort15,R.drawable.sort16,R.drawable.sort17)

    private lateinit var sortCatalogAdapter: SortCatalogAdapter
    private lateinit var sortContentAdapter: SortContentAdapter
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

        //初始化右侧显示内容
        sortContentAdapter = SortContentAdapter(context!!, mContentImg)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvSortContext.layoutManager =linearLayoutManager
        mView.rvSortContext.adapter = sortContentAdapter

        val snap = LinearSnapHelper()
        snap.attachToRecyclerView(rvSortItem)
        snap.attachToRecyclerView(rvSortContext)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivSearch.setOnClickListener{v->run{
            val intent  = Intent()
            intent.setClass(this@SortFragment.context!!, SearchActivity::class.java)
            startActivity(intent)

        }}
    }
}
