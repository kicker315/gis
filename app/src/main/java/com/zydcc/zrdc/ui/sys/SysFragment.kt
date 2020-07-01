package com.zydcc.zrdc.ui.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.zydcc.zrdc.R
import com.zydcc.zrdc.widget.adapters.*
import kotlinx.android.synthetic.main.fragment_sys.*

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
        return inflater.inflate(R.layout.fragment_sys, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        tool_bar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        view_pager.isUserInputEnabled = false
        view_pager.adapter = SysPageAdapter(this@SysFragment)
        setting_gps.setOnClickListener { view_pager.currentItem = GPS_SETTING_SETTING_PAGE_INDEX }
        setting_getpoint.setOnClickListener { view_pager.currentItem = GET_POINT_SETTING_PAGE_INDEX }
        setting_camera.setOnClickListener {view_pager.currentItem = CAMERA_SETTING_PAGE_INDEX }
        setting_difference.setOnClickListener { view_pager.currentItem = DIFF_SETTING_PAGE_INDEX }
        setting_coordinate.setOnClickListener { view_pager.currentItem = COORDINATE_SETTING_PAGE_INDEX }
        setting_layertemp.setOnClickListener { view_pager.currentItem = LAYER_TEMP_SETTING_PAGE_INDEX  }
        setting_us.setOnClickListener { view_pager.currentItem = US_SETTING_PAGE_INDEX  }
    }

    interface Callback {
        fun navTo(view: View)
    }

}