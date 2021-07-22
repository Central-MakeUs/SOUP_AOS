package com.makeus.eatoo.src.review.store_map.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.databinding.DialogRegisterNewStoreBinding
import com.makeus.eatoo.src.review.store_map.StoreMapActivity

class RegisterNewStoreDialog(val view : StoreMapActivity) : Dialog(view)  {

    private lateinit var binding : DialogRegisterNewStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogRegisterNewStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        binding.btnDialogConfirm.setOnClickListener {
            view.onRegisterNewStoreConfirm()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }

    }
}