package com.makeus.eatoo.src.explanation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityExplanationBinding
import com.makeus.eatoo.databinding.DialogFindInviteBinding
import com.makeus.eatoo.databinding.FragmentGuide2Binding
import com.makeus.eatoo.src.explanation.adapter.GuideViewpageradapter

class ExplanationActivity : BaseActivity<ActivityExplanationBinding>(ActivityExplanationBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pagerAdapter = GuideViewpageradapter(this)
        pagerAdapter.addFragment(GuideFragment1())
        pagerAdapter.addFragment(GuideFragment2())
        pagerAdapter.addFragment(GuideFragment3())
        binding.photoGuideVp.adapter = pagerAdapter
//        var current = binding.photoGuideVp.currentItem
//        if(current == 2){
//            binding.skipBtn.visibility = View.GONE
//        }
//        else{
//            binding.skipBtn.visibility = View.VISIBLE
//        }
        registerBr()


//        binding.skipBtn.setOnClickListener {
//            var current = binding.photoGuideVp.currentItem
//            binding.photoGuideVp.setCurrentItem(2, false)
//        }
    }

    private val reviewBr = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if(p1?.action.equals("finish_explanation")) {
                this@ExplanationActivity.finish()
            }
        }

    }

    private fun registerBr() {
        val filter = IntentFilter("finish_explanation")
        LocalBroadcastManager.getInstance(this).registerReceiver(reviewBr, filter)
    }

    private fun unregisterBr() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reviewBr)
    }

    override fun onDestroy() {
        unregisterBr()
        super.onDestroy()
    }
}