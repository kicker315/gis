package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingCameraBinding

/**
 * =======================================
 * 相机设置界面
 * Create by ningsikai 2020/5/31:11:15 AM
 * ========================================
 */
class CameraSettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingCameraBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

}