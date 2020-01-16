package com.mmm.mytestutil.rvInRecycler

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.ParentBean
import com.mmm.mypupu.ui.bean.headImgBean
import com.mmm.mypupu.ui.bean.itemBean
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.fragment_sort.*
import kotlinx.android.synthetic.main.item_sort_right.view.*

//分类页面 右侧内容，使用vp2
//父适配器
class SortRightAdapter(var context: Context, var list: List<ParentBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_MUCH = 0
    private val TYPE_LESS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == TYPE_MUCH) {
//            //root = null 解决item太多时 显示不全的问题 (wrap_content)
//            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, null, false)
//            val holder = Holder(itemView)
//            return holder
//        } else {//item数少时 适配父容器（match_parent)
//            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, parent, false)
//            val holder = Holder(itemView)
//            return holder
//        }
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, parent, false)
        val holder = Holder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        if (list.size > 0) return list.size else return 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder.itemView
        val head = headImgBean(mSortHeadTag[position], mContentHeadImg[position])

        val rvAdapter = SortRightChildAdapter(head, addAllItem(position), context)
        val layoutManager = GridLayoutManager(context, 3)

        //设置根据类型不同，横跨不同列
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 3 else 1
            }
        }

        layoutManager.spanSizeLookup = spanSizeLookup

        layoutManager.orientation = GridLayoutManager.VERTICAL

        h.rvRightCatalog.layoutManager = layoutManager
        h.rvRightCatalog.adapter = rvAdapter
    }


//    override fun getItemViewType(position: Int): Int {
//        if (mSortNum[position] > 17) {
//            return TYPE_MUCH
//        } else {
//            return TYPE_LESS
//        }
//    }

    fun addAllItem(position: Int): ArrayList<itemBean> {
        val itemList: ArrayList<itemBean> = arrayListOf()
        val catalogImgList = arrayListOf(
            mCatalog1, mCatalog2, mCatalog3, mCatalog4, mCatalog5, mCatalog6, mCatalog7, mCatalog8, mCatalog9, mCatalog10, mCatalog11,
            mCatalog12, mCatalog13, mCatalog14, mCatalog15, mCatalog16, mCatalog17
        )

        val catalogTextList = arrayListOf(
            mCatalogName1, mCatalogName2, mCatalogName3, mCatalogName4, mCatalogName5, mCatalogName6, mCatalogName7, mCatalogName8, mCatalogName9, mCatalogName10, mCatalogName11,
            mCatalogName12, mCatalogName13, mCatalogName14, mCatalogName15, mCatalogName16, mCatalogName17
        )

        for (i in 0 until mSortNum[position]) {
            val iBean = itemBean(i, catalogImgList[position][i], catalogTextList[position][i])
            itemList.add(iBean)

        }
        addEmptyItem(mSortNum[position], itemList)
        return itemList
    }

    //添加空item 修复 item过多时(>15)  滑动冲突 直接进入下一页的bug
    fun addEmptyItem(count: Int , itemList:ArrayList<itemBean>) {
        if (count > 15) {
            if (count % 3 == 1) {
                //增加 3个 空item
                val emptyBean = itemBean(-1, 0, "")
                for (i in 0..2)
                    itemList.add(emptyBean)

            } else if (count % 3 == 2) {
                //增加 2 个空item
                val emptyBean = itemBean(-1, 0, "")
                for (i in 0..1)
                    itemList.add(emptyBean)
            }else if ( count % 3 == 0) {
                //增在 1个空item
                val emptyBean = itemBean(-1, 0, "")
                    itemList.add(emptyBean)
            }
        }
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)