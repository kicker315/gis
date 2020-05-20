package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentScreenshotManagerBinding

/**
 * =======================================
 * 截图管理
 * Create by ningsikai 2020/5/20:9:05 PM
 * ========================================
 */
class ScreenshotManagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScreenshotManagerBinding.inflate(inflater, container, false)
        context?: return binding.root
        return binding.root
    }
}