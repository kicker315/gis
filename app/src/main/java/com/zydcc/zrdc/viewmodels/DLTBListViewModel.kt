package com.zydcc.zrdc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.zydcc.zrdc.data.DLTBRepository
import com.zydcc.zrdc.MainFragment
import com.zydcc.zrdc.data.DLTB

/**
 * =======================================
 * The ViewModel for [MainFragment]
 * Create by ningsikai 2020/5/19:8:34 AM
 * ========================================
 */
class DLTBListViewModel internal constructor(
    dltbRepository: DLTBRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val dltbs: LiveData<List<DLTB>> = dltbRepository.getDLTBList()
}