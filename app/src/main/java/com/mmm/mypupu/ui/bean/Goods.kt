package com.mmm.mypupu.ui.bean

import java.io.Serializable

class Goods :Serializable{
    var mImgPath :String = " "
    var mName : String = " "
    var mSubtitle :String = " "
    var mQuantity : String =" "
    var mRemark : String = " "
    var mPrice :Double = 0.0
    var mOriPrice :Double = 0.0
    var mNum :Int = 0
    override fun toString(): String {
        return "Goods(mImgPath='$mImgPath', mName='$mName', mSubtitle='$mSubtitle', mQuantity='$mQuantity', mRemark='$mRemark', mPrice=$mPrice, mOriPrice=$mOriPrice, mNum=$mNum)"
    }
}