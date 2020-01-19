package com.mmm.mypupu.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.QuickHistoryAdapter
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import com.mmm.mypupu.ui.widgets.HorizontalItemDecoration
import com.mmm.mypupu.ui.widgets.MyLayoutManager
import com.mmm.mypupu.util.MyHistoryUtil
import com.mmm.mypupu.util.MyUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.findAll

class SearchHistoryFragmemt : Fragment() {
    private lateinit var historyList :ArrayList<SearchHistoryBean>
    private lateinit var myLayoutManager: MyLayoutManager
    private lateinit var historyAdapter: QuickHistoryAdapter

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_history, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        /*
         *优化为 rv 显示历史记录 使用自动换行 @param MyLayoutManager
         */
        historyList = getBeanList()
        historyAdapter = QuickHistoryAdapter(R.layout.item_history ,historyList)
        myLayoutManager = MyLayoutManager()
        myLayoutManager.isAutoMeasureEnabled = true //非常重要
        //myLayoutManager.orientation = GridLayoutManager.VERTICAL
        //添加水平间距
        rvHistory.addItemDecoration(HorizontalItemDecoration(20 , context!!))
        rvHistory.layoutManager = myLayoutManager
        rvHistory.adapter = historyAdapter

        // addHistory(historyList)

        historyAdapter.setOnItemClickListener { _,_, position ->
            activity!!.etSearch.setText(historyList[position].title )
            // 直接进行搜索
            toSearch()
            MyHistoryUtil.addHistory(activity!!.etSearch)
            MyUtil.hideKeyforard(activity!!.etSearch, activity!!)
        }

        if(historyList.isEmpty()){
            ivClean.visibility = View.INVISIBLE
            tvNoHistory.visibility = View.VISIBLE
        }else {
            ivClean.visibility = View.VISIBLE
            tvNoHistory.visibility = View.INVISIBLE
        }

        //清空历史记录
        ivClean.setOnClickListener {
            AlertDialog.Builder(context!!)
                .setMessage("确认删除全部搜索历史")
                .setPositiveButton("确定") { _, _ ->
                    ivClean.visibility = View.INVISIBLE
                    tvNoHistory.visibility = View.VISIBLE
                    historyList.clear()
                    LitePal.deleteAll<SearchHistoryBean>()
                    historyAdapter.notifyDataSetChanged()
                }
                .setNeutralButton("取消", null)
                .create()
                .show()
        }
    }

    //开始 搜索 显示结果页
    private fun toSearch() {
        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.llContainer, SearchResultFragment())
        manager.commit()
    }

    private fun getBeanList(): ArrayList<SearchHistoryBean> {
        val beanList = ArrayList<SearchHistoryBean>()
        val mList: List<SearchHistoryBean> = LitePal.findAll<SearchHistoryBean>()
        //用 栈的数据结构 后进先出
        var range= mList.indices
        for ( i in range.reversed()){
            beanList.add(mList[i])
        }
        return beanList
    }

}