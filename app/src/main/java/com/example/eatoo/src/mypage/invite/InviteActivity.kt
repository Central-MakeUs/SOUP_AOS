package com.example.eatoo.src.mypage.invite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityInviteBinding
import com.example.eatoo.src.mypage.invite.adapter.Invite_Group_Kind_RecyclerviewAdapter
import com.example.eatoo.src.mypage.invite.model.InviteCodeResponse
import com.example.eatoo.src.mypage.invite.model.InviteResponse
import com.example.eatoo.util.getUserIdx


class InviteActivity :  BaseActivity<ActivityInviteBinding>(ActivityInviteBinding::inflate), InviteActivityView {

    lateinit var GroupName : String
    var GroupIdx : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InviteService(this).tryGetInviteGroupData(getUserIdx())
    }

    override fun onGetInviteGroupDateSuccess(response: InviteResponse) {
        if(response.code == 1000){

            val GroupAdapter = Invite_Group_Kind_RecyclerviewAdapter(response.result)
            binding.myGroupRecyclerview.adapter = GroupAdapter
            binding.myGroupRecyclerview.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
            binding.indicator.attachTo(binding.myGroupRecyclerview, true)
            GroupAdapter.setItemClickListener(object :
                Invite_Group_Kind_RecyclerviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int, groupIdx : Int , groupname : String) {
                    GroupIdx = groupIdx
                    GroupName = groupname
                }
            })

            binding.inviteBtn.setOnClickListener {
                InviteService(this).tryGetInviteCodeData(getUserIdx(),GroupIdx)

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