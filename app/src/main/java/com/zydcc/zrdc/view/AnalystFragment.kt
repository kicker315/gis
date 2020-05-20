package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentAnalystBinding

/**
 * =======================================
 * 分析Fragment
 * Create by ningsikai 2020/5/19:1:45 PM
 * ========================================
 */
class AnalystFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAnalystBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

}