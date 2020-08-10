package com.ningsk.zrdc.ui.sys.getpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ningsk.zrdc.R

/**
 * =======================================
 * 采集点设置
 * Create by ningsikai 2020/5/31:11:16 AM
 * ========================================
 */
class GetPointFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_getpoint, container, false)
    }

}