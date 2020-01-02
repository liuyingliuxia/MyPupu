package com.mmm.mypupu.ui.bean

import java.io.Serializable

data class RightBean2 (var id :Int, var ivHead:Int, var ivItem :Int, var tvTitle :String, var itemNum :Int) : Serializable {

    companion object {
        fun newRight(): RightBean2 {
            val right = RightBean2(0,0,0,"",0)
//            val index: Int = (0 until mAllName1.size).random()
            return right
        }

        fun newCatalogList() {

        }
    }
}