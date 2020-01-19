package com.mmm.mypupu.util

import android.util.Log
import android.widget.EditText
import com.mmm.mypupu.ui.bean.SearchHistoryBean
import kotlinx.android.synthetic.main.activity_search.*
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll
import org.litepal.extension.min
import java.util.*
import kotlin.math.min

const val MAX_HISTORY = 6 //最多显示的item数目

object MyHistoryUtil {
    fun addHistory(etSearch: EditText) {

        val historyBean = SearchHistoryBean()
        historyBean.title = etSearch.text.toString()

        val findResult = LitePal.where("title = ?", historyBean.title).find<SearchHistoryBean>()
        //最多存6条数据 超出6条时 把最早存进来的第一条数据更新
        val nowLite = LitePal.findAll<SearchHistoryBean>()

        if (findResult.isEmpty() && nowLite.size < MAX_HISTORY) { //是新的记录 当前< 6  直接save
            historyBean.save()
        } else if (findResult.isEmpty() && nowLite.size == MAX_HISTORY) { //是新的记录 当前 ==6 删除第一条 插入新的
            nowLite[0].delete()
            historyBean.save()
        } else if (findResult.isNotEmpty()) { //是 旧的记录  直接删除旧的 插入新的
            Log.e("该数据已存在", "!")
            findResult[0].delete()
            historyBean.save()
        }
    }
}