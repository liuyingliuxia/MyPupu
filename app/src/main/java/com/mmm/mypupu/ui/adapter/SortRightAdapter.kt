package com.mmm.mytestutil.rvInRecycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.TwoBean
import com.mmm.mypupu.ui.bean.headImgBean
import com.mmm.mypupu.ui.bean.itemBean
import com.mmm.mypupu.ui.data.*
import kotlinx.android.synthetic.main.item_sort_right.view.*

//分类页面 右侧内容，使用vp2
//父适配器
class SortVP2Adapter(var context: Context, var list: List<TwoBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_MUCH = 0
    private val TYPE_LESS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_MUCH) {
            //root = null 解决item太多时 显示不全的问题 (wrap_content) 
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, null, false)
            val holder = Holder(itemView)
            return holder
        } else {//item数少时 适配父容器（match_parent)
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_right, parent, false)
            val holder = Holder(itemView)
            return holder
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder.itemView
        val head = headImgBean(mSortHeadTag[position], mContentHeadImg[position])

        val rvAdapter = SortRightChildAdapter(head, addAllItem(position), context, h.rvRightCatalog)
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

    override fun getItemViewType(position: Int): Int {
       if (mSortNum[position]> 17){
           return TYPE_MUCH
       }else {
           return TYPE_LESS
       }
    }

    fun addAllItem(position: Int): ArrayList<itemBean> {
        val itemList: ArrayList<itemBean> = arrayListOf()
        val catalogImgList   = arrayListOf(mCatalog1, mCatalog2,mCatalog3, mCatalog4,mCatalog5, mCatalog6,mCatalog7, mCatalog8,mCatalog9, mCatalog10,mCatalog11,
            mCatalog12, mCatalog13, mCatalog14,mCatalog15, mCatalog16,mCatalog17)

        val catalogTextList   = arrayListOf(mCatalogName1, mCatalogName2,mCatalogName3, mCatalogName4,mCatalogName5, mCatalogName6,mCatalogName7, mCatalogName8,mCatalogName9, mCatalogName10,mCatalogName11,
            mCatalogName12, mCatalogName13, mCatalogName14,mCatalogName15, mCatalogName16,mCatalogName17)
        
        for ( i in 0 until mSortNum[position]){
            val iBean = itemBean( i , catalogImgList[position][i] , catalogTextList[position][i])
            itemList.add(iBean)

        }
/*        when (position) {
            0 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog1[i], mCatalogName1[i])
                    itemList.add(iBean)
                }
            }

            1 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog2[i], mCatalogName2[i])
                    itemList.add(iBean)
                }

            }
            2 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog3[i], mCatalogName3[i])
                    itemList.add(iBean)
                }
            }

            3 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog4[i], mCatalogName4[i])
                    itemList.add(iBean)
                }
            }
            4 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog5[i], mCatalogName5[i])
                    itemList.add(iBean)
                }

            }
            5 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog6[i], mCatalogName6[i])
                    itemList.add(iBean)
                }

            }

            6 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog7[i], mCatalogName7[i])
                    itemList.add(iBean)
                }
            }
            7 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog8[i], mCatalogName8[i])
                    itemList.add(iBean)
                }

            }
            8 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog9[i], mCatalogName9[i])
                    itemList.add(iBean)
                }
            }

            9 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog10[i], mCatalogName10[i])
                    itemList.add(iBean)
                }
            }
            10 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog11[i], mCatalogName11[i])
                    itemList.add(iBean)
                }

            }
            11 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog12[i], mCatalogName12[i])
                    itemList.add(iBean)
                }

            }

            12 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog13[i], mCatalogName13[i])
                    itemList.add(iBean)
                }
            }
            13 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog14[i], mCatalogName14[i])
                    itemList.add(iBean)
                }

            }
            14 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog15[i], mCatalogName15[i])
                    itemList.add(iBean)
                }
            }

            15 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog16[i], mCatalogName16[i])
                    itemList.add(iBean)
                }
            }
            16 -> {
                itemList.clear()
                for (i in 0 until mSortNum[position]) {
                    val iBean = itemBean(i, mCatalog17[i], mCatalogName17[i])
                    itemList.add(iBean)
                }

            }


        }*/

        return itemList
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)