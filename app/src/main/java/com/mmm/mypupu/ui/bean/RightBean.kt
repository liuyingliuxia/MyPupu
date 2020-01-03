package com.mmm.mypupu.ui.bean

import androidx.recyclerview.widget.RecyclerView
/*//右侧目录布局 使用vp2  嵌套 一个 recycler view
data class RightBean (var id :Int , var rvRightCatalog :RecyclerView ,var itemNum : Int){
    override fun toString(): String {
        return "这是第"+id+1 + "个viewPager , 里面是个rv: $rvRightCatalog ,共有 $itemNum 个item项"
    }
}*/

data class itemBean ( var id : Int , var imgId:Int, var text :String ) {
    override fun toString(): String {
        return "这是第"+ id+"个item ： $text "
    }
}


data class headImgBean ( var tag :String , var imgId :Int )