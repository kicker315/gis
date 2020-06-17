package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingGetpointBinding

/**
 * =======================================
 * 采集点设置
 * Create by ningsikai 2020/5/31:11:16 AM
 * ========================================
 */
class GetPointFragment : Fragment() {

    private lateinit var binding: FragmentSettingGetpointBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingGetpointBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

}