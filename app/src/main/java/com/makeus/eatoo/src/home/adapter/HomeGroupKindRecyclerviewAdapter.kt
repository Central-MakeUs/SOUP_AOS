package com.makeus.eatoo.src.home.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.ColorMatrixColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.src.home.model.GroupResultResponse
import com.google.android.material.chip.Chip
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.src.home.model.GroupMembers
import com.makeus.eatoo.util.EatooCharList

class HomeGroupKindRecyclerviewAdapter(
    val GroupList: ArrayList<GroupResultResponse>,
    var groupsize: Int,
    var group_status: String,
    val listener : OnGroupLongClick
) : RecyclerView.Adapter<HomeGroupKindRecyclerviewAdapter.CustomViewholder>() {

    val colorArray = ApplicationClass.applicationResources.getIntArray(R.array.groupRVColor)

    interface OnGroupLongClick{
        fun onGroupLongClicked(groupIdx: Int, groupName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        var inflaterview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_has_group, parent, false)
        return CustomViewholder(inflaterview)
    }


    override fun getItemCount() = GroupList.size + 1

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        Log.d("position", "" + position)

        if (position == GroupList.size - 1) {

            val ivMemberList =
                listOf<AppCompatImageView>(holder.GroupMember1, holder.GroupMember2, holder.GroupMember3)
            val ivMemberContainerList =
                listOf<ImageView>(holder.member1Container, holder.member2Container, holder.member3Container)
            setMemberChar(GroupList[position].getGroupMembersRes, ivMemberList, ivMemberContainerList)

            holder.GroupLayout.setOnLongClickListener {
                listener.onGroupLongClicked(GroupList[position].groupIdx, GroupList[position].name)
                true
            }

            holder.GroupLayout.backgroundTintList = ColorStateList.valueOf(colorArray[GroupList[position].color-1])




            holder.LastLayout.visibility = GONE
            group_status = "LAST"
            holder.GroupName.text = GroupList[position].name
            if (GroupList[position].membersNumber > 3) {
                holder.GroupMemberCount.text =
                    "+" + (GroupList[position].membersNumber - 3).toString()
            } else if (GroupList[position].membersNumber == 1) {
                holder.member2Container.visibility = View.INVISIBLE
                holder.member3Container.visibility = View.INVISIBLE
                holder.GroupMember2.visibility = View.INVISIBLE
                holder.GroupMember3.visibility = View.INVISIBLE
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            } else if (GroupList[position].membersNumber == 2) {
                holder.GroupMember3.visibility = View.INVISIBLE
                holder.member3Container.visibility = View.INVISIBLE
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            } else if (GroupList[position].membersNumber == 3) {
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            }

        } else if (position == GroupList.size) {
            holder.GroupLayout.visibility = GONE
            holder.LastLayout.visibility = VISIBLE
        } else {

            val ivMemberList =
                listOf<AppCompatImageView>(holder.GroupMember1, holder.GroupMember2, holder.GroupMember3)
            val ivMemberContainerList =
                listOf<ImageView>(holder.member1Container, holder.member2Container, holder.member3Container)
            setMemberChar(GroupList[position].getGroupMembersRes, ivMemberList, ivMemberContainerList)

            holder.GroupLayout.setOnLongClickListener {
                listener.onGroupLongClicked(GroupList[position].groupIdx, GroupList[position].name)
                true
            }
            holder.GroupLayout.backgroundTintList = ColorStateList.valueOf(colorArray[GroupList[position].color-1])

            holder.LastLayout.visibility = GONE
            holder.GroupName.text = GroupList[position].name
            if (GroupList[position].membersNumber > 3) {
                holder.GroupMemberCount.text =
                    "+" + (GroupList[position].membersNumber - 3).toString()
            } else if (GroupList[position].membersNumber == 1) {
                holder.member2Container.visibility = View.INVISIBLE
                holder.member3Container.visibility = View.INVISIBLE
                holder.GroupMember2.visibility = View.INVISIBLE
                holder.GroupMember3.visibility = View.INVISIBLE
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            } else if (GroupList[position].membersNumber == 2) {
                holder.member3Container.visibility = View.INVISIBLE
                holder.GroupMember3.visibility = View.INVISIBLE
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            } else if (GroupList[position].membersNumber == 3) {
                holder.GroupMemberCount.visibility = View.GONE
                holder.GroupMemberCountBackground.visibility = View.GONE
            }
        }
        Log.d("GroupList.size", "" + GroupList.size)
        Log.d("groupsize", "" + groupsize)

        holder.GroupLayout.setOnClickListener {
            itemClickListener.onClick(
                it,
                position,
                GroupList[position].groupIdx,
                GroupList[position].name,
                "Group_activity"
            )
        }

        holder.GroupPlus.setOnClickListener {
            itemClickListener.onClick(it, position, 0, "no", "plus")
        }


        if (position <= GroupList.size - 1) {
            Log.d("키워드 개수", "" + GroupList[position].getGroupKeywordRes.size)

            if (GroupList[position].getGroupKeywordRes.size != 0) {

                holder.GroupNameLayouttext1.visibility = GONE
                holder.GroupNameLayouttext2.visibility = GONE

                if (GroupList[position].getGroupKeywordRes.size == 1) {
                    holder.GroupKeywordChip1.visibility = VISIBLE
                    holder.GroupKeywordChip2.visibility = GONE
                    holder.GroupKeyword2.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name

                } else if (GroupList[position].getGroupKeywordRes.size == 2) {
                    holder.GroupKeywordChip1.visibility = VISIBLE
                    holder.GroupKeywordChip2.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
                } else if (GroupList[position].getGroupKeywordRes.size == 3) {
                    holder.GroupKeywordChip1.visibility = VISIBLE
                    holder.GroupKeywordChip2.visibility = VISIBLE
                    holder.GroupKeyword4.visibility = GONE
                    //holder.GroupKeyword5.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
                    holder.GroupKeyword3.text = GroupList[position].getGroupKeywordRes[2].name
                } else if (GroupList[position].getGroupKeywordRes.size == 4 || GroupList[position].getGroupKeywordRes.size == 5) {
                    holder.GroupKeywordChip1.visibility = VISIBLE
                    holder.GroupKeywordChip2.visibility = VISIBLE
                    //holder.GroupKeyword5.visibility = GONE
                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
                    holder.GroupKeyword3.text = GroupList[position].getGroupKeywordRes[2].name
                    holder.GroupKeyword4.text = GroupList[position].getGroupKeywordRes[3].name
                }
//                else if (GroupList[position].getGroupKeywordRes.size == 5) {
//                    holder.GroupKeywordChip1.visibility = VISIBLE
//                    holder.GroupKeywordChip2.visibility = VISIBLE
//                    holder.GroupKeyword1.text = GroupList[position].getGroupKeywordRes[0].name
//                    holder.GroupKeyword2.text = GroupList[position].getGroupKeywordRes[1].name
//                    holder.GroupKeyword3.text = GroupList[position].getGroupKeywordRes[2].name
//                    holder.GroupKeyword4.text = GroupList[position].getGroupKeywordRes[3].name
//                    holder.GroupKeyword5.text = GroupList[position].getGroupKeywordRes[4].name
//                }

            } else {
                holder.GroupNameLayouttext1.visibility = VISIBLE
                holder.GroupNameLayouttext1.text =
                    GroupList[position].name + holder.GroupNameLayouttext1.text
                holder.GroupNameLayouttext2.visibility = VISIBLE
                holder.GroupKeywordChip1.visibility = GONE
                holder.GroupKeywordChip2.visibility = GONE
            }
        }


    }

    private fun setMemberChar(memberList: ArrayList<GroupMembers>, ivMemberList: List<AppCompatImageView>, ivMemberContainerList: List<ImageView>) {

        var memberColor: Int
        var memberChar: Int
        memberList.forEachIndexed { index, groupMembers ->

            if(index <3) {
                memberColor = if (groupMembers.color != 0) groupMembers.color - 1 else 0
                memberChar = if (groupMembers.characters != 0) groupMembers.characters - 1 else 0
                ivMemberList[index].setImageResource(EatooCharList[(memberColor * 5) + memberChar])

                if (groupMembers.singleStatus == "ON") {
                    val grayScale = floatArrayOf(
                        0.2989f, 0.5870f, 0.1140f, 0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f,
                        0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f
                    )
                    val matrix = ColorMatrixColorFilter(grayScale)
                    ivMemberList[index].colorFilter = matrix
                    ivMemberContainerList[index].setImageResource(R.drawable.background_member_gray)
                }else  {
                    ivMemberList[index].colorFilter = null
                    ivMemberContainerList[index].setImageResource(R.drawable.background_member_orange)
                }

            }

        }
    }


    class CustomViewholder(val view: View) : RecyclerView.ViewHolder(view) {
        val GroupLayout: CardView = view.findViewById(R.id.group_layout)
        val LastLayout: ConstraintLayout = view.findViewById(R.id.last_layout)
        val GroupName: AppCompatTextView = view.findViewById(R.id.group_name_tv)
        val GroupPlus: AppCompatImageButton = view.findViewById(R.id.group_plus_btn)
        val GroupMemberCount: AppCompatTextView = view.findViewById(R.id.group_member_count_tv)
        val GroupMemberCountBackground: AppCompatImageView =
            view.findViewById(R.id.group_member_count_tv_background)
        val GroupMember1: AppCompatImageView = view.findViewById(R.id.group_member1)
        val GroupMember2: AppCompatImageView = view.findViewById(R.id.group_member2)
        val GroupMember3: AppCompatImageView = view.findViewById(R.id.group_member3)
        val member1Container : ImageView = view.findViewById(R.id.iv_member1_container)
        val member2Container : ImageView = view.findViewById(R.id.iv_member2_container)
        val member3Container : ImageView = view.findViewById(R.id.iv_member3_container)
        val GroupNameLayouttext1: AppCompatTextView = view.findViewById(R.id.keword_recomand1)
        val GroupNameLayouttext2: AppCompatTextView = view.findViewById(R.id.keword_recomand2)
        val GroupKeywordChip1: ConstraintLayout = view.findViewById(R.id.chip_first)
        val GroupKeywordChip2 : ConstraintLayout = view.findViewById(R.id.chip_second)
        val GroupKeyword1: AppCompatTextView = view.findViewById(R.id.chip1)
        val GroupKeyword2: AppCompatTextView = view.findViewById(R.id.chip2)
        val GroupKeyword3 : AppCompatTextView = view.findViewById(R.id.chip2_1)
        val GroupKeyword4 : AppCompatTextView = view.findViewById(R.id.chip2_2)
        //val GroupKeyword5 : AppCompatTextView = view.findViewById(R.id.chip2_3)

    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, groupIdx: Int, groupname: String, state: String)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


}