package com.makeus.eatoo.src.home.notification.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.ItemNotificationBinding
import com.makeus.eatoo.src.home.notification.model.GetMemberRe
import com.makeus.eatoo.src.home.notification.model.NotificationResult
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserNickName

class NotificationRVAdapter(
    val context: Context,
    val notiList : List<NotificationResult>
) : RecyclerView.Adapter<NotificationRVAdapter.ViewHolder>(){

    private val mInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    inner class ViewHolder(val binding : ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item : NotificationResult){
            binding.tvNotiDate.text = item.createdAt
            binding.ivNew.isVisible = item.isChecked == "N"
            when(item.noticeStatus){
                0 -> bindSignInNoti()
                1 -> reviewNoti()
                2 -> mateNoti(item)
            }
        }

        @SuppressLint("InflateParams")
        private fun mateNoti(item: NotificationResult) {
            binding.clMateNoti.isVisible = true
            binding.clNonMateNoti.isVisible = false
            binding.ivNonMateNoti.isVisible = false
            binding.tvNotiName.text = ApplicationClass.applicationResources.getString(R.string.mate_noti_title)
            binding.tvMateName.text = item.mateName
            binding.tvMateStoreName.text = item.storeName
            binding.tvMateTime.text = item.time

            val insertPoint: LinearLayout = binding.llMateMemberContainer

            item.getMemberRes.forEach {
                val memberView: View = mInflater.inflate(R.layout.view_notification_member, null)
                val memberContainer = memberView.findViewById<ImageView>(R.id.iv_member_container)
                val memberChar = memberView.findViewById<ImageView>(R.id.iv_member)
                val memberNickname = memberView.findViewById<TextView>(R.id.tv_member_nickname)
                setMemberChar(memberContainer, memberChar, it)
                memberNickname.text = it.nickName

                insertPoint.addView(memberView, insertPoint.childCount)
            }
        }

        private fun setMemberChar(
            memberContainer: ImageView?,
            member: ImageView?,
            it: GetMemberRe
        ) {
            val memberColor = if(it.color != 0) it.color -1 else 0
            val memberChar = if(it.characters != 0) it.characters -1 else 0

            member?.setImageResource(EatooCharList[(memberColor*5) + memberChar])

            if(it.singleStatus == "ON"){
                memberContainer?.setImageResource(R.drawable.background_member_gray)
                val grayScale = floatArrayOf(0.2989f, 0.5870f, 0.1140f,
                    0F, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.2989f, 0.5870f, 0.1140f, 0f, 0f, 0.0000F, 0.0000F, 0.0000F, 1f, 0f)
                val matrix = ColorMatrixColorFilter(grayScale)
                member?.colorFilter= matrix
            }else {
                memberContainer?.setImageResource(R.drawable.background_member_orange)
                member?.colorFilter = null
            }
        }

        private fun reviewNoti() {
            binding.clMateNoti.isVisible = false
            binding.clNonMateNoti.isVisible = true
            binding.ivNonMateNoti.isVisible = true
            binding.tvNotiName.text = ApplicationClass.applicationResources.getString(R.string.review_noti_title)
            binding.tvNoti1.text = ApplicationClass.applicationResources.getString(R.string.review_noti_mid1)
            binding.tvNoti2.text = ApplicationClass.applicationResources.getString(R.string.review_noti_mid2)
            binding.tvNotiExp1.text = ApplicationClass.applicationResources.getString(R.string.review_noti_exp1)
            binding.tvNotiExp2.text = ApplicationClass.applicationResources.getString(R.string.review_noti_exp2)
            binding.ivNonMateNoti.setImageResource(R.drawable.ic_review_create)
        }

        private fun bindSignInNoti() {
            binding.clMateNoti.isVisible = false
            binding.clNonMateNoti.isVisible = true
            binding.ivNonMateNoti.isVisible = true
            binding.tvNotiName.text = ApplicationClass.applicationResources.getString(R.string.signup_noti_title)
            binding.tvNoti1.text = String.format(
                ApplicationClass.applicationResources.getString(R.string.signup_noti_mid1), getUserNickName())
            binding.tvNoti2.text = ApplicationClass.applicationResources.getString(R.string.signup_noti_mid2)
            binding.tvNotiExp1.text = ApplicationClass.applicationResources.getString(R.string.signup_noti_exp1)
            binding.tvNotiExp2.text = ApplicationClass.applicationResources.getString(R.string.signup_noti_exp2)
            binding.ivNonMateNoti.setImageResource(R.drawable.ic_main_group_icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(notiList[position])
    }

    override fun getItemCount(): Int = notiList.size
}