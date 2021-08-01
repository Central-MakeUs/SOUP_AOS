package com.makeus.eatoo.src.home.group.groupmatesuggestion.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogAccountWithdrawBinding
import com.makeus.eatoo.databinding.DialogLeaveMateSuggBinding
import com.makeus.eatoo.util.getGroupName

class LeaveMateSuggDialog (
    context: Context,
    val listener : LeaveMateSuggDialogInterface
) : Dialog(context) {
    private lateinit var binding: DialogLeaveMateSuggBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogLeaveMateSuggBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnDialogConfirm.setOnClickListener {
            listener.onLeaveMateSuggClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}