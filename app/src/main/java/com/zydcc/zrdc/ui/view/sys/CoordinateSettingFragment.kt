package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingCoordinateBinding

/**
 * =======================================
 * 坐标系
 * Create by ningsikai 2020/5/31:11:34 AM
 * ========================================
 */
class CoordinateSettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingCoordinateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingCoordinateBinding.inflate(inflater, container, false)
        return binding.root
    }

}