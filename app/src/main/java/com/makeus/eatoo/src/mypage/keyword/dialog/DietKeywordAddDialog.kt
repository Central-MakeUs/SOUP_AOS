package com.makeus.eatoo.src.mypage.keyword.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import com.makeus.eatoo.databinding.DialogDietKeywordAddBinding
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordInfo
import com.makeus.eatoo.src.mypage.keyword.model.DietKeywordReq

class DietKeywordAddDialog(
    context : Context,
    val listener : DietKeywordAddDialogInterface
) : Dialog(context) {
    private lateinit var binding: DialogDietKeywordAddBinding
    private var seekbarLevel : Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogDietKeywordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setSeekBar()

        binding.btnDialogConfirm.setOnClickListener {
            if(seekbarLevel == 0) {
                Toast.makeText(context, "강조 레벨을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val radioBtnPrefer
                    = binding.radiogroupDietKeyword.findViewById<RadioButton>(binding.radiogroupDietKeyword.checkedRadioButtonId).tag.toString()
                .toInt()
            val isPrefer = if(radioBtnPrefer==1) "Y" else "N"
            listener.onDietKeywordAddClicked(DietKeywordInfo(binding.etDietKeywordInput.text.toString(), isPrefer, seekbarLevel!!))
            dismiss()
        }
    }

    private fun setSeekBar() {
        binding.seekbarDietKeyword.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                seekbarLevel = p0?.progress
            }
        })
    }
}
