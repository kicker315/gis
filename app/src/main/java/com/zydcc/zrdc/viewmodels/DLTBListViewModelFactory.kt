package com.zydcc.zrdc.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.zydcc.zrdc.data.DLTBRepository

/**
 * =======================================
 * Factory for creating a [DLTBListViewModel] with a constructor that takes a [DLTBListViewModelFactory]
 * Create by ningsikai 2020/5/19:8:50 AM
 * ========================================
 */
class DLTBListViewModelFactory(
    private val respository: DLTBRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
): AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return DLTBListViewModel(respository, handle) as T
    }
}