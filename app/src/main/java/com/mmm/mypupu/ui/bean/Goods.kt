package com.mmm.mypupu.ui.bean

import java.io.Serializable

class Goods :Serializable{
    var mImgPath :Int = 0
    var mName : String = " "
    var mSubtitle :String = " "
    var mQuantity : String =" "
    var mRemark : String = " "
    var mPrice :Double = 0.0
    var mOriPrice :Double = 0.0
    var mNum :Int = 0

    constructor(
        mImgPath: Int,
        mName: String,
        mSubtitle: String,
        mQuantity: String,
        mRemark: String,
        mPrice: Double,
        mOriPrice: Double,
        mNum : Int
    ) {
        this.mImgPath = mImgPath
        this.mName = mName
        this.mSubtitle = mSubtitle
        this.mQuantity = mQuantity
        this.mRemark = mRemark
        this.mPrice = mPrice
        this.mOriPrice = mOriPrice
        this.mNum = mNum
    }


    override fun toString(): String {
        return "Goods(mImgPath='$mImgPath', mName='$mName', mSubtitle='$mSubtitle', mQuantity='$mQuantity', mRemark='$mRemark', mPrice=$mPrice, mOriPrice=$mOriPrice, mNum=$mNum)"
    }
}