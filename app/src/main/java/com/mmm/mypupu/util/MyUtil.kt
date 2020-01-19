package com.mmm.mypupu.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.GoodsBean
import com.mmm.mypupu.ui.bean.SearchResultBean
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.item_recommend.view.*
import java.util.*
import kotlin.math.absoluteValue

class MyUtil {
    companion object {

        fun dip2px(dpValue: Float, context: Context): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun jumpView(context: Context, newActivity: Activity) {
            val intent = Intent()
            intent.setClass(context, newActivity::class.java)
            context.startActivity(intent)
        }


        fun getNow(): List<Int> {
    /*        if (android.os.Build.VERSION.SDK_INT >= 24) {
                return SimpleDateFormat(" HH:mm:ss").format(Date())
            } else {*/
                val tms = Calendar.getInstance()
                //时区： 东八区，北京时间 HOUR_OF_DAY 24小时制
                tms.timeZone = TimeZone.getTimeZone("GMT+8")
                return arrayListOf(tms.get(Calendar.HOUR_OF_DAY), tms.get(Calendar.MINUTE), tms.get(Calendar.SECOND))
                // }

            }

            fun talk(context: Context, input: String) {
                if (input.length < 10)
                    Toast.makeText(context, input, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, input, Toast.LENGTH_LONG).show()
            }


        fun toTwo(time: Int): String {
            if (time > 9)
                return time.toString()
            else
                return "0" + time.toString()
        }


            fun moveToMiddle(recyclerView: RecyclerView, position: Int) {
                val manager = recyclerView.layoutManager as GridLayoutManager
                val firstItem = manager.findFirstVisibleItemPosition()
                val lastItem = manager.findLastVisibleItemPosition()
                //中间位置
                val middle = (firstItem + lastItem) / 2
                val index = (position - middle).absoluteValue
                if (index >= recyclerView.childCount) {
                    recyclerView.scrollToPosition(position)
                } else {
                    if (position < middle) {
                        recyclerView.scrollBy(0, -recyclerView.getChildAt(index).top)
                    } else {
                        recyclerView.scrollBy(0, recyclerView.getChildAt(index).top)
                    }
                }
            }

            fun itemAddClickAnimator(holder: RecyclerView.ViewHolder) {
                //+ 号旋转 用 补间动画
                val mAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.add)
                holder.itemView.ivAdd.startAnimation(mAnimation)
                // 数字部分直接平移 x轴
                holder.itemView.tvNum.x -= 80
                // - 号 旋转平移 同时进行 用 属性动画
                val mIvSub = holder.itemView.ivSub
                val objectAnimatorR: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "rotation", 0f, 180f)
                val objectAnimatorX: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "translationX", 0f, -120f)
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(objectAnimatorR, objectAnimatorX)
                animatorSet.duration = 300
                animatorSet.start()
            }

            fun itemSubClickAnimator(holder: RecyclerView.ViewHolder) {
                //+ 号旋转 用 补间动画
                val mAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim._add)
                holder.itemView.ivAdd.startAnimation(mAnimation)
                // 数字部分直接平移 x轴
                holder.itemView.tvNum.x += 80
                // - 号 旋转平移 同时进行 用 属性动画
                val mIvSub = holder.itemView.ivSub
                val objectAnimatorRSub: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "rotation", -180f, 0f)
                val objectAnimatorTSubX: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "translationX", -120f, 0f)
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(objectAnimatorTSubX, objectAnimatorRSub)
                animatorSet.duration = 300
                animatorSet.start()
            }

            fun addStrikeout(v: TextView) {
                //给文字添加删除线
                v.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            //强制隐藏软键盘
            fun hideKeyforard(etSearch: EditText , myActivity: Activity) {
                etSearch.isFocusable = false
                etSearch.isFocusableInTouchMode = false
                val inputMethodManager = myActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(etSearch.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            // 获取焦点并 强制显示软键盘
            fun showKeyBoard(etSearch: EditText, window: Window) {
                etSearch.isFocusable = true
                etSearch.isFocusableInTouchMode = true
                etSearch.requestFocus()
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }

            //三种点击状态的切换
            private var rbClickTiems = 0
            fun changedItemState3(rb: RadioButton, mActivity: Activity) {
                val resultBean = SearchResultBean()
                when (rbClickTiems) {
                    0 -> {
                        //从小到大排序
                        rbClickTiems++
                        rb.setTextColor(ContextCompat.getColor(mActivity, R.color.color23))
                        val drawable = ContextCompat.getDrawable(mActivity, R.drawable.icon_indicate_arrow_top_selected)!!
                        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                        rb.setCompoundDrawables(null, null, drawable, null)
                        resultBean.goodsList!!.clear()
                        resultBean.goodsList!!.addAll(GoodsBean.newFruitList(5))
                    }
                    1 -> {
                        //从大到小排序
                        rbClickTiems++
                        rb.setTextColor(ContextCompat.getColor(mActivity, R.color.color23))
                        val drawable = ContextCompat.getDrawable(mActivity, R.drawable.icon_indicate_arrow_bottom_selected)!!
                        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                        rb.setCompoundDrawables(null, null, drawable, null)

                        resultBean.goodsList!!.clear()
                        resultBean.goodsList!!.addAll(GoodsBean.newFruitList(5))
                    }
                    2 -> {
                        //还原
                        rbClickTiems = 0
                        rb.setTextColor(ContextCompat.getColor(mActivity, R.color.color5))
                        val drawable = ContextCompat.getDrawable(mActivity, R.drawable.icon_indicate_arrow_no_selected)!!
                        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                        rb.setCompoundDrawables(null, null, drawable, null)
                        resultBean.goodsList!!.clear()
                        resultBean.goodsList!!.addAll(GoodsBean.newFruitList(5))
                    }
                }
            }

            //----------尺寸转换----------
            fun Int.dp2px(): Int {
                return (0.5f + this * Resources.getSystem().displayMetrics.density).toInt()
            }

            fun Int.px2dp(): Int {
                val scale = Resources.getSystem().displayMetrics.density
                return (this / scale + 0.5f).toInt()
            }

            fun Float.dp2px(): Float {
                return (0.5f + this * Resources.getSystem().displayMetrics.density)
            }

            fun Float.px2dp(): Float {
                val scale = Resources.getSystem().displayMetrics.density
                return (this / scale + 0.5f)
            }
        }
    }