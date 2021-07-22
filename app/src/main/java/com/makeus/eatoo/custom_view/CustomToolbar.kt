package com.makeus.eatoo.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.makeus.eatoo.R

class CustomToolbar : androidx.appcompat.widget.Toolbar {

    lateinit var leftIcon: ImageView
    lateinit var title: TextView
    lateinit var rightIcon: ImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
        getAttrs(attrs, defStyleAttr)
    }

    private fun init(context: Context?) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, false)
        addView(view)

        leftIcon = findViewById(R.id.iv_toolbar_left)
        title = findViewById(R.id.tv_toolbar_title)
        rightIcon = findViewById(R.id.iv_toolbar_right)
    }

    private fun getAttrs(attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {

        val leftIconDrawable = typedArray.getResourceId(R.styleable.CustomToolbar_leftIcon, 0)
        leftIcon.setImageResource(leftIconDrawable)

        var rightIconDrawable = typedArray.getResourceId(R.styleable.CustomToolbar_rightIcon, 0)
        rightIcon.setBackgroundResource(rightIconDrawable)

        val text = typedArray.getText(R.styleable.CustomToolbar_titleText)
        title.text = text

        typedArray.recycle()
    }

    fun setTitleText(titleStr: String) {
        title.text = titleStr
    }

    fun setLeftIconClickListener(listener: OnClickListener) {
        leftIcon.setOnClickListener(listener)
    }

    fun setRightIconClickListener(listener: OnClickListener) {
        rightIcon.setOnClickListener(listener)
    }
}