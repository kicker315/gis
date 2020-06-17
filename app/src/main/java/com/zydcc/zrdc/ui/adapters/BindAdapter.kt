package com.zydcc.zrdc.ui.adapters

import android.view.View
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.rm.freedrawview.FreeDrawView

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

@BindingAdapter("backgroundColor")
fun bindBackgroundColor(view: View, color: Int) {
    view.setBackgroundColor(color)
}

@BindingAdapter("paintColor")
fun bindPaintColor(view: FreeDrawView, color: Int) {
    view.paintColor = color
}

//@BindingAdapter("customDivider")
//fun bindRecycleViewDivider(view: RecyclerView, size: Int) {
//    RecyclerViewDivider.with(view.context)
//        .size(size)
//        .build()
//        .addTo(view)
//}