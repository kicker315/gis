package com.zydcc.zrdc.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.data.AppDatabase
import com.zydcc.zrdc.data.CodeBrushRepository
import com.zydcc.zrdc.data.DatasourceRepository
import com.zydcc.zrdc.interfaces.MapOperate
import com.zydcc.zrdc.viewmodels.DatasourceChooseViewModelFactory
import com.zydcc.zrdc.viewmodels.DrawViewModelFactory
import com.zydcc.zrdc.viewmodels.LayerManagerViewModelFactory
import com.zydcc.zrdc.viewmodels.MapViewModelFactory

/**
 * =======================================
 * Static methods used to inject classes needed for various Activities and Fragments
 * Create by ningsikai 2020/5/18:4:06 PM
 * ========================================
 */
object InjectorUtils {

    private fun getCodeBrushRepository(context: Context): CodeBrushRepository {
        return CodeBrushRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).codeBrushDao()
        )
    }

    private fun getDatasourceRepository(context: Context): DatasourceRepository {
        return DatasourceRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).datasourceDao()
        )
    }

    fun providerMapViewModelFactory(view: MapOperate, fragment: Fragment): MapViewModelFactory {
        val repository = getCodeBrushRepository(fragment.requireContext())
        return MapViewModelFactory(repository, view, fragment)
    }

    fun providerDrawViewModelFactory(): DrawViewModelFactory {
        return DrawViewModelFactory()
    }

    fun providerLayerManagerModelFactory(fragment: Fragment): LayerManagerViewModelFactory {
        val repository = getDatasourceRepository(fragment.requireContext())
        return LayerManagerViewModelFactory(repository)
    }

    fun providerDatasourceChooseViewModelFactory(fragment: Fragment): DatasourceChooseViewModelFactory {
        return DatasourceChooseViewModelFactory()
    }


}