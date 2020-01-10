package com.mmm.mypupu.ui.bean

import androidx.recyclerview.widget.RecyclerView
//右侧目录布局 使用vp2  嵌套 一个 recycler view

//图 + 文字 子布局的item
data class itemBean ( var id : Int , var imgId:Int, var text :String ) {
    override fun toString(): String {
        return "这是第"+ id+"个item ： $text "
    }
}

//顶部大图
data class headImgBean ( var tag :String , var imgId :Int )

//父布局的bean
data class ParentBean(var id:Int,var rv: RecyclerView )