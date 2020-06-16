package com.zydcc.zrdc.view.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.zydcc.zrdc.R
import com.zydcc.zrdc.databinding.ActivitySplashBinding
import com.zydcc.zrdc.view.main.MainActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:9:43 AM
 * ========================================
 */
class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        requirePermission()
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun requirePermission() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.RECORD_AUDIO
        )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            startActivity(Intent(this, MainActivity::class.java))
            return
        }
        EasyPermissions.requestPermissions(this, "一张图需要申请以下权限", RC_CAMERA_AND_LOCATION, *permissions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    companion object {
        private const val RC_CAMERA_AND_LOCATION = 1
    }


}