package com.zydcc.zrdc.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.zydcc.zrdc.interfaces.MapOperate

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/20:11:10 AM
 * ========================================
 */
class MapViewModelFactory(
    private val view: MapOperate,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
): AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MapViewModel(view, handle) as T
    }
}