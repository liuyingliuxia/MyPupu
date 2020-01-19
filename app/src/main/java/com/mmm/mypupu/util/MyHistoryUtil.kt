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
        //historyBean.id = nowLite.size
        if (nowLite.size < MAX_HISTORY ) {
            if (findResult.isEmpty()) {
                historyBean.save()
                Log.e("历史记录--",nowLite.toString())
            } else { //删除已存在的 插入新的
                Log.e("该数据已存在", "!")
                findResult[0].delete()
                historyBean.save()
            }
        } else if (nowLite.size == MAX_HISTORY ) {
            //数据>= 6 时 删除最早搜索的一项 就是  第一个项 再添加新的
            if (findResult.isEmpty()) {
                val history = LitePal.limit(1).find<SearchHistoryBean>()
                history[0].delete()
                historyBean.save()
                Log.e("历史记录--",nowLite.toString())
            }
        } else {
            Log.e("数据库 ", "数据异常 > 6!!!")

        }
    }
}