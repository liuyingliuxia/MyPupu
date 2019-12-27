package com.mmm.mypupu.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.widgets.CustomLayout
import com.mmm.mypupu.ui.widgets.SaveHistory
import com.mmm.mypupu.util.CountDown
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_search_history.*

class SearchHistoryFragmemt : Fragment(){
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_search_history, container, false)

        return mView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mHistoryArray : ArrayList<TextView> = arrayListOf(tvHistory1,tvHistory2,tvHistory3,tvHistory4,tvHistory5,tvHistory6)
        view.postInvalidate()
        addHistory(mHistoryArray)
        //清空历史记录
        ivClean.setOnClickListener { v -> run{
            AlertDialog.Builder(context!!)
                .setMessage("确认删除全部搜索历史")
                .setPositiveButton("确定") {
                        _,_ -> run{
                    ivClean.visibility = View.GONE
                    for ( i in 0..5)
                        mHistoryArray[i].visibility = View.INVISIBLE
                    tvNoHistory.visibility = View.VISIBLE
                    //清空sp
                   SaveHistory.clearSearchHistory(context)
                  }
                }
                .setNeutralButton("取消", null)
                .create()
                .show()
              } }
        //历史记录item点击事件：
        for ( i in 0..5) {

            if (mHistoryArray[i].text.isEmpty()){
                mHistoryArray[i].visibility = View.INVISIBLE
            }
            else
                mHistoryArray[i].visibility = View.VISIBLE

            mHistoryArray[i].setOnClickListener {
                kotlin.run {
                    activity!!.actSearch.setText(mHistoryArray[i].text)
                    // 直接进行搜索
                     toSearch()
                    hideKeyforard(view)
                    activity!!.actSearch.isFocusable = false
                }
            }
        }

        view.postInvalidate()
    }

    private fun toSearch () {

        val manager = activity!!.supportFragmentManager.beginTransaction()
        manager.replace(R.id.llContainer,SearchResultFragment())
        manager.commit()
    }

    //强制隐藏软键盘
    private fun hideKeyforard (view:View) {
        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    //添加历史记录itm到容器
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun addHistory (mHistoryArray :ArrayList<TextView>) {
        val mSaveList :List<String> = SaveHistory.getSearchHistory(context)
        if (mSaveList.isEmpty()){
            Log.e("无历史记录：",mSaveList.toString())
            for ( i in 0..5)
             mHistoryArray[i].visibility = View.INVISIBLE
            tvNoHistory.visibility = View.VISIBLE
            ivClean.visibility = View.INVISIBLE
        }
        else {
            Log.e("历史记录：",mSaveList.toString())
            tvNoHistory.visibility = View.INVISIBLE
            ivClean.visibility = View.VISIBLE
            for (i in 0..mSaveList.size.minus(1)) {
                 mHistoryArray[i].visibility = View.VISIBLE
                 mHistoryArray[i].setText( mSaveList[i])
            }
        }
    }
}