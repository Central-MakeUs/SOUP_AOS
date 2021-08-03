package com.makeus.eatoo.src.mypage.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogAccountWithdrawBinding
import com.makeus.eatoo.util.getGroupName

class AccountWithdrawDialog (
    context: Context,
    val listener : AccountWithdrawalDialogInterface
) : Dialog(context) {
    private lateinit var binding: DialogAccountWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAccountWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.btnDialogConfirm.setOnClickListener {
            listener.onWithdrawClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}