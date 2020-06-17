package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentSettingUsBinding

/**
 * =======================================
 * 关于我们设置
 * Create by ningsikai 2020/5/31:11:16 AM
 * ========================================
 */
class UsSettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingUsBinding.inflate(inflater, container, false)
        context?: return binding.root
        return binding.root
    }

}