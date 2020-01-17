package com.mmm.mypupu.ui.bean

import org.litepal.crud.LitePalSupport

class SearchHistoryBean : LitePalSupport() {
    var id: Int = 0 //数据添加的顺序 越早的 id 越小
    var title: String = ""
    override fun toString(): String {
        return "SearchHistoryBean(id=$id, 历史记录='$title')"
    }
}