package me.lonelyday.derpibooru.ui

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import me.lonelyday.api.models.Query
import me.lonelyday.derpibooru.db.vo.Image
import java.time.Duration

class ImageDiffUtilItemCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}

fun View.animateViewHeight(toInt: Int, duration: Long): ValueAnimator {
    val anim = ValueAnimator.ofInt(this.height, toInt)
    anim.addUpdateListener { valueAnimator ->
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.height =  valueAnimator.animatedValue as Int
        this.layoutParams = layoutParams
    }
    anim.duration = duration
    return anim
}

fun createDefaultQuery(): Query {
    return Query("safe")
}