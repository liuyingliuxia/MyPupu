package com.mmm.mypupu.util

import android.content.Context
import android.util.Log
import java.util.*

//废弃   使用sqlite存取数据
object HistoryUtil {
    private const val PREFERENCE_NAME = "SAVE_HISTORY"
    private const val SEARCH_HISTORY = "HISTORY"
    // 保存搜索记录
   /* fun saveSearchHistory(inputText: String, context: Context) {
        val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val oldHistory = sp.getString(SEARCH_HISTORY, "") //获取之前保存的历史记录
        val tmpHistory = oldHistory!!.split(",").toTypedArray() //逗号截取 保存在数组中
        val historyList = ArrayList(listOf(*tmpHistory)) //将改数组转换成ArrayList
        val editor = sp.edit()

        if (historyList.size > 0) { //1.移除之前重复添加的元素
            for (i in historyList.indices) {
                if (inputText == historyList[i]) {
                    historyList.removeAt(i)
                    break
                }
            }
            historyList.add(0, inputText) //将新输入的文字添加集合的第0位也就是最前面(2.倒序)
            if (historyList.size > 6) {
                historyList.removeAt(historyList.size - 1) //3.最多保存6条搜索记录 删除最早搜索的那一项
            }
            //逗号拼接
            val sb = StringBuilder()
            for (i in historyList.indices) {
                sb.append(historyList[i] + ",")
            }
            //保存到sp
            editor.putString(SEARCH_HISTORY, sb.toString())
            Log.e("保存的数据", sb.toString())
            editor.apply()
        } else { //之前未添加过 size == 0
            editor.putString(SEARCH_HISTORY, "$inputText,")
            editor.commit()
        }
    }*/

    //获取搜索记录
    fun getSearchHistory(context: Context): List<String> {
        val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val longHistory = sp.getString(SEARCH_HISTORY, "")
        val tmpHistory = longHistory!!.split(",").toTypedArray() //split后长度为1有一个空串对象
        val historyList = ArrayList(listOf(*tmpHistory))
        if (historyList.size == 1 && historyList[0] == "") { //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear() //清空集合，这个很关键
        }
        Log.e("输出的数据", historyList.toString())
        return historyList
    }

    fun clearSearchHistory(context: Context) {
        val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}