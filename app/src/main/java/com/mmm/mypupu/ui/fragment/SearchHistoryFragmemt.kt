package com.mmm.mypupu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.widgets.CustomLayout
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_search_history.*

class SearchHistoryFragmemt : Fragment(){
    val mHistoryArray = ArrayList<TextView>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_search_history, container, false)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivClean.setOnClickListener { v -> run{
            AlertDialog.Builder(context!!)
                .setMessage("确认删除全部搜索历史")
                .setPositiveButton("确定") {
                        _,_ -> run{
                    ivClean.visibility = View.GONE
                    tvNoHistory.visibility = View.VISIBLE
                 //   tvHistoryItem.visibility = View.GONE
                  }
                }
                .setNeutralButton("取消", null)
                .create()
                .show()
              } }
    }


 /*   //添加历史记录itm到容器
    private fun addHistory ():TextView {
        val mAllHistory : CustomLayout = findViewById(R.id.clHistoryContent)
        this.layoutInflater.inflate(R.layout.item_search_history, mAllHistory)
        return tvHistoryItem
    }*/
}