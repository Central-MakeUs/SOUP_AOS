package com.makeus.eatoo.util

import android.content.res.Resources
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.makeus.eatoo.config.ApplicationClass

val Int.dpToFloat: Float
    get() = (this * Resources.getSystem().displayMetrics.density).toFloat()

fun roundLeft(iv: ImageView, curveRadius : Int)  : ImageView {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        iv.outlineProvider = object : ViewOutlineProvider() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, (view!!.width+curveRadius).toInt(), (view.height).toInt(), curveRadius.dpToFloat)
            }
        }

        iv.clipToOutline = true
    }
    return iv
}

fun roundAll(iv: ImageView, curveRadius : Int)  : ImageView {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        iv.outlineProvider = object : ViewOutlineProvider() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, curveRadius.dpToFloat)
            }
        }

        iv.clipToOutline = true
    }
    return iv
}

fun roundTop(iv: ImageView, curveRadius : Int)  : ImageView {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        iv.outlineProvider = object : ViewOutlineProvider() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius.dpToFloat)
            }
        }

        iv.clipToOutline = true
    }
    return iv
}