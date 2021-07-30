package com.makeus.eatoo.src.home.group.groupmatesuggestion

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityMateSuggestionBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.util.getUserIdx
import com.google.android.material.chip.Chip
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeus.eatoo.util.glideUtil


class MateSuggestionActivity
    : BaseActivity<ActivityMateSuggestionBinding>(ActivityMateSuggestionBinding::inflate)
    , MateSuggestionView, TimeDialogInterface, View.OnClickListener{


    private var groupIdx : Int = -1
    private var storeImg : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtra()

        loadGroup()

        setViewListeners()


    }

    private fun getIntentExtra() {
        val storeName = intent.getStringExtra("storeName")
        if(storeName != null) binding.storeEdt.setText(storeName)

        storeImg = intent.getStringExtra("storeImg")?.toUri()
        if(storeImg != null) {
            binding.ivMateImg.isVisible = true
            glideUtil(this, storeImg.toString(), binding.ivMateImg)
            binding.llImgHint.isVisible = false
            if(storeName != null)  {
                binding.tvMateStoreNameOnImg.text = storeName
                binding.tvMateStoreNameOnImg.isVisible = true
            }
        }
    }

    private fun setViewListeners() {
        binding.startTimeLayout.setOnClickListener(this)
        binding.endTimeLayout.setOnClickListener(this)
        binding.limitTimeLayout.setOnClickListener(this)
        binding.registerMateBtn.setOnClickListener(this)
        binding.llImgHint.setOnClickListener(this)
    }

    private fun loadGroup() {
        MateSuggestionService(this).tryGetGroupData(getUserIdx())
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.start_time_layout -> {
                val dialog = TimeDialog(this, this,"start")
                dialog.show()
            }
            R.id.end_time_layout -> {
                val dialog = TimeDialog(this, this,"end")
                dialog.show()
            }
            R.id.limit_time_layout -> {
                val dialog = TimeDialog(this, this,"limit")
                dialog.show()
            }
            R.id.register_mate_btn -> checkValidation()
            R.id.ll_img_hint -> loadGallery()
        }
    }

    private fun loadGallery() {
        getImage.launch("image/*")
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                storeImg = it
                glideUtil(this, it.toString(), binding.ivMateImg)
                binding.ivMateImg.isVisible = true
                binding.llImgHint.isVisible = false
            }
        }

    private fun checkValidation() {
        if(groupIdx == -1) {
            showCustomToast("그룹을 선택해주세요.")
            return
        }
        if(binding.suggestionNameEdt.text.isEmpty()){
            showCustomToast("제안명을 입력해주세요.")
            return
        }
        if(binding.storeEdt.text.isEmpty()){
            showCustomToast("가게명을 입력해주세요.")
            return
        }
        if(binding.startTimeBtn.text.isEmpty()){
            showCustomToast("식사 시작 시간을 입력해주세요.")
            return
        }
        if(binding.endTimeBtn.text.isEmpty()){
            showCustomToast("식사 끝 시간을 입력해주세요.")
            return
        }
        if(binding.limitTimeTv.text.isEmpty()){
            showCustomToast("마감 시간을 입력해주세요.")
            return
        }

        if(storeImg != null) {
            loadFirebaseStorage(storeImg!!)
        }else {
            val mateReq = CreateMateRequest(
                groupIdx = groupIdx,
                name = binding.suggestionNameEdt.text.toString(),
                storeName = binding.storeEdt.text.toString(),
                startTime = binding.startTimeBtn.text.toString(),
                endTime = binding.endTimeBtn.text.toString(),
                headCount = binding.limitPeopleEdt.text.toString().toInt(),
                timeLimit = binding.limitTimeTv.text.toString(),
                imgUrl = ""
            )
            showLoadingDialog(this)
            requestMateSugg(mateReq)
        }



    }
    private fun loadFirebaseStorage(firebaseUri : Uri) {
        showLoadingDialog(this)
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val ref = storageRef.child("${getUserIdx()}/mate/${System.currentTimeMillis()}.png")
        ref.putFile(firebaseUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                dismissLoadingDialog()
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener {

            val mateReq = CreateMateRequest(
                groupIdx = groupIdx,
                name = binding.suggestionNameEdt.text.toString(),
                storeName = binding.storeEdt.text.toString(),
                startTime = binding.startTimeBtn.text.toString(),
                endTime = binding.endTimeBtn.text.toString(),
                headCount = binding.limitPeopleEdt.text.toString().toInt(),
                timeLimit = binding.limitTimeTv.text.toString(),
                imgUrl = it.result.toString()
            )

            requestMateSugg(mateReq)

        }
    }

    private fun requestMateSugg(mateReq: CreateMateRequest) {
        Log.d("mateSugg", mateReq.toString())
        MateSuggestionService(this).tryPostMate(mateReq, getUserIdx())
    }


    override fun onGetGroupSuccess(response: GroupResponse) {
        response.result.forEach {
            val chip  = LayoutInflater.from(this).inflate(R.layout.view_chip_2, null) as Chip
            chip.text = it.name
            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(chip.isChecked) groupIdx = it.groupIdx
            }
            binding.chipgroupMateSugg.addView(chip)
        }
    }

    override fun onGetGroupFail(message: String) {
        showCustomToast(message)
    }


    //그룹 생성하기
    override fun onPostMateCreateSuccess(response: CreateMateResponse) {
        dismissLoadingDialog()
        val dialog = MateSuggestionCompleteDialog(this)
        dialog.show()
        finish()

    }

    override fun onPostMateCreateFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }


    @SuppressLint("SetTextI18n")
    override fun onSetStartTime(hour: String, minute: String) {
        binding.startTimeBtn.text = "$hour:$minute"
    }

    @SuppressLint("SetTextI18n")
    override fun onSetEndTime(hour: String, minute: String) {
        binding.endTimeBtn.text = "$hour:$minute"
    }

    @SuppressLint("SetTextI18n")
    override fun onSetLimitTime(hour: String, minute: String) {
        binding.limitTimeTv.text = "$hour:$minute"
    }


}