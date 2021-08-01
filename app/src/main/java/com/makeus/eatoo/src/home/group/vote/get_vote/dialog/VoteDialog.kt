package com.makeus.eatoo.src.home.group.vote.get_vote.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogVoteBinding
import com.makeus.eatoo.src.home.group.vote.GroupVoteFragment
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VoteDetailItem
import com.makeus.eatoo.src.home.group.vote.get_vote.model.VoteDetailResult

class VoteDialog(
    val view: GroupVoteFragment
) : Dialog(view.requireContext()), View.OnClickListener {

    lateinit var voteDetail: VoteDetailResult
    private lateinit var binding: DialogVoteBinding
    var votedItemList = ArrayList<Int>()
    private var toggleChecked = 0
    private val mInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
        } else inflateMultiVoteView()

    }


    private fun setOnClickListeners() {
        binding.btnVoteDialogDone.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_vote_dialog_done -> {
                if (voteDetail.hasDuplicate == "N") getVotedItem()
                else getMultiVotedItem()
            }
        }
    }

    /////공통 메서드
    private fun setVoteConfirmToRetry() {
        if (voteDetail.isVoted == "Y") {
            binding.btnVoteDialogDone.text =
                ApplicationClass.applicationResources.getString(R.string.dialog_vote_retry)
            binding.btnVoteDialogDone.isSelected = true
        }
    }

    private fun setUnItemChecked(voteItem: TextView?, votedNum: TextView?) {
        voteItem?.background =
            ContextCompat.getDrawable(context, R.drawable.background_vote_dialog_item)
        votedNum?.setTextColor(ContextCompat.getColor(context, R.color.dialog_cancel_text))
        voteItem?.setTextColor(ContextCompat.getColor(context, R.color.black))

    }

    private fun setItemChecked(voteItem: TextView?, votedNum: TextView?) {
        voteItem?.background =
            ContextCompat.getDrawable(context, R.drawable.background_vote_dialog_item_orange)
        votedNum?.setTextColor(ContextCompat.getColor(context, R.color.main_orange))
        voteItem?.setTextColor(ContextCompat.getColor(context, R.color.black))
    }


    /***
     *
     * 복수투표
     *
     ***/


    @SuppressLint("InflateParams")
    private fun inflateMultiVoteView() {
        val insertPoint: LinearLayout = findViewById(R.id.ll_multi_vote_container)

        voteDetail.getMenuRes.forEachIndexed { index, voteDetailItem ->
            val dynamicVoteView: View =
                mInflater.inflate(R.layout.view_multi_vote_dialog_item, null)
            dynamicVoteView.id = index
            val voteItem = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_item)
            voteItem.text = voteDetailItem.name
            val votedNum = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_num)
            votedNum.text = String.format(
                ApplicationClass.applicationResources.getString(R.string.dialog_vote_num),
                voteDetailItem.votedNumber
            )
            val voteToggle =
                dynamicVoteView.findViewById<AppCompatToggleButton>(R.id.toggle_vote_dialog)
            voteToggle.setOnClickListener {
                if (voteToggle.isChecked) {
                    toggleChecked++
                    setItemChecked(voteItem, votedNum)
                } else {
                    toggleChecked--
                    setUnItemChecked(voteItem, votedNum)
                }
                binding.btnVoteDialogDone.isSelected = toggleChecked > 0
            }
            if (voteDetailItem.isSelected == "Y") {
                toggleChecked++
                voteToggle.isChecked = true
                voteItem.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_vote_dialog_item_filled_orange
                )
                votedNum.setTextColor(ContextCompat.getColor(context, R.color.white))
                voteItem.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                voteToggle.isChecked = false
                voteItem.background =
                    ContextCompat.getDrawable(context, R.drawable.background_vote_dialog_item)
                votedNum.setTextColor(ContextCompat.getColor(context, R.color.dialog_cancel_text))
                voteItem.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            insertPoint.addView(dynamicVoteView, insertPoint.childCount)
        }

        //////복수투표 항목추가 버튼
        if (voteDetail.isAddible == "Y") {
            val dynamicAddItemView: View =
                mInflater.inflate(R.layout.view_vote_dialog_add_item, null)

            dynamicAddItemView.setOnClickListener {
                addVoteItemView(insertPoint)
            }
            insertPoint.addView(dynamicAddItemView, insertPoint.childCount)
        }

        ////재투표인 경우 버튼 변경
        setVoteConfirmToRetry()

        /////summary

        setMutiSummary(insertPoint)


    }

    private fun setMutiSummary(insertPoint: LinearLayout) {
        val dynamicVoteSummary: View =
            mInflater.inflate(R.layout.view_vote_dialog_summary, null)
        val voteDate = dynamicVoteSummary.findViewById<TextView>(R.id.tv_vote_dialog_date)
        voteDate.text = voteDetail.endTime

        if (voteDetail.isAnonymous == "N") {
            val anonymousVote = dynamicVoteSummary.findViewById<TextView>(R.id.tv_dialog_anonymous)
            anonymousVote.visibility = View.GONE
        }
        insertPoint.addView(dynamicVoteSummary, insertPoint.childCount)

    }

    //////복수투표 항목입력 뷰 추가
    @SuppressLint("InflateParams")
    private fun addVoteItemView(insertPoint: LinearLayout) {
        val dynamicVoteView: View = mInflater.inflate(R.layout.view_multi_vote_add_item, null)
        dynamicVoteView.id = binding.llMultiVoteContainer.childCount - 1
        val addVoteItem = dynamicVoteView.findViewById<EditText>(R.id.et_vote_dialog_item)
        addVoteItem.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                //항목추가 api
                view.onVoteItemAdded(voteDetail.voteIdx, addVoteItem.text.toString())
            }
            false
        }


        val votedNum = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_num)
        votedNum.text = String.format(
            ApplicationClass.applicationResources.getString(R.string.dialog_vote_num), 0
        )

        val voteToggle =
            dynamicVoteView.findViewById<AppCompatToggleButton>(R.id.toggle_vote_dialog)
        voteToggle.setOnClickListener {
            if (voteToggle.isChecked) {
                setItemChecked(addVoteItem, votedNum)
            } else {
                setUnItemChecked(addVoteItem, votedNum)
            }
        }

        insertPoint.addView(dynamicVoteView, insertPoint.childCount - 1)

    }

    ////복수투표 하기.
    private fun getMultiVotedItem() {
        if (binding.btnVoteDialogDone.isSelected) {
            binding.llMultiVoteContainer.children.forEachIndexed { index, view ->
                if(index != binding.llMultiVoteContainer.childCount -1 ){
                    val llChild = view as ConstraintLayout
                    val clChild = llChild.getChildAt(0) as AppCompatToggleButton
                    if (clChild.isChecked) votedItemList.add(view.id)
                }
            }
            view.onVoteFinishClick(voteDetail, votedItemList)
            dismiss()
            votedItemList.clear()
        }
    }


    /****
     *
     *복수 투표 X
     *
     ****/
    private fun inflateNonMultiVoteView() {

        val insertPointItem: LinearLayout = findViewById(R.id.ll_nonmuti_vote_item_container)
        val insertPointRadio: RadioGroup = findViewById(R.id.radiogroup_vote_dialog)

        voteDetail.getMenuRes.forEachIndexed { index, voteDetailItem ->
            //vote item
            val dynamicVoteItemView: View =
                mInflater.inflate(R.layout.view_nonmuti_vote_dialog_item, null)
            dynamicVoteItemView.id = index
            val voteItem = dynamicVoteItemView.findViewById<TextView>(R.id.tv_vote_dialog_item)
            voteItem.text = voteDetailItem.name
            val votedNum = dynamicVoteItemView.findViewById<TextView>(R.id.tv_vote_dialog_num)
            votedNum.text = String.format(
                ApplicationClass.applicationResources.getString(R.string.dialog_vote_num),
                voteDetailItem.votedNumber
            )

            val radioBtn = setRadioButton(index, voteDetailItem)

            if (voteDetailItem.isSelected == "Y") {
                radioBtn.isChecked = true
                voteItem.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.background_vote_dialog_item_filled_orange
                )
                votedNum.setTextColor(ContextCompat.getColor(context, R.color.white))
                voteItem.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                radioBtn.isChecked = false
                voteItem.background =
                    ContextCompat.getDrawable(context, R.drawable.background_vote_dialog_item)
                votedNum.setTextColor(ContextCompat.getColor(context, R.color.dialog_cancel_text))
                voteItem.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
            insertPointItem.addView(dynamicVoteItemView, insertPointItem.childCount)

            //radio btn
            insertPointRadio.addView(radioBtn)
        }

        binding.radiogroupVoteDialog.setOnCheckedChangeListener { radioGroup, i ->
            binding.llNonmutiVoteItemContainer.children.forEachIndexed { index, it ->
                if (index != binding.llNonmutiVoteItemContainer.childCount - 1) {
                    val llChild = it as ConstraintLayout
                    val voteItem = llChild.getChildAt(0) as? TextView
                    val votedNum = llChild.getChildAt(1) as? TextView
                    if (voteItem != null && votedNum != null) {
                        if (it.id == i) setItemChecked(voteItem, votedNum)
                        else setUnItemChecked(voteItem, votedNum)
                    }
                }


            }
        }

        /////복수 투표 x 항목추가 버튼
        if (voteDetail.isAddible == "Y") {
            val dynamicAddItemView: View =
                mInflater.inflate(R.layout.view_vote_dialog_nonmulti_add_item, null)

            dynamicAddItemView.setOnClickListener {
                addNonMultiVoteItemView(insertPointItem, insertPointRadio)
            }
            insertPointItem.addView(dynamicAddItemView, insertPointItem.childCount)
        }

        /////복수 투표 x 재투표 버튼
        setVoteConfirmToRetry()

        setNonMutiSummary(insertPointItem)

    }

    private fun setNonMutiSummary(insertPointItem: LinearLayout) {
        val dynamicVoteSummary: View =
            mInflater.inflate(R.layout.view_non_multivote_dialog_summary, null)
        val voteDate = dynamicVoteSummary.findViewById<TextView>(R.id.tv_vote_dialog_date)
        voteDate.text = voteDetail.endTime

        if (voteDetail.isAnonymous == "N") {
            val anonymousVote = dynamicVoteSummary.findViewById<TextView>(R.id.tv_dialog_anonymous)
            anonymousVote.visibility = View.GONE
        }
        insertPointItem.addView(dynamicVoteSummary, insertPointItem.childCount)

    }

    /////복수 투표 x 항목입력 추가
    @SuppressLint("InflateParams")
    private fun addNonMultiVoteItemView(
        insertPointItem: LinearLayout,
        insertPointRadio: RadioGroup
    ) {
        val dynamicVoteView: View = mInflater.inflate(R.layout.view_nonmulti_vote_add_item, null)
        dynamicVoteView.id = binding.llNonmutiVoteItemContainer.childCount - 1
        val addVoteItem = dynamicVoteView.findViewById<EditText>(R.id.et_vote_dialog_item)
        addVoteItem.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                //항목추가 api
                view.onVoteItemAdded(voteDetail.voteIdx, addVoteItem.text.toString())
            }
            false
        }
        val votedNum = dynamicVoteView.findViewById<TextView>(R.id.tv_vote_dialog_num)
        votedNum.text = String.format(
            ApplicationClass.applicationResources.getString(R.string.dialog_vote_num), 0
        )

        insertPointItem.addView(dynamicVoteView, insertPointItem.childCount - 1)

        insertPointRadio.addView(setRadioButton(binding.radiogroupVoteDialog.childCount, null))

        binding.radiogroupVoteDialog.setOnCheckedChangeListener { radioGroup, i ->
            binding.llNonmutiVoteItemContainer.children.forEachIndexed { index, it ->
                if (index != binding.llNonmutiVoteItemContainer.childCount - 1) {
                    val llChild = it as ConstraintLayout
                    val voteItemEt = llChild.getChildAt(0) as? EditText
                    val voteItemTv = llChild.getChildAt(0) as? TextView
                    val dynamicVotedNum = llChild.getChildAt(1) as TextView
                    if (voteItemEt != null) {
                        if (it.id == i) setItemChecked(voteItemEt, dynamicVotedNum)
                        else setUnItemChecked(voteItemEt, dynamicVotedNum)
                    } else if (voteItemTv != null) {
                        if (it.id == i) setItemChecked(voteItemTv, dynamicVotedNum)
                        else setUnItemChecked(voteItemTv, dynamicVotedNum)
                    }
                }
            }
        }

    }

    private fun setRadioButton(
        orderIdx: Int,
        voteDetailItem: VoteDetailItem?
    ): RadioButton {
        val radioBtn = RadioButton(context)
        radioBtn.setButtonDrawable(R.drawable.selector_vote_dialog_toggle)
        radioBtn.background = ColorDrawable(Color.TRANSPARENT)
        radioBtn.id = orderIdx
        radioBtn.setOnClickListener {
            binding.btnVoteDialogDone.isSelected =
                binding.radiogroupVoteDialog.checkedRadioButtonId != -1
        }

        val params = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.topMargin =
            ApplicationClass.applicationResources.getDimension(R.dimen.vote_dialog_radio_top_margin)
                .toInt()
        params.bottomMargin =
            ApplicationClass.applicationResources.getDimension(R.dimen.vote_dialog_radio_bottom_margin)
                .toInt()
        radioBtn.layoutParams = params

        radioBtn.isChecked = voteDetailItem?.isSelected == "Y"


        return radioBtn
    }

    private fun getVotedItem() {
        if (binding.btnVoteDialogDone.isSelected) {
            votedItemList.add(binding.radiogroupVoteDialog.checkedRadioButtonId)

            view.onVoteFinishClick(voteDetail, votedItemList)
            dismiss()
            votedItemList.clear()
        }

    }


}