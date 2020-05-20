package com.zydcc.zrdc.utilities

import androidx.fragment.app.Fragment
import com.zydcc.zrdc.interfaces.MapOperate
import com.zydcc.zrdc.viewmodels.MapViewModelFactory

/**
 * =======================================
 * Static methods used to inject classes needed for various Activities and Fragments
 * Create by ningsikai 2020/5/18:4:06 PM
 * ========================================
 */
object InjectorUtils {

    fun providerMapViewModelFactory(view: MapOperate, fragment: Fragment): MapViewModelFactory {
        return MapViewModelFactory(view, fragment)
    }


}