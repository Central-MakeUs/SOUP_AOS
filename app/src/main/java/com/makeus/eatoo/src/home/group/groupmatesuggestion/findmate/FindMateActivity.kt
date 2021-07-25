package com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityFindMateBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.adpater.Find_Mate_Recyclerview_Adapter
import com.makeus.eatoo.src.home.group.groupmatesuggestion.findmate.model.GroupMateResponse
import com.makeus.eatoo.src.home.group.main.MateAttendDialog
import com.makeus.eatoo.src.home.group.main.adapter.Group_Home_Main_Mate_Kind_RecyclerviewAdapter
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName

class FindMateActivity : BaseActivity<ActivityFindMateBinding>(
    ActivityFindMateBinding::inflate), FindMateView {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.suggestionTxt.text = "'" +getUserNickName() + "'" + binding.suggestionTxt.text
        FindMateService(this).tryGetFindMateData(getUserIdx())
        showLoadingDialog(this)
    }

    override fun onGetFindMateSuccess(response: GroupMateResponse) {
        dismissLoadingDialog()


        val MateAdapter = Find_Mate_Recyclerview_Adapter(response.result)

        if(response.result.size == 0){
            binding.suggestionRecyclerview.visibility = View.GONE
            binding.mySuggestionNoneLayout.visibility = View.VISIBLE
        }
        else {
            binding.suggestionRecyclerview.visibility = View.VISIBLE
            binding.mySuggestionNoneLayout.visibility = View.GONE
            binding.suggestionRecyclerview.adapter = MateAdapter
            binding.suggestionRecyclerview.layoutManager = LinearLayoutManager(this).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            MateAdapter.setItemClickListener(object :
                Find_Mate_Recyclerview_Adapter.ItemClickListener {
                override fun onClick(view: View, position: Int, mateIdx : Int) {
                    val dialog = MateAttendDialog(this@FindMateActivity, mateIdx)
                    dialog.show()
                }
            })
        }
    }

    override fun onGetFindMateFail(message: String?) {
        TODO("Not yet implemented")
    }
}