package com.zydcc.zrdc.ui.view.sys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.zydcc.zrdc.R
import com.zydcc.zrdc.ui.adapters.*
import com.zydcc.zrdc.databinding.FragmentSysBinding

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
        val binding = FragmentSysBinding.inflate(inflater, container, false)
        binding.apply {
            toolBar.setNavigationOnClickListener {view ->
                view.findNavController().navigateUp()
            }
            viewPager.isUserInputEnabled = false
            viewPager.adapter = SysPageAdapter(this@SysFragment)
            callback = object: Callback {

                override fun navTo(view: View) {
                    when (view.id) {
                        R.id.setting_gps -> viewPager.currentItem = GPS_SETTING_SETTING_PAGE_INDEX
                        R.id.setting_getpoint -> viewPager.currentItem = GET_POINT_SETTING_PAGE_INDEX
                        R.id.setting_camera -> viewPager.currentItem = CAMERA_SETTING_PAGE_INDEX
                        R.id.setting_difference -> viewPager.currentItem = DIFF_SETTING_PAGE_INDEX
                        R.id.setting_coordinate -> viewPager.currentItem = COORDINATE_SETTING_PAGE_INDEX
                        R.id.setting_layertemp -> viewPager.currentItem = LAYER_TEMP_SETTING_PAGE_INDEX
                        R.id.setting_grid -> viewPager.currentItem = GRID_SETTING_PAGE_INDEX
                        R.id.setting_us -> viewPager.currentItem = US_SETTING_PAGE_INDEX
                    }
                }

            }
        }

        return binding.root
    }

    interface Callback {
        fun navTo(view: View)
    }

}