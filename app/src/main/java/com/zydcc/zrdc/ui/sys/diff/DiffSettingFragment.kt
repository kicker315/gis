package com.zydcc.zrdc.ui.sys.diff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.R

/**
 * =======================================
 * 差分设置界面
 * Create by ningsikai 2020/5/31:11:15 AM
 * ========================================
 */
class DiffSettingFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_setting_diffrent, container, false)
    }

}