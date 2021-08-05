package com.makeus.eatoo.src.mypage.keyword

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.databinding.ActivityDietKeywordBinding
import com.makeus.eatoo.src.mypage.keyword.dialog.DietKeywordAddDialog
import com.makeus.eatoo.src.mypage.keyword.dialog.DietKeywordAddDialogInterface
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordInfo
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordReq
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordResponse
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordResult
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName
import org.w3c.dom.Text

class DietKeywordActivity
    : BaseActivity<ActivityDietKeywordBinding>(ActivityDietKeywordBinding::inflate), DietKeywordAddDialogInterface,
View.OnClickListener, DietKeywordView{

    private var dietKeywordList = arrayListOf<DietKeywordInfo>()
    private var dietKeywordCircleList = arrayListOf<TextView>()
    private var dietKeywordReqList = arrayListOf<DietKeywordReq>()
    private var x = 0f
    private var y = 0f
    private var dx: Float = 0.0f
    private var dy: Float = 0.0f
    private var isAdded = false
        var parentX : Int? = 0
        var parentY : Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDietKeyword()
        binding.tvUsernameDietKeyword.text = String.format(resources.getString(R.string.input_diet_keyword1), getUserNickName())
        binding.ivKeywordAdd.setOnClickListener(this)
        binding.registerKeywordBtn.setOnClickListener (this)


    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_keyword_add -> {
                val dialog = DietKeywordAddDialog(this, this)
                dialog.show()
            }
            R.id.register_keyword_btn -> {
                if(isAdded) saveDietKeyword()
                else {
                    showCustomToast("키워드를 추가해주세요!")
                }
            }
        }
    }

    /**
     * 키워드 만들기
     */

    override fun onDietKeywordAddClicked(dietKeywordInfo: DietKeywordInfo) {
        isAdded = true
        dietKeywordList.add(dietKeywordInfo)
        Log.d("dietKeywordAct", dietKeywordInfo.toString())
        when(dietKeywordInfo.size){
            1 -> makeCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, resources.getDimension(R.dimen.diet_keyword_size1),
                resources.getDimension(R.dimen.diet_keyword_text_size1))
            2 -> makeCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, resources.getDimension(R.dimen.diet_keyword_size2),
                resources.getDimension(R.dimen.diet_keyword_text_size2))
            3 -> makeCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, resources.getDimension(R.dimen.diet_keyword_size3),
                resources.getDimension(R.dimen.diet_keyword_text_size3))
            4 -> makeCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, resources.getDimension(R.dimen.diet_keyword_size4),
                resources.getDimension(R.dimen.diet_keyword_text_size4))
            5 -> makeCircle(dietKeywordInfo.name, dietKeywordInfo.isPrefer, resources.getDimension(R.dimen.diet_keyword_size5),
                resources.getDimension(R.dimen.diet_keyword_text_size5))
        }
    }

    @SuppressLint("InflateParams")
    private fun makeCircle(name: String, isPrefer: String, size: Float, sizeText : Float) {
        val mInflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dynamicCircle: View = mInflater.inflate(R.layout.view_diet_keyword, null)
        (dynamicCircle as TextView).apply {
            layoutParams = LinearLayout.LayoutParams(size.toInt(), size.toInt())
            background = if(isPrefer == "Y") ContextCompat.getDrawable(this@DietKeywordActivity, R.drawable.background_keyword_pos)
            else ContextCompat.getDrawable(this@DietKeywordActivity, R.drawable.background_keyword_neg)

            x = binding.clParent.width/2.toFloat()
            y = binding.clParent.height/2.toFloat()
            text = name
            setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText)
            setOnTouchListener(myTouchListener)
        }
        dietKeywordCircleList.add(dynamicCircle)
        binding.clParent.addView(dynamicCircle, binding.clParent.childCount)
    }

    /**
     * 키워드 가져오기
     */

    private fun getDietKeyword() {
        showLoadingDialog(this)
        DietKeywordService(this).tryGetDietKeyword(getUserIdx())
    }


    @SuppressLint("InflateParams")
    private fun showKeywordCircle(name: String, isPrefer: String, size: Float, sizeText : Float, locX : Float, locY : Float) {
        val mInflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dynamicCircle: View = mInflater.inflate(R.layout.view_diet_keyword, null)
        (dynamicCircle as TextView).apply {
            layoutParams = LinearLayout.LayoutParams(size.toInt(), size.toInt())
            background = if(isPrefer == "Y") ContextCompat.getDrawable(this@DietKeywordActivity, R.drawable.background_keyword_pos)
            else ContextCompat.getDrawable(this@DietKeywordActivity, R.drawable.background_keyword_neg)

            x = locX
            y = locY
            text = name
            setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeText)
//            setOnTouchListener(myTouchListener)
        }
