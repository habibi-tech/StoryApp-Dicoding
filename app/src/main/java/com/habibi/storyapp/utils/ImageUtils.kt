package com.habibi.storyapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.habibi.storyapp.R

fun ImageView.setImage(source: String?) {
    Glide.with(this)
        .load(source)
        .centerCrop()
        .placeholder(androidx.constraintlayout.widget.R.color.material_grey_900)
        .error(R.drawable.ic_broken_image)
        .into(this)
}