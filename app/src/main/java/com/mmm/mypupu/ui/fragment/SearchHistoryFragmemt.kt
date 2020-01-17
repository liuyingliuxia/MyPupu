package com.mmm.mypupu.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import com.mmm.mypupu.util.HistoryUtil
import com.mmm.mypupu.util.myUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*

class SearchHistoryFragmemt : Fragment() {
    private lateinit var mHistoryArray: ArrayList<TextView>
    val historyList = arrayListOf<SearchHistoryBean>()

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
        mHistoryArray = arrayListOf(tvHistory1, tvHistory2, tvHistory3, tvHistory4, tvHistory5, tvHistory6)
        for ( i in mHistoryArray.indices){
            val historyBean =SearchHistoryBean(mHistoryArray[i].text.toString() ,mHistoryArray[i].isVisible )
            historyList.add(historyBean)
        }

        addHistory(historyList)
        //清空历史记录
        ivClean.setOnClickListener {
            AlertDialog.Builder(context!!)
                .setMessage("确认删除全部搜索历史")
                .setPositiveButton("确定") { _, _ ->
                    ivClean.visibility = View.GONE
                    for (i in 0..5)
                        mHistoryArray[i].visibility = View.INVISIBLE
                    tvNoHistory.visibility = View.VISIBLE
                    //清空sp
                    HistoryUtil.clearSearchHistory(context!!)
                }
                .setNeutralButton("取消", null)
                .create()
                .show()
        }

        //历史记录item点击事件：
        for (i in 0..5) {
            if (mHistoryArray[i].text.isEmpty()) {
                mHistoryArray[i].visibility = View.INVISIBLE
            } else
                mHistoryArray[i].visibility = View.VISIBLE

            mHistoryArray[i].setOnClickListener {
                activity!!.etSearch.setText(mHistoryArray[i].text)
                // 直接进行搜索
                toSearch()
                myUtil.hideKeyforard(view, activity!!)
                activity!!.etSearch.isFocusable = false
            }
        }
        view.postInvalidate()
    }

    //开始 搜索 显示结果页
    private fun toSearch() {
        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.llContainer, SearchResultFragment())
        manager.commit()
    }

    //添加历史记录itm到容器
    private fun addHistory(mBeanList: ArrayList<SearchHistoryBean>) {
        val mSaveList: List<String> = HistoryUtil.getSearchHistory(context!!) // 获取 sp 中存储的历史记录
        if (mSaveList.isEmpty()) {
            for ( i in mBeanList.indices){
                mBeanList[i].isVisibily = false
                mHistoryArray[i].visibility = View.INVISIBLE
            }
            tvNoHistory.visibility = View.VISIBLE
            ivClean.visibility = View.INVISIBLE
        } else {
            Log.e("历史记录：", mSaveList.toString())

            tvNoHistory.visibility = View.INVISIBLE
            ivClean.visibility = View.VISIBLE
            for ( i in mSaveList.indices ){
                mBeanList[i].isVisibily = true
                mBeanList[i].title = mSaveList[i]
                mHistoryArray[i].text = mSaveList[i]
                mHistoryArray[i].visibility = View.VISIBLE

            }
        }
    }
}