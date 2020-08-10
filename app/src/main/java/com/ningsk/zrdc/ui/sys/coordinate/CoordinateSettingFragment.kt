package com.ningsk.zrdc.ui.sys.coordinate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ningsk.zrdc.R

/**
 * =======================================
 * 坐标系
 * Create by ningsikai 2020/5/31:11:34 AM
 * ========================================
 */
class CoordinateSettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_coordinate, container, false)
    }

}