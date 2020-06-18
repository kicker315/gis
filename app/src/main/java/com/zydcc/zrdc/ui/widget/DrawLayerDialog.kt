package com.zydcc.zrdc.ui.widget

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.widget.ListPopupWindow
import com.zydcc.zrdc.R
import com.zydcc.zrdc.model.bean.Layer

/**
 * =======================================
 * 绘制图层弹出框
 * Create by ningsikai 2020/6/18:4:59 PM
 * ========================================
 */
class DrawLayerDialog(
    private var context: Context,
    private var layerMap: HashMap<Layer, com.esri.arcgisruntime.layers.Layer>
) {

    private var popupWindow: ListPopupWindow ?= null
    private var layer: Layer ?=  null
    private var alertDialog: AlertDialog ?= null

    fun showDialog() {
        alertDialog = AlertDialog.Builder(context)
            .setView(
                R.layout.dialog_draw_layer
            )
            .setCancelable(false)
            .create()
        alertDialog?.apply {
            show()
            window?.setGravity(Gravity.CENTER)
        }
    }

    companion object {
        @Volatile private var instance: DrawLayerDialog ?= null

        fun getInstance(context: Context, layerMap: HashMap<Layer, com.esri.arcgisruntime.layers.Layer>): DrawLayerDialog {
            synchronized(this) {
                return instance ?: DrawLayerDialog(
                    context, layerMap
                ).also {
                    instance = it
                }
            }
        }

    }

}