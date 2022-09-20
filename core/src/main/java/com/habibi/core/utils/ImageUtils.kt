package com.habibi.core.utils

import android.widget.ImageView
import androidx.constraintlayout.widget.R as ResourceConstrain
import com.bumptech.glide.Glide
import com.habibi.core.R

fun ImageView.setImage(source: String?) {
    Glide.with(this)
        .load(source)
        .centerCrop()
        .placeholder(ResourceConstrain.color.material_grey_900)
        .error(R.drawable.ic_broken_image)
        .into(this)
}