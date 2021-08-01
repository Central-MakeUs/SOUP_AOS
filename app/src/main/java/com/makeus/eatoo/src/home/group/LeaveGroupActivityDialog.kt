package com.makeus.eatoo.src.home.group

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogLeaveGroupActivityBinding

class LeaveGroupActivityDialog (
    context: Context,
    val listener : LeaveGroupActivityInterface,
    private val groupName : String
) : Dialog(context) {

    private lateinit var binding: DialogLeaveGroupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogLeaveGroupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvQuery1.text = String.format(ApplicationClass.applicationResources.getString(R.string.dialog_leave_group_activity_query1), groupName)

        binding.btnDialogConfirm.setOnClickListener {
            listener.onLeaveGroupClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}