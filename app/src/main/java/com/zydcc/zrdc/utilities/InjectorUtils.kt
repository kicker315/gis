package com.zydcc.zrdc.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.data.AppDatabase
import com.zydcc.zrdc.data.CodeBrushRepository
import com.zydcc.zrdc.data.DLTBRepository
import com.zydcc.zrdc.data.LayerRepository
import com.zydcc.zrdc.interfaces.MapOperate
import com.zydcc.zrdc.viewmodels.DrawViewModelFactory
import com.zydcc.zrdc.viewmodels.LayerManagerViewModelFactory
import com.zydcc.zrdc.viewmodels.MapViewModelFactory
import com.zydcc.zrdc.viewmodels.QueryStaticsViewModelFactory

/**
 * =======================================
 * Static methods used to inject classes needed for various Activities and Fragments
 * Create by ningsikai 2020/5/18:4:06 PM
 * ========================================
 */
object InjectorUtils {

    private fun getDLTBRepository(context: Context): DLTBRepository {
        return DLTBRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).dltbDao()
        )
    }

    private fun getCodeBrushRepository(context: Context): CodeBrushRepository {
        return CodeBrushRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).codeBrushDao()
        )
    }

    private fun getDatasourceRepository(context: Context): LayerRepository {
        return LayerRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).layerDao()
        )
    }

    fun providerMapViewModelFactory(view: MapOperate, fragment: Fragment): MapViewModelFactory {
        val datasourceRepository = getDatasourceRepository(fragment.requireContext())
        val codeBrushRepository = getCodeBrushRepository(fragment.requireContext())
        return MapViewModelFactory(datasourceRepository, codeBrushRepository, view, fragment)
    }

    fun providerDrawViewModelFactory(): DrawViewModelFactory {
        return DrawViewModelFactory()
    }

    fun providerLayerManagerModelFactory(fragment: Fragment): LayerManagerViewModelFactory {
        val repository = getDatasourceRepository(fragment.requireContext())
        val dltbRepository = getDLTBRepository(fragment.requireContext())
        return LayerManagerViewModelFactory(repository, dltbRepository)
    }

    fun providerQueryStaticsViewModelFactory(fragment: Fragment): QueryStaticsViewModelFactory {
        val repository = getDatasourceRepository(fragment.requireContext())
        return QueryStaticsViewModelFactory(repository)
    }





}