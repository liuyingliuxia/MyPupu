package com.mmm.mypupu.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.GoodsBean
import kotlinx.android.synthetic.main.item_flash_sale.view.*
import kotlinx.android.synthetic.main.item_flash_sale_head.view.*
import kotlinx.android.synthetic.main.item_load_more.view.*
import kotlinx.android.synthetic.main.item_recommend.view.ivGoods
import kotlinx.android.synthetic.main.item_recommend.view.tvOriginalPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvPrice
import kotlinx.android.synthetic.main.item_recommend.view.tvQuantity
import kotlinx.android.synthetic.main.item_recommend.view.tvRemark
import kotlinx.android.synthetic.main.item_recommend.view.tvSubtitle
import kotlinx.android.synthetic.main.item_recommend.view.tvTitle
import kotlinx.android.synthetic.main.item_recommend.view.tvY2
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class FlashSaleAdapter(var list: ArrayList<GoodsBean>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_IMAGE = 0
    private val TYPE_GOODS = 1
    private val TYPE_END = 2
    private val TYPE_LOAD = 3
    private var mListSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_IMAGE) {
            val itemViewImg = LayoutInflater.from(context).inflate(R.layout.item_flash_sale_head, parent, false)
            val holder1 = Holder(itemViewImg)
            return holder1
        } else if (viewType == TYPE_GOODS) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_flash_sale, parent, false)
            val holder2 = Holder(itemView)
            return holder2
        } else if (viewType == TYPE_END) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_no_more, parent, false)
            val holder3 = Holder(itemView)
            return holder3
        } else {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false)
            val holder = Holder(itemView)
            return holder
        }
    }

    override fun getItemCount(): Int {
        return if (list.size > 0 ) list.size + 2 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //放在主线程 ，更新 ui
        if (TYPE_IMAGE == holder.itemViewType) {
            val maxHour  = 0
            val minHour = 0
            holder.itemView.tag = position
            val timeList = getNow()
            Log.e("now", timeList.toString())
            countDown(holder, timeList[2].minus(59).absoluteValue)

            val subMinu = timeList[1].minus(59).absoluteValue
            if (subMinu >= 0 && subMinu <= 9)
                holder.itemView.tvMinute.setText(toTwo( subMinu.absoluteValue))
            else if (subMinu > 9)
                holder.itemView.tvMinute.setText(subMinu.toString())

            when (timeList[0] > minHour && timeList[0] <= maxHour) {
                minHour == 0, maxHour == 9 -> {
                    holder.itemView.tvHour.setText(toTwo((8.minus(timeList[0])).absoluteValue))
                }
                minHour == 9 , maxHour == 11 -> {
                    holder.itemView.tvHour.setText(toTwo((10.minus(timeList[0])).absoluteValue))
                }

                minHour == 11 , maxHour == 14 -> {
                    holder.itemView.tvHour.setText(toTwo((13.minus(timeList[0])).absoluteValue))
                }
                minHour == 14 , maxHour == 16 -> {
                    holder.itemView.tvHour.setText(toTwo((15.minus(timeList[0])).absoluteValue))
                }
                minHour == 16 , maxHour == 24 -> {
                    holder.itemView.tvHour.setText(toTwo((23.minus(timeList[0])).absoluteValue))
                }
            }

        }

        if (position > 0 && position != itemCount - 1 ) {
            val goods: GoodsBean = list[position - 1]

             if (TYPE_GOODS == holder.itemViewType) {
                holder.itemView.tag = position
                holder.itemView.ivGoods.setImageResource(goods.mImgPath)
                holder.itemView.tvTitle.text = goods.mName
                holder.itemView.tvSubtitle.text = goods.mSubtitle
                holder.itemView.tvQuantity.text = goods.mQuantity
                holder.itemView.tvRemark.text = goods.mRemark
                holder.itemView.tvPrice.text = goods.mPrice.toString()
                holder.itemView.tvOriginalPrice.text = goods.mOriPrice.toString()
                //给文字添加删除线
                holder.itemView.tvY2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.itemView.tvOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                if (goods.mRemark.isEmpty()) {
                    holder.itemView.tvRemark.visibility = View.INVISIBLE
                }
                holder.itemView.llItemFlash.setOnClickListener {
                    run {
                        Toast.makeText(context, goods.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                holder.itemView.tvNowRush.setOnClickListener {
                    run {
                        Toast.makeText(context, "抢购成功！", Toast.LENGTH_SHORT).show()
                        holder.itemView.tvNowRush.isClickable = false
                    }
                }
            }
        } else if (TYPE_LOAD == holder.itemViewType) {
            //手动开启加载 动画
            val mLoading :AnimationDrawable =  holder.itemView.ivLoading.drawable as AnimationDrawable
            mLoading.start()
        } else if (TYPE_END == holder.itemViewType){
         //   list.removeAt(position)
           // notifyDataSetChanged()
        }
    }

    val noGoods = GoodsBean(0,"","","","",0.0,0.0,0)

    override fun getItemViewType(position: Int): Int {
        //数据加载 >50 时 显示 到底了 没有更多
        if (position == 0)
            return TYPE_IMAGE
        else if (position == itemCount - 1 && position < 50 ){
            mListSize = list.size
            return TYPE_LOAD
        }
        else if (position == itemCount - 1&& position >= 50 ){
            return TYPE_END
        }
        else
            return TYPE_GOODS
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun countDown(holder: RecyclerView.ViewHolder, second: Int) {
        object : CountDownTimer(second.times(1000).toLong(), 1000) {
            override fun onFinish() {
                val subMinu = getNow()[1].minus(59).absoluteValue
                if (subMinu == 0) {
                    holder.itemView.tvMinute.setText("59")
                    countDown2(holder)
                } else {
                    holder.itemView.tvMinute.setText(toTwo(subMinu.minus(1)))
                    countDown2(holder)
                }
            }

            override fun onTick(millisUntilFinished: Long) {

                val sec = millisUntilFinished.div(1000)
                holder.itemView.tvSecond.setText(toTwo(sec.toInt()))
            }
        }.start()
    }

    fun countDown2(holder: RecyclerView.ViewHolder) {
        object : CountDownTimer(60.times(1000).toLong(), 1000) {
            override fun onFinish() {
                var min = getNow()[1]
                holder.itemView.tvMinute.setText(min.minus(1).toString())
                start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished.div(1000)
                holder.itemView.tvSecond.setText(toTwo(sec.toInt()))
            }
        }.start()
    }

    fun getNow(): List<Int> {
        /*   if (android.os.Build.VERSION.SDK_INT >= 24){
               return SimpleDateFormat(" HH:mm:ss").format(Date())
           }else{*/
        var tms = Calendar.getInstance()
        //时区： 东八区，北京时间 HOUR_OF_DAY 24小时制
        tms.timeZone = TimeZone.getTimeZone("GMT+8")
        val mTimeList = arrayListOf<Int>(tms.get(Calendar.HOUR_OF_DAY), tms.get(Calendar.MINUTE), tms.get(Calendar.SECOND))
        return mTimeList
        // }

    }

    fun toTwo(time: Int): String {
        if (time > 9)
            return time.toString()
        else
            return "0" + time.toString()
    }
}