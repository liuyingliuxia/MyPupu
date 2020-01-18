package com.mmm.mypupu.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.convertTo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.adapter.QuickHistoryAdapter
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import com.mmm.mypupu.util.HistoryUtil
import com.mmm.mypupu.util.myHistoryUtil
import com.mmm.mypupu.util.myUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

class SearchHistoryFragmemt : Fragment() {
    private lateinit var historyList :ArrayList<SearchHistoryBean>
    private lateinit var layoutManager: GridLayoutManager
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
        // 优化为 rv 显示历史记录

        historyList = getBeanList()
        historyAdapter = QuickHistoryAdapter(R.layout.item_history ,historyList)
        layoutManager = GridLayoutManager(context, 3)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        rvHistory.layoutManager = layoutManager
        rvHistory.adapter = historyAdapter

        // addHistory(historyList)

        historyAdapter.setOnItemClickListener { _,_, position ->
            activity!!.etSearch.setText(historyList[position].title )
            // 直接进行搜索
            toSearch()
            myHistoryUtil.addHistory(activity!!.etSearch)
            myUtil.hideKeyforard(activity!!.etSearch, activity!!)
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

    //添加历史记录itm到容器
    /*  private fun addHistory(mBeanList: ArrayList<SearchHistoryBean>) {
          val mSaveList: List<String> = HistoryUtil.getSearchHistory(context!!) // 获取 sp 中存储的历史记录
          if (mSaveList.isEmpty()) {
              for ( i in mBeanList.indices){
                  mBeanList[i].isVisibily = false
              //    mHistoryArray[i].visibility = View.INVISIBLE
              }
              tvNoHistory.visibility = View.VISIBLE
              ivClean.visibility = View.INVISIBLE
          } else {
              Log.e("历史记录：", mSaveList.toString())

              tvNoHistory.visibility = View.INVISIBLE
              ivClean.visibility = View.VISIBLE
  //            for ( i in mSaveList.indices ){
  //                mBeanList[i].isVisibily = true
  //                mBeanList[i].title = mSaveList[i]
  //                mHistoryArray[i].text = mSaveList[i]
  //                mHistoryArray[i].visibility = View.VISIBLE
  //
  //            }
          }
      }
  */
    private fun getBeanList(): ArrayList<SearchHistoryBean> {
        val beanList = ArrayList<SearchHistoryBean>()
        val mList: List<SearchHistoryBean> = LitePal.findAll<SearchHistoryBean>()
        if(mList.isNotEmpty()){
            tvNoHistory.visibility = View.INVISIBLE
            ivClean.visibility = View.VISIBLE
        }else{
            tvNoHistory.visibility = View.VISIBLE
            ivClean.visibility = View.INVISIBLE
        }
        //用 栈的数据结构 后进先出
        var range= mList.indices
        for ( i in range.reversed()){
            beanList.add(mList[i])
        }
        return beanList
    }

}