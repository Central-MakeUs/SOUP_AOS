package com.example.eatoo.src.home.group.groupmatesuggestion

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityGroupMateSuggetsionBinding
import com.example.eatoo.src.home.GroupService
import com.example.eatoo.src.home.GroupView
import com.example.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.example.eatoo.src.home.model.GroupResponse
import com.example.eatoo.util.getUserIdx
import com.google.android.material.chip.Chip
import kotlin.math.min


class Group_Mate_Suggetsion_Activity
    : BaseActivity<ActivityGroupMateSuggetsionBinding>(ActivityGroupMateSuggetsionBinding::inflate)
    ,GroupView,Mate_Suggestion_ActivityView, TimeDialogInterface{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        GroupService(this).tryGetData(getUserIdx())

        binding.gourpChipGroup.isSingleSelection = true
        binding.gourpChipGroup.isSelectionRequired = true




        binding.registerMateBtn.setOnClickListener {
           // val postRequest = CreateMateRequest(name = binding.suggestionNameEdt.text.toString(), storeName = binding.storeEdt.text.toString(), startTime = ,endTime = ,headCount = ,timeLimit = ,imgUrl = )
            //Log.d("요청사항", ""+ postRequest)
            //MateCreateService(this).postCreateMate(postRequest)
        }

        binding.statrTimeBtn.setOnClickListener{
            val dialog = TimeDialog(this, this)
            dialog.show()
        }

    }




    override fun onGetGroupSuccess(response: GroupResponse) {
        showCustomToast("요청 완료")

        val GroupList = response.result.getGroupsRes
        val GroupListtSize = GroupList.size - 1
        for(i in 0..GroupListtSize){
            var chip = Chip(this) // Must contain context in parameter
            chip.text =  GroupList[i].name
            Log.d("그룹 이름들", GroupList[i].name)
            chip.isCheckable = true
            chip.isCheckedIconVisible = false
            chip.setTextAppearanceResource(R.style.Chip_no_select_Style)
            chip.setBackgroundResource(R.drawable.chip_box_mate_group_suggestion) //Custom 할 예정 하는 법을 모르겠네여,,,ㅎㅎ
            chip.setChipBackgroundColorResource(R.color.transparent)
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if(chip.isChecked) {
                    chip.setTextAppearanceResource(R.style.Chip_select_Style)
                }
                else{
                    chip.setTextAppearanceResource(R.style.Chip_no_select_Style)
                }
            }

            binding.gourpChipGroup.addView(chip)
        }
    }

    override fun onGetGroupFail(message: String) {
    }

    override fun onPostMateCreateSuccess(response: CreateMateResponse) {

    }

    override fun onPostMateCreateFailure(message: String) {

    }

    override fun onSetTime(hour: String, minute: String) {
        showCustomToast("$hour $minute")
    }
}