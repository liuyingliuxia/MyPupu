package com.mmm.mypupu.ui.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.mmm.mypupu.ui.data.mAllItemImg1
import java.io.Serializable
//右侧下方 内部的recyclerview 内有若干个 条目 由1张 大图片 若干张小图片 和文字组成
data class RightItemBean (var id :Int,var ivCatalogHead :Int , var imgId:Int, var text :String ){
}