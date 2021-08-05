package com.makeus.eatoo.src.home.group.member.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.makeus.eatoo.databinding.DialogMemberDietKeywordBinding

class MemberDietKeywordDialog (
    context : Context
) : Dialog(context) {
    private lateinit var binding: DialogMemberDietKeywordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogMemberDietKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}