package com.mmm.mypupu.ui.bean

import com.mmm.mypupu.ui.data.*
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
        return " 你点击了：$mName, 价格是：$mPrice 元"
    }

    companion object{
        fun newGoods() :Goods{
            val goods = Goods(0,"","","","",0.0,0.0,0)
            val index :Int = (0 until goodsTitle.size).random()
            goods.mImgPath = goodsImg[index]
            goods.mName  = goodsTitle[index]
            goods.mSubtitle = goodsSubtitle[index]
            goods.mQuantity = goodsQuantity[index]
            goods.mRemark = goodsRemark[index]
            goods.mPrice = goodsPrice[index]
            goods.mOriPrice = goodsOriginPrice[index]
            goods.mNum = goodsNum[index]

            return goods
        }

        fun newFruit() :Goods{
            val fruit = Goods(0,"","","","",0.0,0.0,0)
            val index :Int = (0 until fruitImg.size).random()
            fruit.mImgPath = fruitImg[index]
            fruit.mName  = fruitTitle[index]
            fruit.mSubtitle = fruitSubtitle[index]
            fruit.mQuantity = fruitQuantity[index]
            fruit.mRemark = fruitRemark[index]
            fruit.mPrice = fruitPrice[index]
            fruit.mOriPrice = goodsOriginPrice[index]
            fruit.mNum = goodsNum[index]

            return fruit
        }

        fun newGoodsList(count:Int): ArrayList<Goods>{
          val list = ArrayList<Goods>()
            for (i in 0..count){
                list.add(newGoods())
            }
            return list
        }

        fun newFruitList(count:Int): ArrayList<Goods>{
            val list = ArrayList<Goods>()
            for (i in 0..count){
                list.add(newFruit())
            }
            return list
        }
    }
}