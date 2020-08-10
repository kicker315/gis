package com.ningsk.zrdc.ui.freedraw

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/22:8:33 AM
 * ========================================
 */
class DrawViewModel(application: Application) : AndroidViewModel(application) {

    var currentBackgroundColor: MutableLiveData<Int> = MutableLiveData()


}