//        dietKeywordCircleList.add(dynamicCircle)
        binding.clParent.addView(dynamicCircle, binding.clParent.childCount)
    }

    /**
     * 키워드 움직임 처리
     */


    private val myTouchListener = object : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View?, event: MotionEvent?): Boolean {
            parentX = (view?.parent as? ViewGroup)?.width
            parentY = (view?.parent as? ViewGroup)?.height

            Log.d("dietKeywordAct", "parentx : $parentX, parenty : $parentY")
            val topHeightLimit = binding.clTvContainer.height + binding.cardviewToolbar.height
            val bottomHeightLimit = parentY?.minus(binding.llRegisterBtnContainer.height)
//            Log.d("dietKeywordAct", topHeightLimit.toString())
//            Log.d("dietKeywordAct", bottomHeightLimit.toString())

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.x
                    y = event.y
                }
                MotionEvent.ACTION_MOVE -> {

                    dx = event.x - (view?.width?.div(2) ?: 0)
                    dy = event.y - (view?.height?.div(2) ?: 0)

                    view?.x = view?.x?.plus(dx)!!
                    view?.y = view?.y?.plus(dy)

                }
                MotionEvent.ACTION_UP -> {
                    view?.x?.let {
                        if(it <0) view.x = 0f
                        else if(it + view.width > parentX!!) view.x = (parentX!! - view.width).toFloat()
                    }
                    view?.y?.let {
                        if(it < topHeightLimit) view.y = topHeightLimit.toFloat()
                        else if(it + view.height > bottomHeightLimit!!) view.y = (bottomHeightLimit - view.height).toFloat()
                    }
                }
            }
            binding.root.invalidate()
            return true
        }
    }

    /**
     * 키워드 등록하기
     */

    private fun saveDietKeyword() {
        Log.i("dietKeywordAct", dietKeywordCircleList.toString())
        dietKeywordCircleList.forEachIndexed { index, textView ->
            textView.setOnTouchListener(null)
            Log.i("dietKeywordAct", "tv x : ${textView.x}, tv y : ${textView.y}")
            dietKeywordReqList.add(DietKeywordReq(
                dietKeywordList[index].name,
                dietKeywordList[index].isPrefer,
                dietKeywordList[index].size,
                dietKeywordCircleList[index].x.toDouble(),
                dietKeywordCircleList[index].y.toDouble()
            ))
        }
        postKeyword(dietKeywordReqList)
    }

    private fun postKeyword(dietKeywordReqList : ArrayList<DietKeywordReq>) {
        //server
        Log.d("dietKeywordAct", dietKeywordReqList.toString())
        showLoadingDialog(this)
        DietKeywordService(this).tryPostDietKeyword(getUserIdx(), dietKeywordReqList.toList())
    }

    /**
     * server result
     */

    override fun onGetDietKeywordSuccess(response: DietKeywordResponse) {
        dismissLoadingDialog()
        Log.d("dietKeywordAct", response.result.toString())
        response.result.forEach {
//            dietKeywordList.add(DietKeywordInfo(it.name, it.isPrefer, it.size))
            when(it.size){
                1 -> showKeywordCircle(it.name, it.isPrefer, resources.getDimension(R.dimen.diet_keyword_size1),
                    resources.getDimension(R.dimen.diet_keyword_text_size1), it.x.toFloat(), it.y.toFloat())
                2 -> showKeywordCircle(it.name, it.isPrefer, resources.getDimension(R.dimen.diet_keyword_size2),
                    resources.getDimension(R.dimen.diet_keyword_text_size2), it.x.toFloat(), it.y.toFloat())
                3 -> showKeywordCircle(it.name, it.isPrefer, resources.getDimension(R.dimen.diet_keyword_size3),
                    resources.getDimension(R.dimen.diet_keyword_text_size3), it.x.toFloat(), it.y.toFloat())
                4 -> showKeywordCircle(it.name, it.isPrefer, resources.getDimension(R.dimen.diet_keyword_size4),
                    resources.getDimension(R.dimen.diet_keyword_text_size4), it.x.toFloat(), it.y.toFloat())
                5 -> showKeywordCircle(it.name, it.isPrefer, resources.getDimension(R.dimen.diet_keyword_size5),
                    resources.getDimension(R.dimen.diet_keyword_text_size5), it.x.toFloat(), it.y.toFloat())
            }
        }
    }

    override fun onGetDietKeywordFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPostDietKeywordSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        showCustomToast("키워드가 등록되었습니다. ")
        isAdded = false
    }

    override fun onPostDietKeywordFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}