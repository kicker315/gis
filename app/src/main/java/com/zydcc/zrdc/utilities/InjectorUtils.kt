package com.zydcc.zrdc.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.ui.interfaces.MapOperate
import com.zydcc.zrdc.ui.viewmodels.DrawViewModelFactory
import com.zydcc.zrdc.ui.viewmodels.LayerManagerViewModelFactory
import com.zydcc.zrdc.ui.viewmodels.MapViewModelFactory
import com.zydcc.zrdc.ui.viewmodels.QueryStaticsViewModelFactory

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

    fun providerDrawViewModelFactory(): DrawViewModelFactory {
        return DrawViewModelFactory()
    }

    fun providerLayerManagerModelFactory(fragment: Fragment): LayerManagerViewModelFactory {

        return LayerManagerViewModelFactory()
    }

    fun providerQueryStaticsViewModelFactory(fragment: Fragment): QueryStaticsViewModelFactory {
        return QueryStaticsViewModelFactory()
    }





}