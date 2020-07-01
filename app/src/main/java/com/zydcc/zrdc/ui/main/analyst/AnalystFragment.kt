package com.zydcc.zrdc.ui.main.analyst

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.R

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
        return inflater.inflate(R.layout.fragment_analyst, container,false)
    }

}