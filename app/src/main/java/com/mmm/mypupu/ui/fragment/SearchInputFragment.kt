package com.mmm.mypupu.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.SearchInputAutoAdapter
import com.mmm.mypupu.ui.data.*
import com.mmm.mypupu.ui.widgets.SaveHistory
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_input.view.*

class SearchInputFragment : Fragment() {
    private var list: MutableList<String> = ArrayList()
    private lateinit var mInputAutoAdapter: SearchInputAutoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_search_input, container, false)
            list = getList()
            mInputAutoAdapter = SearchInputAutoAdapter(list, context!!)
            linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mView.rvAutoComplete.layoutManager = linearLayoutManager
            mView.rvAutoComplete.adapter = mInputAutoAdapter
            mInputAutoAdapter.setOnItemClick(object : SearchInputAutoAdapter.OnItemClickListener {
                override fun OnItemClick(view: View, position: Int) {
                  activity!!.actSearch.setText(  list[position] )
                  // 直接进行搜索
                    toSearch()
                    //添加历史记录
                    SaveHistory.saveSearchHistory(activity!!.actSearch.text.toString(),context as Context)
                    hideKeyforard(view)
                    activity!!.actSearch.isFocusable = false
                }
            })
        return mView
    }

    private fun toSearch () {

        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.llContainer,SearchResultFragment())
        manager.commit()
    }

    fun getList(): MutableList<String> {
            //筛选
            for (i in 0..searchAutoCompleteGoods.size.minus(1))
                list.add(searchAutoCompleteGoods[i])
            return list
        }

    //强制隐藏软键盘
    private fun hideKeyforard (view: View) {
        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}
