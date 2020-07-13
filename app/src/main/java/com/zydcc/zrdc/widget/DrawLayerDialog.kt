package com.zydcc.zrdc.widget

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat

import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Layer
import kotlinx.android.synthetic.main.actionbar_common.view.*
import kotlinx.android.synthetic.main.dialog_draw_layer.view.*

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
    private var layer: Layer?=  null
    private var alertDialog: AlertDialog ?= null

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_draw_layer, null)
        alertDialog = AlertDialog.Builder(context)
            .setView(
                view
            )
            .setCancelable(false)
            .create()
        alertDialog?.apply {
            show()
            window?.setGravity(Gravity.CENTER)
        }

        view.tv_title.text = context.getString(R.string.txt_draw_layer)

        view.btn_back.setOnClickListener {
            alertDialog?.dismiss()
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