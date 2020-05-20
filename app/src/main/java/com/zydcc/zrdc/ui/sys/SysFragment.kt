package com.zydcc.zrdc.ui.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.zydcc.zrdc.databinding.FragmentSysBinding

/**
 * =======================================
 * 设置页面
 * Create by ningsikai 2020/5/20:8:28 AM
 * ========================================
 */
class SysFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSysBinding.inflate(inflater, container, false)
        context ?: return  binding.root
        binding.toolBar.setNavigationOnClickListener {view ->
            view.findNavController().navigateUp()
        }
        return binding.root
    }

}