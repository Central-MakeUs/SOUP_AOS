package com.makeus.eatoo.src.review.create_review.create_review2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCreateReview2Binding
import com.makeus.eatoo.src.home.create_group.model.Keyword
import com.makeus.eatoo.src.review.create_review.model.Review1Request
import com.makeus.eatoo.src.review.create_review.model.Review2Request
import com.makeus.eatoo.src.review.create_review.model.ReviewResponse
import com.makeus.eatoo.src.review.my_review.MyReviewActivity
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.glideUtil
import com.makeus.eatoo.util.roundAll
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI
import kotlin.math.roundToInt

class CreateReview2Activity
    : BaseActivity<ActivityCreateReview2Binding>(ActivityCreateReview2Binding::inflate),
    View.OnClickListener, CreateReview2View {

    private var rating = 0.0
    private var checkedIdx = -1
    private var reviewImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewListeners()
    }

    private fun setViewListeners() {
        binding.ivMenuNameDelete.setOnClickListener(this)
        binding.rlReviewImg.setOnClickListener(this)
        binding.btnRegisterReview.setOnClickListener(this)
        binding.ivKeyword.setOnClickListener(this)
        binding.etMenuName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ivMenuNameDelete.isVisible = p0.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.customToolbar.setLeftIconClickListener(this)
        binding.btnStar1.setOnClickListener(this)
        binding.btnStar2.setOnClickListener(this)
        binding.btnStar3.setOnClickListener(this)
        binding.btnStar4.setOnClickListener(this)
        binding.btnStar5.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_menu_name_delete -> binding.etMenuName.text.clear()
            R.id.rl_review_img -> loadGallery()
            R.id.btn_register_review -> registerReview()
            R.id.iv_keyword -> {
                binding.ivKeyword.visibility = View.GONE
                binding.etKeyword.visibility = View.VISIBLE
                initKeywordChips()
            }
            R.id.iv_toolbar_left -> {
                finish()
            }
            R.id.btn_star1 -> checkedIdx = 0
            R.id.btn_star2 -> checkedIdx = 1
            R.id.btn_star3 -> checkedIdx = 2
            R.id.btn_star4 -> checkedIdx = 3
            R.id.btn_star5 -> checkedIdx = 4
        }

        binding.clRating.children.forEachIndexed { index, view ->
            (view as ToggleButton).isChecked = index <= checkedIdx
        }
    }


    private fun loadGallery() {
        getImage.launch("image/*")
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                reviewImage = it
                glideUtil(this, it.toString(), roundAll(binding.ivReviewImg, 5))
                binding.ivReviewImg.isVisible = true
                binding.ivReviewImgIcon.isVisible = false
                binding.tvInputReviewImg.isVisible = false
            }
        }

    private fun registerReview() {

        if (binding.etMenuName.text.isEmpty()) {
            showCustomToast("메뉴 이름을 입력해주세요")
            return
        }

        if (binding.etShortReview.text.isEmpty()) {
            showCustomToast("한줄평을 입력해주세요")
            return
        }

        if (reviewImage == null) {
            showCustomToast("이미지를 입력해주세요")
            return
        }

        if (checkedIdx == -1) {
            showCustomToast("별점을 입력해주세요")
            return
        }

        loadFirebaseStorage(reviewImage!!)

    }

    private fun loadFirebaseStorage(firebaseUri: Uri) {
        showLoadingDialog(this)
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val ref = storageRef.child("${getUserIdx()}/review/${System.currentTimeMillis()}.png")
        ref.putFile(firebaseUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                dismissLoadingDialog()
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener {
            reviewImage = it.result
            setReviewReq()
        }.addOnFailureListener {
            showCustomToast("이미지를 다시 한 번 업로드 해주세요.")
        }

    }
    private fun setReviewReq() {

        val storeIdx = intent.getIntExtra("storeIdx", -1)
        if (storeIdx != -1) {
            val review1 = Review1Request(
                storeIdx = storeIdx,
                storeCategoryIdx = intent.getIntExtra("categoryIdx", 8),
                menuName = binding.etMenuName.text.toString(),
                contents = binding.etShortReview.text.toString(),
                imgUrl = reviewImage.toString(),
                rating = (checkedIdx + 1).toDouble(),
                postReviewKeywordReq = binding.flexboxReview.getAllChips(),
                link = if (binding.etReviewLink.text.isEmpty()) "" else binding.etReviewLink.text.toString()
            )
            registerReview1(review1)
        } else {
            val review2 = Review2Request(
                latitude = intent.getDoubleExtra("lat", -1.0),
                longitude = intent.getDoubleExtra("lng", -1.0),
                address = intent.getStringExtra("address") ?: "",
                storeName = intent.getStringExtra("store_name") ?: "",
                storeCategoryIdx = intent.getIntExtra("categoryIdx", 8),
                menuName = binding.etMenuName.text.toString(),
                contents = binding.etShortReview.text.toString(),
                imgUrl = reviewImage.toString(),
                rating = (checkedIdx + 1).toDouble(),
                link = if (binding.etReviewLink.text.isEmpty()) "" else binding.etReviewLink.text.toString(),
                postReviewKeywordReq = binding.flexboxReview.getAllChips()
            )
            registerReview2(review2)
        }

    }

    private fun registerReview1(review1: Review1Request) {
        CreateReview2Service(this).tryPostReview1(getUserIdx(), review1)
        Log.d("register2", review1.toString())
    }

    private fun registerReview2(review2: Review2Request) {
        CreateReview2Service(this).tryPostReview2(getUserIdx(), review2)
        Log.d("register2", review2.toString())
    }


    //keyword

    private fun initKeywordChips() {
        setKeywordEt()
        binding.etKeyword.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val keywordList = binding.flexboxReview.getAllChips()
                if (keywordList.size - 1 == 4) showCustomToast(resources.getString(R.string.keyword_num_limit))
                else {
                    val et = v as EditText
                    val keyword = et.text.toString()
                    if (keyword.length >= 11) showCustomToast(resources.getString(R.string.keyword_length_limit))
                    else {
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



    ////////server result

    override fun onPostReview1Success(response: ReviewResponse) {
        dismissLoadingDialog()
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_review1"))
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_my_review"))
        startActivity(Intent(this, MyReviewActivity::class.java))
        finish()
    }

    override fun onPostReview1Fail(message: String?) {
        dismissLoadingDialog()
        message?.let {
            showCustomToast(it)
        }
    }

    override fun onPostReview2Success(response: ReviewResponse) {
        dismissLoadingDialog()
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_review1"))
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("finish_my_review"))
        startActivity(Intent(this, MyReviewActivity::class.java))
        finish()
    }

    override fun onPostReview2Fail(message: String?) {
        dismissLoadingDialog()
        message?.let {
            showCustomToast(it)
        }
    }
}


