package com.zydcc.zrdc.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingLayerTempBinding

/**
 * =======================================
 * 图层模版
 * Create by ningsikai 2020/5/31:11:32 AM
 * ========================================
 */
class LayerTempSettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingLayerTempBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingLayerTempBinding.inflate(inflater, container, false)
        return binding.root
    }

}