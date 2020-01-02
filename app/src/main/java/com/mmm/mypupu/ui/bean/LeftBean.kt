package com.mmm.mypupu.ui.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

data class LeftBean (var id:Int, var text:String , var type : Int) :MultiItemEntity,Serializable {

    override fun getItemType(): Int {
        return type
    }
     companion object {
          val TYPE_TEXT = 1
          val TYPE_EMPTY = 2
     }

}