package com.mmm.mypupu.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SearchInputAutoAdapter
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.util.HistoryUtil
import com.mmm.mypupu.util.myUtil.Companion.hideKeyforard
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_input.view.*

class SearchInputFragment : Fragment() {
    private var list: MutableList<String> = ArrayList()
    private lateinit var mInputAutoAdapter: SearchInputAutoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = loadList()
        mInputAutoAdapter = SearchInputAutoAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        view.rvAutoComplete.layoutManager = linearLayoutManager
        view.rvAutoComplete.adapter = mInputAutoAdapter

        mInputAutoAdapter.setOnItemClick(object : SearchInputAutoAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                activity!!.etSearch.setText(list[position])
                // 直接进行搜索
                toSearch()
                //添加历史记录
                val historyBean =SearchHistoryBean(activity!!.etSearch.text.toString() , true )
                HistoryUtil.saveSearchHistory(historyBean.title!!, context as Context)
                hideKeyforard(view , activity!!)
                activity!!.etSearch.isFocusable = false
            }
        })
    }

    private fun toSearch() {
        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.llContainer, SearchResultFragment())
        manager.commit()
    }

    private fun loadList(): MutableList<String> {
        //筛选
        for (i in 0..searchAutoCompleteGoods.size.minus(1))
            list.add(searchAutoCompleteGoods[i])
        return list
    }



}
