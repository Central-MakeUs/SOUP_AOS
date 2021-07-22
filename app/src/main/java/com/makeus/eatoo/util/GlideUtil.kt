package com.makeus.eatoo.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun glideUtil(context: Context, imageUrl: String?, iv: ImageView) {

    imageUrl?.let { imgUrl ->
        Glide.with(context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .centerCrop()
            )
            .into(iv)
    }
}