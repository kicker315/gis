package com.zydcc.zrdc.adapters

import android.view.View
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:11:32 AM
 * ========================================
 */

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("clearCheck")
fun bindClearCheck(view: RadioGroup, isClear: Boolean) {
    if (isClear) {
        view.clearCheck()
    }
}