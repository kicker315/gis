package com.zydcc.zrdc.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:4:57 PM
 * ========================================
 */
@InverseBindingMethods(InverseBindingMethod(type=DataBindingSeekBar::class, attribute = "progress", event = "progressAttrChanged"))
open class DataBindingSeekBar constructor(context: Context, attributes: AttributeSet) : AppCompatSeekBar(context, attributes) {

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
    }


}