package com.makeus.eatoo.src.home.group.groupmatesuggestion

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityGroupMateSuggetsionBinding
import com.makeus.eatoo.src.home.GroupService
import com.makeus.eatoo.src.home.GroupView
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateRequest
import com.makeus.eatoo.src.home.group.groupmatesuggestion.model.CreateMateResponse
import com.makeus.eatoo.src.home.model.GroupResponse
import com.makeus.eatoo.src.home.model.MainCharResponse
import com.makeus.eatoo.src.home.model.MateResponse
import com.makeus.eatoo.util.getUserIdx
import com.google.android.material.chip.Chip
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.FindMateService
import com.makeus.eatoo.util.getGroupIdx


class Group_Mate_Suggetsion_Activity : BaseActivity<ActivityGroupMateSuggetsionBinding>(ActivityGroupMateSuggetsionBinding::inflate), Mate_Suggestion_ActivityView, TimeDialogInterface{


    private var GroupIndex : Int = 0
    private var Limit_people = arrayListOf<String>("")
    private var limit_headcount : Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        MateCreateService(this).tryGetGroupData(getUserIdx())

        binding.gourpChipGroup.isSingleSelection = true
        binding.gourpChipGroup.isSelectionRequired = true

        setSpinner()



        binding.registerMateBtn.setOnClickListener {
            val postRequest = CreateMateRequest(groupIdx = GroupIndex,name = binding.suggestionNameEdt.text.toString(), storeName = binding.storeEdt.text.toString(), startTime =  binding.startTimeBtn.text.toString() ,endTime =  binding.startTimeBtn.text.toString() ,headCount = limit_headcount,timeLimit = binding.limitTimeTv.text.toString() ,imgUrl = "" )
            Log.d("요청사항", ""+ postRequest)
            MateCreateService(this).postCreateMate(postRequest,getUserIdx())
            showLoadingDialog(this)
        }

        binding.startTimeLayout.setOnClickListener{
            val dialog = TimeDialog(this, this,"start")
            dialog.show()
        }

        binding.endTimeLayout.setOnClickListener {
            val dialog = TimeDialog(this, this,"end")
            dialog.show()
        }

        binding.endTimeLayout.setOnClickListener{
            val dialog = TimeDialog(this, this,"end")
            dialog.show()
        }
        binding.limitTimeLayout.setOnClickListener {
            val dialog = TimeDialog(this, this,"limit")
            dialog.show()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }




    override fun onGetGroupSuccess(response: GroupResponse) {

        val GroupList = response.result
        val GroupListtSize = GroupList.size - 1
        for(i in 0..GroupListtSize){
            val chip  = LayoutInflater.from(this).inflate(R.layout.view_chip_2, null) as Chip
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
                    var GroupName = chip.text.toString()
                    var count : Int = 0
                    //groupidx 부분 수정해야해요
                    for(j in 0..GroupListtSize){
                        if(GroupList[j].name == GroupName){
                            GroupIndex = GroupList[j].groupIdx
                            count++
                            Log.d("그룹 인덱스",""+GroupIndex)
                        }

                    }
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

//Mate 보이기

    //그룹 생성하기
    override fun onPostMateCreateSuccess(response: CreateMateResponse) {
        dismissLoadingDialog()
        val dialog = Mate_completeDialog(this)
        dialog.show()
        finish()

    }

    override fun onPostMateCreateFailure(message: String) {

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