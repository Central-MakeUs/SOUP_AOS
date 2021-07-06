package com.example.eatoo.src.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.eatoo.R
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentHomeBinding
import com.example.eatoo.src.home.create_group.CreateGroupActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.groupPlusBtn.setOnClickListener {
            startActivity(Intent(activity, CreateGroupActivity::class.java))
        }
    }
}