package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityFindMateBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.adpater.Find_Mate_Recyclerview_Adapter
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResponse
import com.makeus.eatoo.src.home.group.main.MateAttendDialog
import com.makeus.eatoo.src.home.group.main.MateAttendDialogInterface
import com.makeus.eatoo.src.home.group.main.MateAttendService
import com.makeus.eatoo.src.home.group.main.MateAttendView
import com.makeus.eatoo.src.home.group.main.model.MateAttendResponse
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName

class FindMateActivity : BaseActivity<ActivityFindMateBinding>(
    ActivityFindMateBinding::inflate), FindMateView, MateAttendDialogInterface, MateAttendView {

    var spinner_item = arrayOf("전체보기", "매칭중", "매칭완료")

    override fun onResume() {
        super.onResume()

        getAllMate()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("findmate", getGroupIdx().toString())
        Log.d("findmate", getUserIdx().toString())
        setSpinner()

        binding.suggestionTxt.text = "'" +getUserNickName() + "'" + binding.suggestionTxt.text


        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.fabGroupMate.setOnClickListener {
            startActivity(Intent(this, MateSuggestionActivity::class.java))
        }

    }

    private fun getAllMate() {
        showLoadingDialog(this)
        FindMateService(this).tryGetFindMateData(getUserIdx(), getGroupIdx(), 0)

    }

    override fun onMateAttendClicked(mateIdx: Int) {
        showLoadingDialog(this)
        MateAttendService(this).tryPostMateAttend(getUserIdx(),mateIdx)
    }

    override fun onGetFindMateSuccess(response: GroupMateResponse) {
        dismissLoadingDialog()
        Log.d("findmate", response.toString())

        if(response.code != 1000){
            binding.suggestionRecyclerview.visibility = View.GONE
            binding.mySuggestionNoneLayout.visibility = View.VISIBLE
        }
        else {
            val MateAdapter = Find_Mate_Recyclerview_Adapter(response.result)
            binding.statusSpinner.visibility = View.VISIBLE
            binding.suggestionRecyclerview.visibility = View.VISIBLE
            binding.mySuggestionNoneLayout.visibility = View.GONE
            binding.suggestionRecyclerview.adapter = MateAdapter
            binding.suggestionRecyclerview.layoutManager = LinearLayoutManager(this).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            MateAdapter.setItemClickListener(object :
                Find_Mate_Recyclerview_Adapter.ItemClickListener {
                override fun onClick(view: View, position: Int, mateIdx : Int, isAttended : String) {
                    if(isAttended == "N"){
                        val dialog = MateAttendDialog(this@FindMateActivity, mateIdx, this@FindMateActivity)
                        dialog.show()
                    }
                }
            })
            MateAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetFindMateFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
        binding.suggestionRecyclerview.visibility = View.GONE
        binding.mySuggestionNoneLayout.visibility = View.VISIBLE
        binding.statusSpinner.visibility = View.GONE

    }

    fun setSpinner() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.mate_status_spinner_item,
            spinner_item
        )

        binding.statusSpinner.setAdapter(arrayAdapter)
        binding.statusSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {//스피너가 선택 되었을때
                FindMateService(this@FindMateActivity).tryGetFindMateData(getUserIdx(), getGroupIdx(),i)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

    }

    override fun onGetMateAttendSuccess(response: MateAttendResponse) {
        dismissLoadingDialog()
        Log.d("메이트 참가 결과",""+response.message)
        getAllMate()
    }

    override fun onGetMateAttendFail(message: String?) {
        dismissLoadingDialog()
        Log.d("메이트 참가 결과",""+message)
    }
}