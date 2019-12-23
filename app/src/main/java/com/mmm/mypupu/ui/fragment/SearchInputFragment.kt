package com.mmm.mypupu.ui.fragment

import android.os.Bundle
import android.text.TextUtils.indexOf
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.FruitAdapter
import com.mmm.mypupu.ui.adapter.SearchInputAutoAdapter
import com.mmm.mypupu.ui.bean.Goods
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_input.view.*
import kotlinx.android.synthetic.main.fragment_tab_fruit.view.*

class SearchInputFragment : Fragment() {

    private var list :MutableList<String> = ArrayList ()
    private lateinit var mInputAutoAdapter: SearchInputAutoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_search_input, container, false)
        list = getList()
        mInputAutoAdapter = SearchInputAutoAdapter(list, context!!)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mView.rvAutoComplete.layoutManager =linearLayoutManager
        mView.rvAutoComplete.adapter = mInputAutoAdapter
        return mView
    }

    fun getList(): MutableList<String> {
       val key = activity!!.actSearch.text.toString()
        if ( searchAutoCompleteGoods.indexOf(key) != -1 ){
            list.add(searchAutoCompleteGoods[searchAutoCompleteGoods.indexOf(key)])
        }
        return list
    }

}
