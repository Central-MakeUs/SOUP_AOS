package com.example.eatoo.src.home.group.vote.create_vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateVoteBinding

class CreateVoteActivity
    : BaseActivity<ActivityCreateVoteBinding>(ActivityCreateVoteBinding::inflate),
View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.customToggleVoteItem.setToggleClickListener()
        binding.customToggleMultiVote.setToggleClickListener()
        binding.customToggleAnonymousVote.setToggleClickListener()
        binding.btnRegisterVote.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_register_vote -> checkValidity()
        }
    }

    private fun checkValidity() {
        val voteName = " "
        if(binding.etVoteName.text.isEmpty()) {
            showCustomToast(resources.getString(R.string.default_web_client_id))
        }
    }
}