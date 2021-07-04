package com.example.eatoo.src.main.create_group

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.eatoo.config.BaseActivity
import com.example.eatoo.databinding.ActivityCreateGroupBinding

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(ActivityCreateGroupBinding::inflate) {

    private lateinit var locationManager: LocationManager
//    private lateinit var myLocationListener: MyLocationListener
    val PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.etCreateGroupLocation.setOnClickListener {
            //현재 위치 권한 다이얼로그.
            startActivity(Intent(this, GroupLocationActivity::class.java))
//            requestPermission()
        }

    }

    private fun requestPermission() {
        if (::locationManager.isInitialized.not()) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSION_REQUEST_CODE
                )
            } else  setMyLocationListener()
        }
    }

    private fun setMyLocationListener() {

    }
}