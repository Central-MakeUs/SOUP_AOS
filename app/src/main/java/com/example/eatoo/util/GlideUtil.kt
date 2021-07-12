package com.example.eatoo.util

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eatoo.R

fun glideUtil(context: Context, imageUrl: String?, iv: ImageView) {

//    val curveRadius = 20F
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//        iv.outlineProvider = object : ViewOutlineProvider() {
//
//            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//            override fun getOutline(view: View?, outline: Outline?) {
//                outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius)
//            }
//        }
//
//        iv.clipToOutline = true
//
//    }

    imageUrl?.let { imgUrl ->
        Glide.with(context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .fitCenter()
            )
            .into(iv)
    }
//    iv.clipToOutline = true
}