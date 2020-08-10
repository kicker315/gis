package com.ningsk.zrdc.ui.sys.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ningsk.zrdc.R

/**
 * =======================================
 * 相机设置界面
 * Create by ningsikai 2020/5/31:11:15 AM
 * ========================================
 */
class CameraSettingFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_camera, container, false)
    }

}