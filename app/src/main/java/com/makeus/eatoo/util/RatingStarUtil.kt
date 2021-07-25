package com.makeus.eatoo.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.makeus.eatoo.R

fun showRatingStartUtil(context : Context, rating : Int, iv : ImageView){
    when(rating){
        1 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_369))
        2-> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_368))
        3 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_367))
        4 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_366))
        5 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.group_365))
    }
}

fun showRatingOrangeStartUtil(context : Context, rating : Int, iv : ImageView){
    when(rating){
        1 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_group_416))
        2-> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_group_415))
        3 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_group_414))
        4 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_group_413))
        5 -> iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_group_412))
    }
}