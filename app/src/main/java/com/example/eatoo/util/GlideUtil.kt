package com.example.eatoo.util

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.eatoo.R

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