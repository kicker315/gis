package com.zydcc.zrdc.ui.sys.us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.R

/**
 * =======================================
 * 关于我们设置
 * Create by ningsikai 2020/5/31:11:16 AM
 * ========================================
 */
class UsSettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_us, container, false)
    }

}