package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Catalog
import com.mmm.mypupu.ui.data.mSortHeadTag
import com.mmm.mypupu.util.mT
import kotlinx.android.synthetic.main.item_sort_right.view.*

class SortContentAdapter (private val context: Context , private val list: ArrayList<Catalog> ):RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, parent, false)
            val holder0 = Holder(itemView)
            return holder0

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val mCatalog :Catalog = list[position]
         val h = holder.itemView
         h.tag = position
         h.ivHead.setImageResource(mCatalog.mHeadImg)
         h.ivHead.setOnClickListener{run{
            mT.t(context, mSortHeadTag[position])
        }}
        
        val ItemIV :ArrayList<ImageView> = arrayListOf(h.ivSort1,h.ivSort2,h.ivSort3,h.ivSort4,h.ivSort5,h.ivSort6,h.ivSort7,h.ivSort8,h.ivSort9,h.ivSort10,
            h.ivSort11,h.ivSort12,h.ivSort3,h.ivSort14,h.ivSort15)
        
        val ItemTV :ArrayList<TextView> = arrayListOf(h.tvSort1,h.tvSort2,h.tvSort3,h.tvSort4,h.tvSort5,h.tvSort6,h.tvSort7,h.tvSort8,h.tvSort9,h.tvSort10,
            h.tvSort11,h.tvSort12,h.tvSort3,h.tvSort14,h.tvSort15)

        val ItemLL :ArrayList<LinearLayout> = arrayListOf(h.llSort1_1,h.llSort1_2,h.llSort1_3,h.llSort1_4,h.llSort1_5,h.llSort1_6,h.llSort1_7,h.llSort1_8,
            h.llSort1_9,h.llSort1_10, h.llSort1_11,h.llSort1_12,h.llSort1_13,h.llSort1_14,h.llSort1_15)
        
        val catalogIV :ArrayList<Int> = arrayListOf(mCatalog.mItemImg1,mCatalog.mItemImg2,mCatalog.mItemImg3,mCatalog.mItemImg4,mCatalog.mItemImg5,mCatalog.mItemImg6,
            mCatalog.mItemImg7,mCatalog.mItemImg8,mCatalog.mItemImg9,mCatalog.mItemImg10,mCatalog.mItemImg11,mCatalog.mItemImg12,mCatalog.mItemImg13,mCatalog.mItemImg14,
            mCatalog.mItemImg15)

        val catalogTV :ArrayList<String> = arrayListOf(mCatalog.mItemName1,mCatalog.mItemName2,mCatalog.mItemName3,mCatalog.mItemName4,mCatalog.mItemName5,mCatalog.mItemName6,
            mCatalog.mItemName7,mCatalog.mItemName8,mCatalog.mItemName9,mCatalog.mItemName10,mCatalog.mItemName11,mCatalog.mItemName12,mCatalog.mItemName13,mCatalog.mItemName14,
            mCatalog.mItemName15)

       // val closeImg :Drawable.ConstantState? = h.resources.getDrawable(R.drawable.close_2).constantState

        for (i in 0 until 14){
           // val trueImg :Drawable.ConstantState? = ItemIV[i]?.drawable.constantState
           // 判断为空时 不设置图片和文字
                ItemIV[i].setImageResource((catalogIV[i]))
                ItemTV[i].setText(catalogTV[i])
            if (ItemTV[i].text.toString() == "0"){
                Log.e("text bool", (ItemTV[i].text.toString() == "0").toString())
                ItemIV[i].visibility = View.GONE
                ItemTV[i].visibility = View.GONE
                ItemLL[i].visibility = View.GONE
            }

            if (!(ItemTV[i].text.toString() == "")){
                ItemLL[i].setOnClickListener { run {
                    mT.t(context, ItemTV[i].text.toString())
                }
           }
         }
       }
    }


}