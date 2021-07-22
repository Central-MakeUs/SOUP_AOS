package com.makeus.eatoo.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.makeus.eatoo.config.ApplicationClass
import com.makeus.eatoo.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivitySplashBinding
import com.makeus.eatoo.src.explanation.ExplanationActivity
import com.makeus.eatoo.src.main.MainActivity


class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("토큰", X_ACCESS_TOKEN)
        //디버깅용
        //ApplicationClass.sSharedPreferences.edit().putString(X_ACCESS_TOKEN, null).apply()
        Handler(Looper.getMainLooper()).postDelayed({

            if (ApplicationClass.sSharedPreferences.contains(X_ACCESS_TOKEN)) {
                //디버깅용
                val useridx =
                    ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, -1)
                val jwt = ApplicationClass.sSharedPreferences.getString(X_ACCESS_TOKEN, null)
                Log.d("jwt", "jwt : $jwt useridx : $useridx")

                startActivity(Intent(this, MainActivity::class.java))
            } else{
                startActivity(Intent(this, ExplanationActivity::class.java))
            }
            finish()
        }, 3000)
    }

    // 자동 로그인 기능 구현해야합니다
    // 로그인 상태이면, 로그인 액티비티를 거치지 않고 바로 홈 화면으로 넘어갑니다.
    // 현재는 임시로 3초 뒤에 메인 액티비티로 넘어가는 방식으로 세팅해놓았습니다.
}