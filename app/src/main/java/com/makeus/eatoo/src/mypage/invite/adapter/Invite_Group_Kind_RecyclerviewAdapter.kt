package com.makeus.eatoo.src.mypage.invite.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.mypage.invite.model.InviteGroupResult
import com.google.android.material.chip.Chip

class Invite_Group_Kind_RecyclerviewAdapter(val GroupList : ArrayList<InviteGroupResult>) : RecyclerView.Adapter<Invite_Group_Kind_RecyclerviewAdapter.CustomViewholder>(){

    val colorArray = ApplicationClass.applicationResources.getIntArray(R.array.groupRVColor)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val inflaterview = LayoutInflater.from(parent.context).inflate(R.layout.item_invit_group_recyclerview,parent,false)

        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = GroupList.size

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewholder, position : Int) {

        holder.GroupName.text = GroupList[position].name



        if (GroupList[position].membersNumber > 3) {
            holder.GroupMemberCount.text =
                "+" + (GroupList[position].membersNumber - 3).toString()
        }
        else if(GroupList[position].membersNumber == 1){
            holder.GroupMember2.visibility = View.GONE
            holder.GroupMember3.visibility = View.GONE
            holder.GroupMemberCount.visibility = View.GONE
            holder.GroupMemberCountBackground.visibility = View.GONE
        }
        else if(GroupList[position].membersNumber == 2){
            holder.GroupMember3.visibility = View.GONE
            holder.GroupMemberCount.visibility = View.GONE
            holder.GroupMemberCountBackground.visibility = View.GONE
        }
        else if(GroupList[position].membersNumber == 3){
            holder.GroupMemberCount.visibility = View.GONE
            holder.GroupMemberCountBackground.visibility = View.GONE
        }

        if(position <= GroupList.size - 1) {
            Log.d("키워드 개수", "" + GroupList[position].getGroupKeywordRes.size)

            if (GroupList[position].getGroupKeywordRes.size != 0) {

                holder.GroupNameLayouttext1.visibility = View.GONE
                holder.GroupNameLayouttext2.visibility = View.GONE

                if (GroupList[position].getGroupKeywordRes.size == 1) {
                    holder.GroupKeywordChip1.visibility = View.VISIBLE
//                    holder.GroupKeywordChip2.visibility = GONE
                    holder.GroupKeyword2.visibility = View.GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name

                } else if (GroupList[position].getGroupKeywordRes.size == 2) {
                    holder.GroupKeywordChip1.visibility = View.VISIBLE
//                    holder.GroupKeywordChip2.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
                } else if (GroupList[position].getGroupKeywordRes.size == 3) {
                    holder.GroupKeywordChip1.visibility = View.VISIBLE
//                    holder.GroupKeywordChip2.visibility = VISIBLE
//                    holder.GroupKeyword4.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
//                    holder.GroupKeyword3.text = GroupList[position].getGroupKeywordRes[2].name
                } else if (GroupList[position].getGroupKeywordRes.size == 4 || GroupList[position].getGroupKeywordRes.size == 5) {
                    holder.GroupKeywordChip1.visibility = View.VISIBLE
//                    holder.GroupKeywordChip2.visibility = VISIBLE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
//                    holder.GroupKeyword3.text = GroupList[position].getGroupKeywordRes[2].name
//                    holder.GroupKeyword4.text = GroupList[position].getGroupKeywordRes[3].name
                }

            } else {
                holder.GroupNameLayouttext1.visibility = View.VISIBLE
                holder.GroupNameLayouttext1.text = GroupList[position].name + holder.GroupNameLayouttext1.text
                holder.GroupNameLayouttext2.visibility = View.VISIBLE
                holder.GroupKeywordChip1.visibility = View.GONE
//                holder.GroupKeywordChip2.visibility = GONE
            }
        }


        if (GroupList[position].color == 1){
            holder.GroupLayout.setBackgroundTintList(
                ColorStateList.valueOf(colorArray[0]))
            holder.GroupKeywordChip1.setBackgroundTintList(
                ColorStateList.valueOf(colorArray[0]))
        }
        else if(GroupList[position].color == 2){
            holder.GroupLayout.setBackgroundTintList(
                ColorStateList.valueOf(colorArray[1]))
            holder.GroupKeywordChip1.setBackgroundTintList(
                ColorStateList.valueOf(colorArray[1]))
        }
        else if(GroupList[position].color == 3){
            holder.GroupLayout.backgroundTintList = ColorStateList.valueOf(colorArray[2])
            holder.GroupKeywordChip1.backgroundTintList = ColorStateList.valueOf(colorArray[2])
        }
        else if(GroupList[position].color == 4){
            holder.GroupLayout.backgroundTintList = ColorStateList.valueOf(colorArray[3])
            holder.GroupKeywordChip1.backgroundTintList = ColorStateList.valueOf(colorArray[3])
        }
        else if(GroupList[position].color == 5){
            holder.GroupLayout.backgroundTintList = ColorStateList.valueOf(colorArray[4])
            holder.GroupKeywordChip1.backgroundTintList = ColorStateList.valueOf(colorArray[4])

        }

    }


    class CustomViewholder(val view : View) : RecyclerView.ViewHolder(view){
        val GroupLayout : ConstraintLayout = view.findViewById(R.id.group_layout)
        val GroupName : AppCompatTextView = view.findViewById(R.id.group_name_tv)
        val GroupMemberCount : AppCompatTextView = view.findViewById(R.id.group_member_count_tv)
        val GroupMemberCountBackground : AppCompatImageView = view.findViewById(R.id.group_member_count_tv_background)
        val GroupMember1 : AppCompatImageView = view.findViewById(R.id.group_member1)
        val GroupMember2 : AppCompatImageView = view.findViewById(R.id.group_member2)
        val GroupMember3 : AppCompatImageView = view.findViewById(R.id.group_member3)
        val GroupNameLayouttext1 : AppCompatTextView = view.findViewById(R.id.keword_recomand1)
        val GroupNameLayouttext2 : AppCompatTextView = view.findViewById(R.id.keword_recomand2)
        val GroupKeywordChip1 : LinearLayout = view.findViewById(R.id.chip_first)
        //        val GroupKeywordChip2 : LinearLayout = view.findViewById(R.id.chip_second)
        val GroupKeyword1 : Chip = view.findViewById(R.id.chip1)
        val GroupKeyword2 : Chip = view.findViewById(R.id.chip2)
//        val GroupKeyword3 : Chip = view.findViewById(R.id.chip2_1)
//        val GroupKeyword4 : Chip = view.findViewById(R.id.chip2_2)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int , groupIdx : Int, groupname : String)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}