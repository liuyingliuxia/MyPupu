package com.mmm.mypupu.ui.fragment

import android.os.Bundle
import android.text.TextUtils.indexOf
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
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
    var key: String = ""
    private var list: MutableList<String> = ArrayList()
    private lateinit var mInputAutoAdapter: SearchInputAutoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mResultFragment = Fragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_search_input, container, false)
        if (arguments?.getString("Key").isNullOrEmpty())
            Log.e("警告：", "传过来的值为空")
        else {
            key = arguments?.getString("Key")!!
            Log.e("传过来的值为", key)
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
                    val fm = activity!!.supportFragmentManager
                    fm.beginTransaction().replace(activity!!.llContainer.id,mResultFragment).commit()
                    activity!!.actSearch.isFocusable = false
                }
            })
        }
        return mView
    }
        fun getList(): MutableList<String> {
            /*   if ( searchAutoCompleteGoods.indexOf(key) != -1 ){
            list.add(searchAutoCompleteGoods[searchAutoCompleteGoods.indexOf(key)])
            }*/
            for (i in 0..searchAutoCompleteGoods.size.minus(1))
                list.add(searchAutoCompleteGoods[i])
            return list
        }

}
