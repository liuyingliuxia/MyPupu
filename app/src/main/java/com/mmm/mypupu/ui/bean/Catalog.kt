package com.mmm.mypupu.ui.bean

import java.io.Serializable


class Catalog : Serializable {
    var mHeadImg :Int = 0
    var mItemImg : Int = 0
    var mItemName :String = " "


    constructor(mHeadImg: Int, mItemImg: Int, mItemName: String) {
        this.mHeadImg = mHeadImg
        this.mItemImg = mItemImg
        this.mItemName = mItemName
    }

    constructor(mHeadImg: Int) {
        this.mHeadImg = mHeadImg
    }

    override fun toString(): String {
        return " 你点击了：$mItemName"
    }
}