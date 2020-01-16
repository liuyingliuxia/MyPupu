package com.mmm.mypupu.ui.bean

//图 + 文字 子布局的item
data class ItemBean ( var id : Int , var imgId:Int, var text :String ) {
    override fun toString(): String {
        return "这是第"+ id+"个item ： $text "
    }
}
