package com.example.eatoo.src.home.group.vote.get_vote.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.databinding.DialogVoteBinding
import com.example.eatoo.src.home.create_group.model.Keyword
import com.example.eatoo.src.home.group.vote.GroupVoteFragment
import com.example.eatoo.src.home.group.vote.get_vote.model.VoteDetailResult
import org.w3c.dom.Text

class VoteDialog(
    val view: GroupVoteFragment,
    private val voteDetail: VoteDetailResult
) : Dialog(view.requireContext()), View.OnClickListener {

    private lateinit var binding: DialogVoteBinding
    var votedItemList = ArrayList<Int>()
    private var toggleChecked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setView()
        setOnClickListeners()
//        setCanceledOnTouchOutside(false)
//        setCancelable(false)


    }

    private fun setView() {
        binding.tvGroupVoteName.text = voteDetail.name
        binding.tvVoteDialogTopDueDate.text = voteDetail.endDate
        if (voteDetail.hasDuplicate == "N") {
            inflateNonMultiVoteView()
        } else  inflateMultiVoteView()

    }


    private fun setOnClickListeners() {
        binding.btnVoteDialogDone.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_vote_dialog_done -> {
                if(voteDetail.hasDuplicate == "N") getVotedItem()
                else getMultiVotedItem()
            }
        }
    }




    /////////복수투표

    @SuppressLint("InflateParams")
    private fun inflateMultiVoteView() {
        val mInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val insertPoint: LinearLayout = findViewById(R.id.ll_multi_vote_container)

        voteDetail.getMenuRes.forEachIndexed { index, voteDetailItem ->
            val dynamicVoteView: View = mInflater.inflate(R.layout.view_multi_vote_dialog_item, null)
            dynamicVoteView.id = index
            val voteItem = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_item)
            voteItem.text = voteDetailItem.name
            val votedNum = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_num)
            votedNum.text = String.format(
                ApplicationClass.applicationResources.getString(R.string.dialog_vote_num), voteDetailItem.votedNumber)
            val voteToggle = dynamicVoteView.findViewById<AppCompatToggleButton>(R.id.toggle_vote_dialog)
            voteToggle.setOnClickListener {
                if(voteToggle.isChecked)  toggleChecked++
                else toggleChecked--
               binding.btnVoteDialogDone.isSelected = toggleChecked >0
            }

            insertPoint.addView(dynamicVoteView, insertPoint.childCount)
        }

    }

    private fun getMultiVotedItem() {
        if( binding.btnVoteDialogDone.isSelected) {
            binding.llMultiVoteContainer.children.forEach {
                val llChild = it as ConstraintLayout
                val clChild = llChild.getChildAt(0) as AppCompatToggleButton
                if(clChild.isChecked) votedItemList.add(it.id)
            }
            view.onVoteFinishClick(voteDetail, votedItemList)
            dismiss()
        }
    }



    /////////복수 투표 X
    private fun inflateNonMultiVoteView() {
        val mInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val insertPointItem: LinearLayout = findViewById(R.id.ll_nonmuti_vote_item_container)
        val insertPointRadio: RadioGroup = findViewById(R.id.radiogroup_vote_dialog)

        voteDetail.getMenuRes.forEachIndexed { index, voteDetailItem ->
            //vote item
            val dynamicVoteItemView: View = mInflater.inflate(R.layout.view_nonmuti_vote_dialog_item, null)
            val voteItem = dynamicVoteItemView.findViewById<TextView>(R.id.tv_vote_dialog_item)
            voteItem.text = voteDetailItem.name
            val votedNum = dynamicVoteItemView.findViewById<TextView>(R.id.tv_vote_dialog_num)
            votedNum.text = String.format(
                ApplicationClass.applicationResources.getString(R.string.dialog_vote_num), voteDetailItem.votedNumber)
            insertPointItem.addView(dynamicVoteItemView, insertPointItem.childCount)

            //radio btn
            insertPointRadio.addView( setRadioButton(index))
        }

    }

    private fun setRadioButton(orderIdx : Int) : RadioButton {
        val radioBtn = RadioButton(context)
        radioBtn.setButtonDrawable(R.drawable.selector_vote_dialog_toggle)
        radioBtn.background = ColorDrawable(Color.TRANSPARENT)
        radioBtn.id = orderIdx
        radioBtn.setOnClickListener{
            binding.btnVoteDialogDone.isSelected = binding.radiogroupVoteDialog.checkedRadioButtonId != -1
        }

        val params = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = ApplicationClass.applicationResources.getDimension(R.dimen.vote_dialog_radio_top_margin).toInt()
        params.bottomMargin = ApplicationClass.applicationResources.getDimension(R.dimen.vote_dialog_radio_bottom_margin).toInt()
        radioBtn.layoutParams = params

        return radioBtn
    }

    private fun getVotedItem() {
       if( binding.btnVoteDialogDone.isSelected){
           votedItemList.add(binding.radiogroupVoteDialog.checkedRadioButtonId)

           view.onVoteFinishClick(voteDetail, votedItemList)
           dismiss()
       }

    }


}