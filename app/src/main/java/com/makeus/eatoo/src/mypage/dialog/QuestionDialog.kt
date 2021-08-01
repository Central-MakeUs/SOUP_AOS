package com.makeus.eatoo.src.mypage.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogGoogleStoreBinding
import com.makeus.eatoo.databinding.DialogLeaveGroupActivityBinding
import com.makeus.eatoo.databinding.DialogStoreLinkBinding

class QuestionDialog (
    context: Context,
    val listener : QuestionDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogGoogleStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogGoogleStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvGotoGooglestore.setOnClickListener {
            listener.onGoToGoogleStoreClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}