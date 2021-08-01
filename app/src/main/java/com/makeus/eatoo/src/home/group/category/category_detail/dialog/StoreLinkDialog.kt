package com.makeus.eatoo.src.home.group.category.category_detail.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogLeaveGroupActivityBinding
import com.makeus.eatoo.databinding.DialogStoreLinkBinding

class StoreLinkDialog (
    context: Context,
    val listener : StoreLinkDialogInterface
) : Dialog(context) {

    private lateinit var binding: DialogStoreLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogStoreLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnDialogConfirm.setOnClickListener {
            listener.goToStoreLinkClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}