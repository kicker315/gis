package com.zydcc.zrdc.ui.sys.layertemp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.R

/**
 * =======================================
 * 图层模版
 * Create by ningsikai 2020/5/31:11:32 AM
 * ========================================
 */
class LayerTempSettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_layer_temp, container, false)
    }

}