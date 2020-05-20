package com.zydcc.zrdc.ui.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentQueryBinding

/**
 * =======================================
 * 查询
 * Create by ningsikai 2020/5/19:1:43 PM
 * ========================================
 */
class QueryFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQueryBinding.inflate(inflater, container, false)
        context?: return binding.root
        return binding.root
    }

}