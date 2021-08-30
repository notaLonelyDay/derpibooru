package me.lonelyday.derpibooru.ui

import androidx.recyclerview.widget.DiffUtil
import me.lonelyday.derpibooru.db.vo.Image

class ImageDiffUtilItemCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}