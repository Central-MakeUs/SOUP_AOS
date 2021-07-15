package com.example.eatoo.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import com.example.eatoo.R

class CustomToggleLayout : RelativeLayout {

    lateinit var title: TextView
    lateinit var toggleBtn: AppCompatToggleButton
    lateinit var rlToggle : RelativeLayout
    lateinit var viewToggle : View

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
        val view = LayoutInflater.from(context).inflate(R.layout.custom_toggle_layout, this, false)
        addView(view)

        title = findViewById(R.id.tv_toggle_title)
        toggleBtn = findViewById(R.id.toggle_custom)
        rlToggle = findViewById(R.id.rl_toggle)
        viewToggle = findViewById(R.id.view_custom_toggle)
    }

    private fun getAttrs(attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToggleLayout)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToggleLayout, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {

        val text = typedArray.getText(R.styleable.CustomToggleLayout_titleTextCustomToggle)
        title.text = text

        typedArray.recycle()
    }

    fun setToggleClickListener() {
        toggleBtn.setOnClickListener{
            if(toggleBtn.isChecked) title.setTextColor(ContextCompat.getColor(context, R.color.black))
            else title.setTextColor(ContextCompat.getColor(context, R.color.input_hint))
        }

        rlToggle.setOnClickListener {
            toggleBtn.isChecked = !toggleBtn.isChecked
            if(toggleBtn.isChecked) title.setTextColor(ContextCompat.getColor(context, R.color.black))
            else title.setTextColor(ContextCompat.getColor(context, R.color.input_hint))
        }
    }
}