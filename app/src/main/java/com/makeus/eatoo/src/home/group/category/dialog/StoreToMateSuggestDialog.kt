package com.makeus.eatoo.src.home.group.category.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.databinding.DialogRegisterNewStoreBinding
import com.makeus.eatoo.databinding.DialogSuggestMateStoreBinding
import com.makeus.eatoo.src.review.store_map.StoreMapActivity

class StoreToMateSuggestDialog (
    context : Context,
    val listener : StoreToMateSuggestDialogInterface,
    val storeName : String,
    val storeImg : String) : Dialog(context) {

    private lateinit var binding: DialogSuggestMateStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogSuggestMateStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        binding.btnDialogConfirm.setOnClickListener {
            listener.onGotoMateSuggestClicked(storeName, storeImg)
            dismiss()
        }
        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }

    }
}