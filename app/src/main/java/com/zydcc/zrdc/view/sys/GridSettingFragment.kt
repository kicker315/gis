package com.zydcc.zrdc.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingGridBinding

/**
 * =======================================
 * 调查
 * Create by ningsikai 2020/5/31:11:27 AM
 * ========================================
 */
class GridSettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingGridBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingGridBinding.inflate(inflater, container, false)
        return binding.root
    }

}