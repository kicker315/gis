package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingDiffrentBinding

/**
 * =======================================
 * 差分设置界面
 * Create by ningsikai 2020/5/31:11:15 AM
 * ========================================
 */
class DiffSettingFragment: Fragment() {

    private lateinit var binding: FragmentSettingDiffrentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingDiffrentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

}