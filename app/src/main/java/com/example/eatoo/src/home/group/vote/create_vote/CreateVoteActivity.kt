package com.example.eatoo.src.home.group.vote.create_vote

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateVoteBinding
import com.example.eatoo.src.home.create_group.model.Keyword
import com.example.eatoo.src.home.group.vote.create_vote.model.CreateVoteRequest
import com.example.eatoo.util.*
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.math.roundToInt

class CreateVoteActivity
    : BaseActivity<ActivityCreateVoteBinding>(ActivityCreateVoteBinding::inflate),
    View.OnClickListener, CreateVoteView {

    companion object {
        const val YES = "Y"
        const val NO = "N"
    }

    private val voteItemList: MutableList<Keyword> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListeners()
        setCurrentTime()
    }

    private fun setCurrentTime() {

        val calendar = Calendar.getInstance()
        binding.tvDueDateDate.text = getCurrentDate()
        binding.tvDueDateTime.text = formatTo12HTimeStamp(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

    }

    private fun setOnClickListeners() {
        binding.customToggleVoteItem.setToggleClickListener()
        binding.customToggleMultiVote.setToggleClickListener()
        binding.customToggleAnonymousVote.setToggleClickListener()
        binding.btnRegisterVote.setOnClickListener(this)
        binding.ivVoteKeyword.setOnClickListener(this)
        binding.llVoteItemPlus.setOnClickListener(this)
        listenDueDateToggle()
        binding.tvDueDateDate.setOnClickListener(this)
        binding.tvDueDateTime.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register_vote -> checkValidity()
            R.id.iv_vote_keyword -> {
                binding.ivVoteKeyword.visibility = View.GONE
                binding.etVoteKeyword.visibility = View.VISIBLE
                initKeywordChips()
            }
            R.id.ll_vote_item_plus -> addVoteItemView()
            R.id.tv_due_date_date -> openCalendar()
            R.id.tv_due_date_time -> openClock()
        }
    }

    //due date 마감시간
    private fun openClock() {

        val calendar = Calendar.getInstance()

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText(resources.getString(R.string.vote_due_date))
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTheme(R.style.ThemeOverlay_App_TimePicker)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            binding.tvDueDateTime.text = formatTo12HTimeStamp(timePicker.hour, timePicker.minute)
            timePicker.dismiss()
        }
        timePicker.addOnCancelListener {
            timePicker.dismiss()
        }
        timePicker.show(supportFragmentManager, "vote_time")
    }

    private fun openCalendar() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText(resources.getString(R.string.vote_due_date))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            binding.tvDueDateDate.text = datePicker.headerText
            datePicker.dismiss()
        }
        datePicker.addOnCancelListener {
            datePicker.dismiss()
        }
        datePicker.show(supportFragmentManager, "vote_calendar")

    }

    private fun listenDueDateToggle() = with(binding) {
        rlVoteDueDate.setOnClickListener {
            toggleDueDate.isChecked = !toggleDueDate.isChecked
            if (toggleDueDate.isChecked) setToggleChecked()
            else setToggleUnChecked()
        }

        toggleDueDate.setOnClickListener {
            if (toggleDueDate.isChecked) setToggleChecked()
            else setToggleUnChecked()
        }
    }

    private fun setToggleChecked() = with(binding){
        tvDueDate.setTextColor(ContextCompat.getColor(this@CreateVoteActivity, R.color.black))
        tvDueDateDate.isVisible = true
        tvDueDateTime.isVisible = true
    }

    private fun setToggleUnChecked()= with(binding) {
        tvDueDate.setTextColor(ContextCompat.getColor(this@CreateVoteActivity, R.color.input_hint))
        tvDueDateDate.visibility = View.GONE
        tvDueDateTime.visibility = View.GONE
    }


    //vote keyword
    private fun initKeywordChips() {
        binding.etVoteKeyword.isFocusable = true
        binding.etVoteKeyword.isCursorVisible = true

        binding.etVoteKeyword.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val keywordList = binding.flexboxVoteKeyword.getAllChips()
                if (keywordList.size - 1 == 6) showCustomToast(resources.getString(R.string.keyword_num_limit))
                else {
                    val et = v as EditText
                    val keyword = et.text.toString()
                    if (keyword.length >= 11) showCustomToast(resources.getString(R.string.keyword_length_limit))
                    else {
                        binding.flexboxVoteKeyword.addChip(keyword)
                        et.text.clear()
                    }
                }
            }
            false
        }
    }

    @SuppressLint("InflateParams")
    private fun FlexboxLayout.addChip(keyword: String) {
        val chip = LayoutInflater.from(context).inflate(R.layout.view_chip, null) as Chip
        chip.text = keyword
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = dpToPx(4)
        chip.setOnCloseIconClickListener {
            removeView(chip as View)
        }
        addView(chip, childCount - 1, layoutParams)
    }

    fun Context.dpToPx(dp: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).roundToInt()

    private fun FlexboxLayout.getAllChips(): List<Keyword> {
        val keywordList: MutableList<Keyword> = mutableListOf()
        (0 until childCount - 1).mapNotNull { index ->
            val childChip = getChildAt(index) as? Chip
            keywordList.add(Keyword(name = childChip?.text.toString()))
        }
        return keywordList
    }


    //vote item 항목추가
    @SuppressLint("InflateParams")
    private fun addVoteItemView() {
        val mInflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dynamicVoteItem: View = mInflater.inflate(R.layout.view_vote_item, null)
        val insertPoint: LinearLayout = findViewById(R.id.ll_dynamic_vote_item_container)
        insertPoint.addView(dynamicVoteItem, insertPoint.childCount)
    }

    private fun getVoteItem() {
        binding.llDynamicVoteItemContainer.children.forEach {
            val child = it as LinearLayout
            val llChild = child.getChildAt(0) as EditText
            if (llChild.text.isNotEmpty()) voteItemList.add(Keyword(name = llChild.text.toString()))
        }
    }

    //register vote
    private fun checkValidity() {

        //투표명
        var voteName = ""
        if (binding.etVoteName.text.isEmpty()) {
            showCustomToast(resources.getString(R.string.input_vote_name_toast))
            return
        } else {
            voteName = binding.etVoteName.text.toString()
        }

        //투표 항목
        binding.etVoteItem1.text?.let {
            voteItemList.add(Keyword(it.toString()))
        }
        binding.etVoteItem2.text?.let {
            voteItemList.add(Keyword(it.toString()))
        }
        getVoteItem()
        if (voteItemList.isEmpty()) {
            showCustomToast(resources.getString(R.string.input_vote_item_toast))
            return
        }

        //마감시간
        val hasDueDate : String
        val timeLimit : String

        if(binding.toggleDueDate.isChecked) {
            hasDueDate = YES
            timeLimit = getDueDateTime()
        }else {
            hasDueDate = NO
            timeLimit = ""
        }

        //항목 추가
        val isAddible: String = if (binding.customToggleVoteItem.toggleBtn.isChecked) YES
        else NO

        //복수 투표
        val isMultiSelection: String = if (binding.customToggleMultiVote.toggleBtn.isChecked) YES
        else NO

        //익명 투표
        val isAnonymous: String = if (binding.customToggleAnonymousVote.toggleBtn.isChecked) YES
        else NO


        //post request
        val req = CreateVoteRequest(
            name = voteName,
            isAnonymous = isAnonymous,
            isAddible = isAddible,
            hasDuplicate = isMultiSelection,
            hasTimeLimit = hasDueDate,
            timeLimit = timeLimit,
            postKeywordReq = binding.flexboxVoteKeyword.getAllChips(),
            postMenuReq = voteItemList
        )
        postCreateVote(req)

    }

    private fun getDueDateTime() : String {
        val date = formatDateToTimeStamp(binding.tvDueDateDate.text.toString())
        val time = formatTo24HTimeStamp(binding.tvDueDateTime.text.toString())

        return "$date $time"
    }

    private fun postCreateVote(req: CreateVoteRequest) {
        showLoadingDialog(this)
        Log.d("createVoteActivity", "$req")
        CreateVoteService(this).tryPostVote(getUserIdx(), getGroupIdx(), req)
    }

    override fun onPostCreateVoteSuccess() {
        dismissLoadingDialog()
        finish() //vote fragment 로 복귀.
    }

    override fun onPostCreateVoteFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}