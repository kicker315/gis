package com.zydcc.zrdc.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingGpsBinding

/**
 * =======================================
 * Gps设置
 * Create by ningsikai 2020/5/31:11:15 AM
 * ========================================
 */
class GpsSettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingGpsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingGpsBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

}