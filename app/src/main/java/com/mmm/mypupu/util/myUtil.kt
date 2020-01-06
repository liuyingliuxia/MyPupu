package com.mmm.mypupu.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.mmm.mypupu.R
import com.mmm.mypupu.ui.bean.Goods
import kotlinx.android.synthetic.main.item_recommend.view.*
import kotlin.math.absoluteValue

class myUtil {
    companion object {

        fun jumpView(context: Context, newActivity: Activity) {
            val intent = Intent()
            intent.setClass(context, newActivity::class.java)
            context.startActivity(intent)
        }

        fun jumpView(context: Context, newFragment: Fragment) {
            val intent = Intent()
            intent.setClass(context, newFragment::class.java)
            context.startActivity(intent)
        }

        fun talk(context: Context, input: String) {
            if (input.length < 10)
                Toast.makeText(context, input, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, input, Toast.LENGTH_LONG).show()
        }

  /*      fun say( input: String) {
            if (input.length < 10)
                Toast.makeText(App.getInstance(), input, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(App.getInstance(), input, Toast.LENGTH_LONG).show()
        }

        fun say ( resId :Int ) {
            say (App.getInstance().getString(resId))
        }*/

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
            holder.itemView.tvNum.x -= 68
            // - 号 旋转平移 同时进行 用 属性动画
            val mIvSub = holder.itemView.ivSub
            val objectAnimatorR: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "rotation", 0f, 180f)
            val objectAnimatorX: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "translationX", 0f, -100f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(objectAnimatorR, objectAnimatorX)
            animatorSet.duration = 200
            animatorSet.start()
        }

        fun itemSubClickAnimator(holder: RecyclerView.ViewHolder) {
            //+ 号旋转 用 补间动画
            val mAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim._add)
            holder.itemView.ivAdd.startAnimation(mAnimation)
            // 数字部分直接平移 x轴
            holder.itemView.tvNum.x += 68
            // - 号 旋转平移 同时进行 用 属性动画
            val mIvSub = holder.itemView.ivSub
            val objectAnimatorRSub: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "rotation", -180f, 0f)
            val objectAnimatorTSubX: ObjectAnimator = ObjectAnimator.ofFloat(mIvSub, "translationX", -100f, 0f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(objectAnimatorTSubX, objectAnimatorRSub)
            animatorSet.duration = 200
            animatorSet.start()
        }

    }
}