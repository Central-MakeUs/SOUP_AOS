package com.makeus.eatoo.src.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogDeleteGroupBinding
import com.makeus.eatoo.util.getGroupName

class GroupDeleteDialog(
    context: Context,
    val listener : GroupDeleteDialogInterface,
    val groupIdx : Int,
    val groupName : String
) : Dialog(context) {

    private lateinit var binding: DialogDeleteGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogDeleteGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvQuery1.text = String.format(ApplicationClass.applicationResources.getString(R.string.delete_group), groupName)

        binding.btnDialogConfirm.setOnClickListener {
            listener.onGroupDeleteClicked(groupIdx)
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}