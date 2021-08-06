package com.makeus.eatoo.src.mypage.invite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityInviteBinding
import com.makeus.eatoo.src.mypage.invite.adapter.Invite_Group_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.makeus.eatoo.src.mypage.invite.model.InviteResponse
import com.makeus.eatoo.util.getUserIdx
import java.lang.Math.round
import kotlin.math.round


class InviteActivity :  BaseActivity<ActivityInviteBinding>(ActivityInviteBinding::inflate), InviteActivityView {

    lateinit var GroupName : String
    var GroupIdx : Int = 0
    var Click_status = 0
    var Position = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InviteService(this).tryGetInviteGroupData(getUserIdx())
    }

    override fun onGetInviteGroupDateSuccess(response: InviteResponse) {
        if(response.code == 1000){

            if(response.result.size == 0){
                binding.myGroupRecyclerview.visibility = View.GONE
                binding.mySuggestionNoneLayout.visibility = View.VISIBLE
                binding.inviteBtn.visibility = View.GONE
            }
            else{
                GroupIdx = response.result[0].groupIdx
                GroupName = response.result[0].name
                Click_status = 1
                binding.myGroupRecyclerview.visibility = View.VISIBLE
                binding.inviteBtn.visibility = View.VISIBLE
                binding.mySuggestionNoneLayout.visibility = View.GONE

                val GroupAdapter = Invite_Group_Kind_RecyclerviewAdapter(response.result)
                binding.myGroupRecyclerview.adapter = GroupAdapter
                binding.myGroupRecyclerview.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
//                GroupAdapter.setItemClickListener(object :
//                    Invite_Group_Kind_RecyclerviewAdapter.ItemClickListener {
//                    override fun onClick(view: View, position: Int, groupIdx : Int ,groupname : String) {
//                        GroupIdx = groupIdx
//                        GroupName = groupname
//                        Click_status = 1
//                    }
//                })
                binding.recyclerViewIndicator.setRecyclerView(binding.myGroupRecyclerview)
                //binding.recyclerViewIndicator.forceUpdateItemCount()

                binding.myGroupRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        super.onScrollStateChanged(binding.myGroupRecyclerview, newState)
                        when (newState) {
                            RecyclerView.SCROLL_STATE_IDLE -> {
                                Position = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstCompletelyVisibleItemPosition()
                                Log.d("Position","" + Position )
                                if(Position == -1){
                                    Click_status = 0
                                }else{
                                    Click_status = 1
                                }
                                binding.recyclerViewIndicator.setCurrentPosition(Position)
                            }
                        }
                    }
                })
            }


            binding.inviteBtn.setOnClickListener {
                if(Click_status == 1){
                    GroupIdx = response.result[Position].groupIdx
                    GroupName = response.result[Position].name
                    ApplicationClass.sSharedPreferences.edit()
                        .putString(ApplicationClass.GROUP_NAME,  GroupName).apply()
                    Click_status = 1
                    InviteService(this).tryGetInviteCodeData(getUserIdx(),GroupIdx)
                }
                else{
                    showCustomToast("그룹을 선택해 주세요")
                }
            }
            binding.backBtn.setOnClickListener {
                finish()
            }

        }

    }

    override fun onGetInviteGroupDateFail(message: String) {
    }

    override fun onGetInviteCodeDateSuccess(response: InviteCodeResponse) {
        val dialog = InviteDialog(this,response.result.code)
        dialog.show()
    }

    override fun onGetInviteCodeDateFail(message: String) {
    }
}