package com.makeus.eatoo.src.explanation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityExplanationBinding
import com.makeus.eatoo.src.explanation.adapter.GuideViewpageradapter

class ExplanationActivity
    : BaseActivity<ActivityExplanationBinding>(ActivityExplanationBinding::inflate), SkipListenerInterface {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pagerAdapter = GuideViewpageradapter(this)
        pagerAdapter.addFragment(GuideFragment1(this))
        pagerAdapter.addFragment(GuideFragment2(this))
        pagerAdapter.addFragment(GuideFragment3())
        binding.photoGuideVp.adapter = pagerAdapter

        registerBr()
    }

    override fun onSkipClicked() {
        binding.photoGuideVp.currentItem = 2
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