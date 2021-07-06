package com.example.eatoo.src.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass
import com.example.eatoo.config.ApplicationClass.Companion.USER_IDX
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.config.BaseResponse
import com.example.eatoo.databinding.FragmentHomeBinding
import com.example.eatoo.single_status.SingleResultResponse
import com.example.eatoo.single_status.SingleService
import com.example.eatoo.single_status.SingleView


class HomeFragment
    : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), SingleView {

    val userIdx = ApplicationClass.sSharedPreferences.getInt(USER_IDX, -1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initSingleStatusListener()


    }

    private fun initSingleStatusListener() {
        binding.toolbarHome.rightIcon.setOnClickListener {
            it.isSelected = !it.isSelected
            SingleService(this).tryPatchGroup(userIdx)
        }
    }

    override fun onPatchSingleStatusSuccess(response: SingleResultResponse) {
        Log.d("homefragment", response.toString())
    }

    override fun onPatchSingleStatusFail(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}