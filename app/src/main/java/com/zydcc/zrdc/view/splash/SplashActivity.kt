package com.zydcc.zrdc.view.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.permission.PermissionX
import com.zydcc.zrdc.databinding.ActivitySplashBinding
import com.zydcc.zrdc.view.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:9:43 AM
 * ========================================
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        requirePermission()
    }

    private fun requirePermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            )
            .onExplainRequestReason { deniedList ->
                showRequestReasonDialog(deniedList, "软件需要开启以下权限", "确定", "取消")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    GlobalScope.launch {
                        delay(2000)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
    }

}