package com.example.eatoo.src.mypage.profile

import android.R
import android.os.Bundle
import android.view.View
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityProfileBinding
import com.google.android.material.chip.Chip


class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backBtn.setOnClickListener {
            finish()
        }

        val Usercolor = getUserColor()
        val UserCharacter = getUserCharacter()
        val Nickname = binding.nickNameEdt.text.toString()

    }

    private fun getUserColor(): Int {
        val checkedChip =
            binding.chipgroupMakegroup.findViewById<Chip>(binding.chipgroupMakegroup.checkedChipId).tag

        return checkedChip.toString().toInt()
    }

    private fun getUserCharacter(): Int {
        val checkedChip =
            binding.characterGroup.findViewById<Chip>(binding.characterGroup.checkedChipId).tag

        return checkedChip.toString().toInt()
    }
}