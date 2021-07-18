package com.example.eatoo.src.mypage.profile

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.children
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityProfileBinding
import com.example.eatoo.src.mypage.profile.model.PatchProfileRequest
import com.example.eatoo.src.mypage.profile.model.PatchProfileResponse
import com.example.eatoo.src.mypage.profile.model.ProfileResponse
import com.example.eatoo.src.mypage.profile.model.ProfileResult
import com.example.eatoo.util.EatooCharList
import com.example.eatoo.util.getUserIdx
import com.google.android.material.chip.Chip


class ProfileActivity
    : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate),
    View.OnClickListener, ProfileView {

    lateinit var currentNickname : String
    private var currentColor  = 0
    private var currentCharacter  = 0


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getProfile()
        setOnClickListeners()
    }

    private fun getProfile() {
        showLoadingDialog(this)
        ProfileService(this).tryGetProfile(getUserIdx())
    }

    private fun setProfile(result: ProfileResult) {
        currentNickname = result.nickName
        currentColor = result.color -1
        currentCharacter = (result.characters)*2-2

        binding.etNickname.setText(currentNickname)
        val chip = binding.chipgroupProfile.getChildAt(currentColor) as Chip?
        chip?.isChecked = true
        val radioBtn = binding.radiogroupProfile.getChildAt(currentCharacter) as RadioButton?
        radioBtn?.isChecked = true

        val currentCharImg = currentColor*5 + (result.characters-1)
        binding.ivProfileChar.setBackgroundResource(EatooCharList[currentCharImg])
    }

    private fun setOnClickListeners() {
        binding.toolbarProfile.leftIcon.setOnClickListener {
            finish()
        }
        binding.btnProfileConfirm.setOnClickListener(this)
        binding.chip1.setOnClickListener(this)
        binding.chip2.setOnClickListener(this)
        binding.chip3.setOnClickListener(this)
        binding.chip4.setOnClickListener(this)
        binding.chip5.setOnClickListener(this)
        binding.radiobtnCharacter1.setOnClickListener(this)
        binding.radiobtnCharacter2.setOnClickListener(this)
        binding.radiobtnCharacter3.setOnClickListener(this)
        binding.radiobtnCharacter4.setOnClickListener(this)
        binding.radiobtnCharacter5.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_profile_confirm -> changeProfile()
            R.id.chip_1 -> if(binding.chip1.isChecked)  changeProfileChar()
            R.id.chip_2 -> if(binding.chip2.isChecked)  changeProfileChar()
            R.id.chip_3 -> if(binding.chip3.isChecked)  changeProfileChar()
            R.id.chip_4 -> if(binding.chip4.isChecked)  changeProfileChar()
            R.id.chip_5 -> if(binding.chip5.isChecked)  changeProfileChar()
            R.id.radiobtn_character1 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character2 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character3 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character4 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character5 ->changeProfileCharOnColorChange()

        }
    }

    private fun changeProfileCharOnColorChange() {
        if(binding.chip1.isChecked)  changeProfileChar()
        if(binding.chip2.isChecked)  changeProfileChar()
        if(binding.chip3.isChecked)  changeProfileChar()
        if(binding.chip4.isChecked)  changeProfileChar()
        if(binding.chip5.isChecked)  changeProfileChar()
    }


    private fun changeProfileChar() {
        val chipTag
                = binding.chipgroupProfile.findViewById<Chip>(binding.chipgroupProfile.checkedChipId).tag.toString()
            .toInt()

        val radioBtnTag =
            binding.radiogroupProfile.findViewById<RadioButton>(binding.radiogroupProfile.checkedRadioButtonId).tag.toString()
                .toInt()

        binding.ivProfileChar.setBackgroundResource(EatooCharList[(chipTag-1) * 5 + (radioBtnTag - 1)])
    }


    private fun changeProfile() {
        val nickname = if(binding.etNickname.text.toString() != currentNickname) binding.etNickname.text.toString()
        else currentNickname

        var checkedChipTag = 1
        val checkedChip =binding.chipgroupProfile.children
            .toList()
            .filter { (it as Chip).isChecked }
        if(checkedChip.isEmpty()) {
            showCustomToast(resources.getString(R.string.input_color_request))
            return
        }
        else checkedChipTag = checkedChip[0].tag.toString().toInt()

        val color = if(checkedChipTag != currentColor) checkedChipTag
        else currentColor

        val checkedRadioButton
        = binding.radiogroupProfile.findViewById<RadioButton>(binding.radiogroupProfile.checkedRadioButtonId).tag.toString().toInt()

        val character = if(checkedRadioButton != currentCharacter) checkedRadioButton
        else currentCharacter

        patchProfileChange(PatchProfileRequest(nickname, color, character))
    }

    private fun patchProfileChange(patchProfileRequest: PatchProfileRequest) {
        showLoadingDialog(this)
        ProfileService(this).tryPatchProfile(getUserIdx(), patchProfileRequest)
    }

    /////////////server result

    override fun onGetProfileSuccess(response: ProfileResponse) {
        dismissLoadingDialog()
        setProfile(response.result)
    }

    override fun onGetProfileFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchProfileSuccess(response: PatchProfileResponse) {
        dismissLoadingDialog()
        showCustomToast("프로필 수정 완료")
        finish()
    }

    override fun onPatchProfileFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}