package com.makeus.eatoo.src.home.group.groupmatesuggestion

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.explanation.ExplanationActivity
import com.makeus.eatoo.src.home.group.groupmatesuggestion.dialog.LeaveMateSuggDialog
import com.makeus.eatoo.src.home.group.groupmatesuggestion.dialog.LeaveMateSuggDialogInterface
import com.makeus.eatoo.src.main.MainActivity
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.glideUtil


class MateSuggestionActivity
    : BaseActivity<ActivityMateSuggestionBinding>(ActivityMateSuggestionBinding::inflate)
    , MateSuggestionView, TimeDialogInterface, View.OnClickListener, LeaveMateSuggDialogInterface{


    private var groupIdx : Int = -1
    private var storeImg : Uri? = null
    private var Limit_people = arrayListOf<String>("")
    private var limit_headcount : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtra()

        loadGroup()

        setViewListeners()

        setSpinner()


    }

    private fun getIntentExtra() {
        val storeName = intent.getStringExtra("storeName")
        if(storeName != null) binding.storeEdt.setText(storeName)

        storeImg = intent.getStringExtra("storeImg")?.toUri()
        if(storeImg != null) {
            binding.ivMateImg.isVisible = true
            glideUtil(this, storeImg.toString(), binding.ivMateImg)
            binding.ivMateImg.setAlpha(400)
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
        binding.customToolbar.setLeftIconClickListener(this)
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
            R.id.iv_toolbar_left -> {
                val dialog = LeaveMateSuggDialog(this, this)
                dialog.show()
            }
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
            Log.d("matesugg", groupIdx.toString())
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
        if(binding.limitPeopleTv.text == resources.getString(R.string.main_mate_suggestion_limit_people_hint)){
            showCustomToast("제한 인원을 입력해주세요.")
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
                headCount =limit_headcount,
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
                headCount = limit_headcount,
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

    override fun onLeaveMateSuggClicked() {
        finish()
    }

    override fun onBackPressed() {
        val dialog = LeaveMateSuggDialog(this, this)
        dialog.show()
    }


    /*

    server result

     */
    override fun onGetGroupSuccess(response: GroupResponse) {
        response.result.forEachIndexed { index, it ->
            val chip  = LayoutInflater.from(this).inflate(R.layout.view_chip_2, null) as Chip
            chip.text = it.name
            if(getGroupIdx() == it.groupIdx){
                chip.isChecked = true
            }
            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(chip.isChecked) groupIdx = it.groupIdx
            }
            binding.chipgroupMateSugg.addView(chip)

            if(index == 0) groupIdx = it.groupIdx
        }
    }

    override fun onGetGroupFail(message: String) {
        showCustomToast(message)
    }


    override fun onPostMateCreateSuccess(response: CreateMateResponse) {
        dismissLoadingDialog()
        val dialog = MateSuggestionCompleteDialog(this)
        dialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            finish()
        }, 1300)

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

    fun setSpinner() {
        for(i in 1..99) {
            Limit_people.add((i+1).toString())
        }
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.limit_people_spinner_item,
            Limit_people
        )

        binding.limitPeopleSpinner.adapter = arrayAdapter
        binding.limitPeopleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {//스피너가 선택 되었을때
                if(i == 0){
                    binding.limitPeopleTv.setText(R.string.main_mate_suggestion_limit_people_hint)
                    binding.limitPeopleTv.setTextColor(binding.limitPeopleTv.context.resources.getColor(R.color.gray_100))
                }
                else {
                    binding.limitPeopleTv.text = (i + 1).toString() + '명'
                    limit_headcount = i+1
                    binding.limitPeopleTv.setTextColor(
                        binding.limitPeopleTv.context.resources.getColor(
                            R.color.black
                        )
                    )
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

    }


}