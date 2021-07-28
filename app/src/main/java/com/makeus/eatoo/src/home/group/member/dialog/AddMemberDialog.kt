package com.makeus.eatoo.src.home.group.member.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogAddMemberBinding
import com.makeus.eatoo.util.getGroupName

class AddMemberDialog (
    context: Context,
    val listener : AddMemberDialogInterface
    ) : Dialog(context) {
    private lateinit var binding: DialogAddMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvQuery1.text = String.format(ApplicationClass.applicationResources.getString(R.string.new_member1), getGroupName())

        binding.btnDialogAgree.setOnClickListener {
            listener.onDialogAddMemberClicked()
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }

}