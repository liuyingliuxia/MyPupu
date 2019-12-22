package com.mmm.mypupu.ui.search

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_search_history.*
import java.nio.file.WatchEvent

class SearchActivity : AppCompatActivity(),TextWatcher {
    //历史记录最大item数 为 6
    val MAX_SEARCH_HISTORY_ITEM = 6
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)


        ivBack.setOnClickListener { v -> this.finish()}
        ivClean.setOnClickListener { v -> run{
            AlertDialog.Builder(this)
                .setMessage("确认删除全部搜索历史")
                .setPositiveButton("确定") {
                        _,_ -> run{
                    ivClean.visibility = View.GONE
                    tvNoHistory.visibility = View.VISIBLE
                    tvHistoryItem.visibility = View.GONE
                    }
                }
                .setNeutralButton("取消", null)
                .create()
                .show()
            }
        }
        etSearch.addTextChangedListener(this)
        etSearch.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    addHistory()
                }
                return false
            }
        })

    }

    override fun afterTextChanged(p0: Editable?) {


    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    fun addHistory () {
        val mGl :GridLayout = findViewById<GridLayout>(R.id.glContainer)
        this.layoutInflater.inflate(R.layout.item_search_history, mGl)
        tvHistoryItem.text = etSearch.text
        tvHistoryItem.visibility = View.VISIBLE
    }

}
