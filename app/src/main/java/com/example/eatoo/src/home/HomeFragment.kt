package com.example.eatoo.src.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentHomeBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.groupPlusBtn.setOnClickListener {
            startActivity(Intent(activity, CreateGroupActivity::class.java))
        }
    }
}