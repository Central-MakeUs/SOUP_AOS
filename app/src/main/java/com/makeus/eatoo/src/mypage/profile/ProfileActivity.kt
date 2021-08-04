package com.makeus.eatoo.src.mypage.profile

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.children
import androidx.core.view.isVisible
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityProfileBinding
import com.makeus.eatoo.src.mypage.profile.model.PatchProfileRequest
import com.makeus.eatoo.src.mypage.profile.model.PatchProfileResponse
import com.makeus.eatoo.src.mypage.profile.model.ProfileResponse
import com.makeus.eatoo.src.mypage.profile.model.ProfileResult
import com.makeus.eatoo.util.EatooCharList
import com.makeus.eatoo.util.getUserIdx
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
        currentColor = if(result.color != 0) result.color -1 else 0
        currentCharacter = if(result.characters != 0) (result.characters)-1 else 0

        binding.etNickname.setText(currentNickname)
        val radioBtnColor = binding.radiogroupProfileColor.getChildAt(currentColor) as RadioButton?
        radioBtnColor?.isChecked = true
        val radioBtnChar = binding.radiogroupProfile.getChildAt(currentCharacter) as RadioButton?
        radioBtnChar?.isChecked = true

        val currentCharImg = if(result.characters != 0) currentColor*5 + currentCharacter else currentColor*5
        binding.ivProfileChar.setBackgroundResource(EatooCharList[currentCharImg])
    }

    private fun setOnClickListeners() {
        binding.toolbarProfile.leftIcon.setOnClickListener {
            finish()
        }
        binding.btnProfileConfirm.setOnClickListener(this)
        binding.rdBtnColor1.setOnClickListener(this)
        binding.rdBtnColor2.setOnClickListener(this)
        binding.rdBtnColor3.setOnClickListener(this)
        binding.rdBtnColor4.setOnClickListener(this)
        binding.rdBtnColor5.setOnClickListener(this)
        binding.radiobtnCharacter1.setOnClickListener(this)
        binding.radiobtnCharacter2.setOnClickListener(this)
        binding.radiobtnCharacter3.setOnClickListener(this)
        binding.radiobtnCharacter4.setOnClickListener(this)
        binding.radiobtnCharacter5.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_profile_confirm -> changeProfile()
            R.id.rd_btn_color1 -> if(binding.rdBtnColor1.isChecked)  changeProfileChar()
            R.id.rd_btn_color2 -> if(binding.rdBtnColor2.isChecked)  changeProfileChar()
            R.id.rd_btn_color3 -> if(binding.rdBtnColor3.isChecked)  changeProfileChar()
            R.id.rd_btn_color4 -> if(binding.rdBtnColor4.isChecked)  changeProfileChar()
            R.id.rd_btn_color5 -> if(binding.rdBtnColor5.isChecked)  changeProfileChar()
            R.id.radiobtn_character1 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character2 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character3 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character4 ->changeProfileCharOnColorChange()
            R.id.radiobtn_character5 ->changeProfileCharOnColorChange()

        }
    }

    private fun changeProfileCharOnColorChange() {
        if(binding.rdBtnColor1.isChecked)  changeProfileChar()
        if(binding.rdBtnColor2.isChecked)  changeProfileChar()
        if(binding.rdBtnColor3.isChecked)  changeProfileChar()
        if(binding.rdBtnColor4.isChecked)  changeProfileChar()
        if(binding.rdBtnColor5.isChecked)  changeProfileChar()
    }


    private fun changeProfileChar() {
        val radioBtnColorTag
                = binding.radiogroupProfileColor.findViewById<RadioButton>(binding.radiogroupProfileColor.checkedRadioButtonId).tag.toString()
            .toInt()

        val radioBtnCharTag =
            binding.radiogroupProfile.findViewById<RadioButton>(binding.radiogroupProfile.checkedRadioButtonId).tag.toString()
                .toInt()

        binding.ivProfileChar.setBackgroundResource(EatooCharList[(radioBtnColorTag-1) * 5 + (radioBtnCharTag - 1)])
    }


    private fun changeProfile() {
        val nickname = if(binding.etNickname.text.toString() != currentNickname) binding.etNickname.text.toString()
        else currentNickname

//        var checkedChipTag = 1
//        val checkedChip =binding.chipgroupProfile.children
//            .toList()
//            .filter { (it as Chip).isChecked }
//        if(checkedChip.isEmpty()) {
//            showCustomToast(resources.getString(R.string.input_color_request))
//            return
//        }
//        else checkedChipTag = checkedChip[0].tag.toString().toInt()

        val checkedRadioButtonColor
                = binding.radiogroupProfileColor.findViewById<RadioButton>(binding.radiogroupProfileColor.checkedRadioButtonId).tag.toString().toInt()

        val color = if(checkedRadioButtonColor != currentColor) checkedRadioButtonColor
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
        binding.tvNotAvailableNickname.isVisible = false
        finish()
    }

    override fun onPatchProfileFail(code : Int, message: String?) {
        dismissLoadingDialog()
        if(code == 2033) binding.tvNotAvailableNickname.isVisible = true
        else showCustomToast(message?:resources.getString(R.string.failed_connection))

    }
}