package com.makeus.eatoo.src.review.review_detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ToggleButton
import androidx.core.view.children
import androidx.core.view.isVisible
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.databinding.ActivityReviewDetailBinding
import com.makeus.eatoo.src.home.create_group.model.Keyword
import com.makeus.eatoo.src.review.review_detail.model.PatchReviewRequest
import com.makeus.eatoo.src.review.review_detail.model.ReviewDetailResponse
import com.makeus.eatoo.src.review.review_detail.model.ReviewDetailResult
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.glideUtil
import kotlin.math.roundToInt

class ReviewDetailActivity
    : BaseActivity<ActivityReviewDetailBinding>(ActivityReviewDetailBinding::inflate),
ReviewDetailView, View.OnClickListener{

    private var checkedIdx = 0
    private var reviewIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewIdx = intent.getIntExtra("reviewIdx", -1)
        getReviewDetail(reviewIdx)
        setClickListeners()

    }

    private fun setClickListeners() {
        binding.ivMenuNameDelete.setOnClickListener(this)
        binding.flCreateReviewKeyword.setOnClickListener(this)
        binding.flexboxReview.setOnClickListener(this)
        binding.btnStar1.setOnClickListener(this)
        binding.btnStar2.setOnClickListener(this)
        binding.btnStar3.setOnClickListener(this)
        binding.btnStar4.setOnClickListener(this)
        binding.btnStar5.setOnClickListener(this)
        binding.btnEditReview.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_menu_name_delete -> binding.etMenuName.text.clear()
            R.id.flexbox_review ->  {
                binding.etKeyword.isVisible = true
                binding.etKeyword.requestFocus()
            }
            R.id.btn_star1 -> checkedIdx = 0
            R.id.btn_star2 ->  checkedIdx = 1
            R.id.btn_star3 ->  checkedIdx = 2
            R.id.btn_star4 ->  checkedIdx = 3
            R.id.btn_star5 ->  checkedIdx = 4
            R.id.btn_edit_review -> getChangedReview()
        }

        binding.clRating.children.forEachIndexed { index, view ->
            (view as ToggleButton).isChecked = index <= checkedIdx
        }
    }



    private fun getReviewDetail(reviewIdx : Int) {
        if(reviewIdx != -1){
            showLoadingDialog(this)
            ReviewDetailService(this).tryGetReviewDetail(getUserIdx(), reviewIdx)
        }
    }


    private fun setReviewDetail(review: ReviewDetailResult) {
        Log.d("reviewDetail", review.toString())
        val categoryItem = resources.getStringArray(R.array.category_food)
        binding.tvStoreCategory.text = categoryItem[review.storeCategoryIdx-1]
        glideUtil(this, review.imgUrl, binding.ivStoreImage)
        binding.tvStoreName.text = review.storeName
        binding.tvStoreAddress.text = review.address
        binding.etMenuName.setText(review.menuName)
        binding.etShortReview.setText(review.contents)
        binding.etReviewLink.setText(review.link)
        review.getReviewKeywordRes.forEach {
            if(it.name.isNotEmpty()) binding.flexboxReview.addChip(it.name)
        }
        val ratingIdx = review.rating.toInt() -1
        initKeywordChips()
        binding.clRating.children.forEachIndexed { index, view ->
            if(index <= ratingIdx){
                (view as ToggleButton).isChecked = true
            }
        }



    }


    /////keyword

    private fun initKeywordChips() {
        setKeywordEt()
        binding.etKeyword.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val keywordList = binding.flexboxReview.getAllChips()
                if (keywordList.size-1 == 4) showCustomToast(resources.getString(R.string.keyword_num_limit))
                else {
                    val et = v as EditText
                    val keyword = et.text.toString()
                    if (keyword.length >= 11) showCustomToast(resources.getString(R.string.keyword_length_limit))
                    else{
                        binding.flexboxReview.addChip(keyword)
                        et.text.clear()
                    }
                }
            }
            false
        }
    }

    private fun setKeywordEt() {
        binding.etKeyword.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etKeyword, InputMethodManager.SHOW_IMPLICIT)
    }

    @SuppressLint("InflateParams")
    private fun FlexboxLayout.addChip(keyword: String) {
        val chip = LayoutInflater.from(context).inflate(R.layout.view_chip, null) as Chip
        chip.text = keyword
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = dpToPx(4)
        chip.setOnCloseIconClickListener {
            removeView(chip as View)
        }
        addView(chip, childCount - 1, layoutParams)
    }

    fun Context.dpToPx(dp: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).roundToInt()

    private fun FlexboxLayout.getAllChips(): List<Keyword> {
        var keywordList: MutableList<Keyword> = mutableListOf()
        (0 until childCount - 1).mapNotNull { index ->
            val childChip = getChildAt(index) as? Chip
            keywordList.add(Keyword(name = childChip?.text.toString()))
        }
        return keywordList
    }

    ////////edit review

    private fun getChangedReview() {
        var rating = 0
        binding.clRating.children.forEach {
            if((it as ToggleButton).isChecked) rating++
        }
        var menuName = ""
        var shortReview = ""

        if(binding.etMenuName.text.isEmpty()){
            showCustomToast("메뉴 이름을 입력해주세요")
            return
        }else {
            menuName = binding.etMenuName.text.toString()
        }

        if(binding.etShortReview.text.isEmpty()){
            showCustomToast("한줄평을 입력해주세요")
            return
        }else {
            shortReview = binding.etShortReview.text.toString()
        }



        val link = if(binding.etReviewLink.text.isEmpty()) "" else binding.etReviewLink.text.toString()

        val patchReview = PatchReviewRequest(
            contents = shortReview,
            link = link,
            menuName = menuName,
            patchReviewKeywordReq =  binding.flexboxReview.getAllChips(),
            rating = rating.toDouble()
        )

        patchReviewToServer(patchReview)

    }

    private fun patchReviewToServer(changedReview: PatchReviewRequest) {
        if(reviewIdx != -1) {
            showLoadingDialog(this)
            ReviewDetailService(this).tryPatchReview(getUserIdx(), reviewIdx, changedReview)
        }

    }


    ////////server result

    override fun onGetReviewDetailSuccess(response: ReviewDetailResponse) {
        dismissLoadingDialog()
        setReviewDetail(response.result)
    }



    override fun onGetReviewDetailFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
        finish()
    }

    override fun onPatchReviewSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        showCustomToast("후기 수정을 완료했습니다!")
        finish()
    }

    override fun onPatchReviewFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
        finish()
    }




}