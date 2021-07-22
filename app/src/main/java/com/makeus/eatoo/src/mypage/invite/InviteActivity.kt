package com.makeus.eatoo.src.mypage.invite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityInviteBinding
import com.makeus.eatoo.src.mypage.invite.adapter.Invite_Group_Kind_RecyclerviewAdapter
import com.makeus.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.makeus.eatoo.src.mypage.invite.model.InviteResponse
import com.makeus.eatoo.util.getUserIdx


class InviteActivity :  BaseActivity<ActivityInviteBinding>(ActivityInviteBinding::inflate), InviteActivityView {

    lateinit var GroupName : String
    var GroupIdx : Int = 0
    var Click_status = 0


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
                binding.myGroupRecyclerview.visibility = View.VISIBLE
                binding.inviteBtn.visibility = View.VISIBLE
                binding.mySuggestionNoneLayout.visibility = View.GONE
                val GroupAdapter = Invite_Group_Kind_RecyclerviewAdapter(response.result)
                binding.myGroupRecyclerview.adapter = GroupAdapter
                binding.myGroupRecyclerview.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
//            binding.indicator.attachTo(binding.myGroupRecyclerview, true)
//            GroupAdapter.setItemClickListener(object :
//                Invite_Group_Kind_RecyclerviewAdapter.ItemClickListener {
//                override fun onClick(view: View, position: Int, groupIdx : Int , groupname : String) {
//                    GroupIdx = groupIdx
//                    GroupName = groupname
//                }
//            })
                GroupAdapter.setItemClickListener(object :
                    Invite_Group_Kind_RecyclerviewAdapter.ItemClickListener {
                    override fun onClick(view: View, position: Int, groupIdx : Int ,groupname : String) {
                        GroupIdx = groupIdx
                        GroupName = groupname
                        Click_status = 1
                    }
                })
            }


            binding.inviteBtn.setOnClickListener {
                if(Click_status == 1){
                    InviteService(this).tryGetInviteCodeData(getUserIdx(),GroupIdx)
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
        val dialog = InviteDialog(this,response.result.code,GroupName)
        dialog.show()
    }

    override fun onGetInviteCodeDateFail(message: String) {
    }
}