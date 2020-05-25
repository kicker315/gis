package com.zydcc.zrdc.viewmodels

import android.graphics.Color
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/22:8:33 AM
 * ========================================
 */
class DrawViewModel : ViewModel() {

    var currentBackgroundColor = ObservableInt(Color.BLACK)


}

class DrawViewModelFactory(): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrawViewModel() as T
    }

}