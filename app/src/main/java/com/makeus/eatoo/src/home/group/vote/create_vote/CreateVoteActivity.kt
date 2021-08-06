package com.makeus.eatoo.src.home.group.vote.create_vote

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityCreateVoteBinding
import com.makeus.eatoo.src.home.create_group.model.Keyword
import com.makeus.eatoo.src.home.group.vote.create_vote.model.CreateVoteRequest
import com.makeus.eatoo.util.*
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
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
        binding.customToolbar.leftIcon.setOnClickListener { finish() }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register_vote -> checkValidity()
            R.id.iv_vote_keyword -> {
                binding.ivVoteKeyword.visibility = View.GONE
                binding.etVoteKeyword.visibility = View.VISIBLE
                initKeywordChips()
            }
            R.id.ll_vote_item_plus -> addVoteItemView()
            R.id.tv_due_date_date -> {
                val datePicker = DatePickerDialog(this,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.datePicker.minDate = cal.timeInMillis
                datePicker.show()
            }
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
            if(!isDueDateValid( binding.tvDueDateDate.text.toString(), formatTo12HTimeStamp(timePicker.hour, timePicker.minute))){
                showCustomToast("마감시간은 현재시간 이후 이어야 합니다.")
            }
            else {
                binding.tvDueDateTime.text = formatTo12HTimeStamp(timePicker.hour, timePicker.minute)
                timePicker.dismiss()
            }

        }
        timePicker.addOnCancelListener {
            timePicker.dismiss()
        }
        timePicker.show(supportFragmentManager, "vote_time")
    }


    val cal: Calendar = Calendar.getInstance()
    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            binding.tvDueDateDate.text = "${year}년 ${monthOfYear+1}월 ${dayOfMonth}일"
            if(!isDueDateValid("${year}년 ${monthOfYear+1}월 ${dayOfMonth}일", binding.tvDueDateTime.text.toString())) {
                showCustomToast("마감시간은 현재시간 이후 이어야 합니다.")
            }
        }
    }

    fun isDueDateValid(userSetDate : String, userSetTime : String) : Boolean {
        val cal = Calendar.getInstance().time
        val timeStampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

        val parsedDate = timeStampFormat.format(cal)
        return timeStampFormat.parse(getDueDateTime(userSetDate, userSetTime)).after(timeStampFormat.parse(parsedDate))
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

        setKeywordEt()

        binding.etVoteKeyword.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val keywordList = binding.flexboxVoteKeyword.getAllChips()
                if (keywordList.size - 1 == 4) showCustomToast(resources.getString(R.string.keyword_num_limit))
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

    private fun setKeywordEt() {
        binding.etVoteKeyword.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etVoteKeyword, InputMethodManager.SHOW_IMPLICIT)
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
            if(!isDueDateValid(binding.tvDueDateDate.text.toString(), binding.tvDueDateTime.text.toString())) {
                showCustomToast("마감시간은 현재시간 이후 이어야 합니다.")
                return
            }else {
                hasDueDate = YES
                timeLimit = getDueDateTime(binding.tvDueDateDate.text.toString(), binding.tvDueDateTime.text.toString())
            }

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

    private fun getDueDateTime(userSetDate : String, userSetTime : String) : String {
        val date = formatDateToTimeStamp(userSetDate)
        val time = formatTo24HTimeStamp(userSetTime)

        return "$date $time"
    }

    private fun postCreateVote(req: CreateVoteRequest) {
        showLoadingDialog(this)
        Log.d("createVoteActivity", "$req ${getGroupIdx()}")
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