package com.mmm.mypupu.ui.search

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.githang.statusbar.StatusBarCompat
import com.mmm.mypupu.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_search_history.*
import java.nio.file.WatchEvent

class SearchActivity : AppCompatActivity() {
    //历史记录最大item数 为 6
    val MAX_SEARCH_HISTORY_ITEM = 6
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //设置沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getColor(R.color.color1), true)
        //etSearch.addTextChangedListener()
        ivBack.setOnClickListener { v -> this.finish()}
    }
}
