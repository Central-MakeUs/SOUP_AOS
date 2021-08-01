package com.makeus.eatoo.src.suggestion

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.R
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.databinding.DialogSuggestDeleteBinding
import com.makeus.eatoo.util.getUserNickName

class MySuggestDeleteDialog (
    context: Context,
    val listener : MySuggestDeleteDialogInterface,
    val mateIdx : Int,
    private val mateTitle : String
) : Dialog(context) {

    private lateinit var binding: DialogSuggestDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogSuggestDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvQuery1.text = String.format(
            ApplicationClass.applicationResources.getString(R.string.delete_suggestion_query1),
            mateTitle
        )
        binding.tvExp1.text = String.format(
            ApplicationClass.applicationResources.getString(R.string.delete_suggestion_exp1),
            getUserNickName()
        )

        binding.btnDialogConfirm.setOnClickListener {
            listener.onMySuggestConfirm(mateIdx)
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }
}