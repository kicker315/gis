package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zydcc.zrdc.databinding.FragmentQueryBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.viewmodels.QueryStaticsViewModel

/**
 * =======================================
 * 查询
 * Create by ningsikai 2020/5/19:1:43 PM
 * ========================================
 */
class QueryFragment: Fragment() {

    val viewModel: QueryStaticsViewModel by viewModels {
        InjectorUtils.providerQueryStaticsViewModelFactory(this)
    }

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