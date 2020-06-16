package com.zydcc.zrdc.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.fragment.app.DialogFragment
import com.zydcc.zrdc.utilities.DimenUtils

/**
 * =======================================
 * 属性列表
 * Create by ningsikai 2020/5/26:1:11 PM
 * ========================================
 */
class FeatureAttrDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialogWindow = dialog?.window
        if (dialogWindow != null) {
            dialogWindow.decorView.setPadding(0,0,0,0)
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = dialogWindow.attributes
            lp.width = (DimenUtils.getScreenHeight(requireContext()))
            lp.height = ((DimenUtils.getScreenHeight(requireContext()) * 0.7).toInt())
            lp.gravity = Gravity.CENTER
            dialogWindow.attributes = lp
        }
    }

}