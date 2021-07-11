package com.example.eatoo.src.review.create_review

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateReview2Binding

class CreateReview2Activity
    : BaseActivity<ActivityCreateReview2Binding>(ActivityCreateReview2Binding::inflate),
    View.OnClickListener {

    companion object {
        const val REQUEST_CODE_GALLERY = 15
    }

    private var curImgUri: Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivMenuNameDelete.setOnClickListener(this)
        binding.rlReviewImg.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_menu_name_delete -> binding.etMenuName.text.clear()
            R.id.rl_review_img -> loadGallery()
        }
    }

    private fun loadGallery() {
        //갤러리 권한?!
        getImage.launch("image/*")
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                curImgUri = it
                //파이어베이스 변환.
            }
        }
}