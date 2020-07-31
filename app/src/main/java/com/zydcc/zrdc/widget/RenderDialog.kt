package com.zydcc.zrdc.widget

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.entity.dic.Project
import kotlinx.android.synthetic.main.actionbar_common.view.*
import kotlinx.android.synthetic.main.dialog_layer_render.view.*

/**
 * =======================================
 * 渲染界面
 * Create by ningsikai 2020/6/19:8:33 AM
 * ========================================
 */
class RenderDialog(
    private var context: Activity,
    private var currentProject: Project,
    private var layer: Layer

) {

    private lateinit var dialog: AlertDialog
    private val dataBase = AppDatabase.getInstance(context.applicationContext)

    private var lineColorBackground = ContextCompat.getColor(context, R.color.primary_text)
    private var fillColorBackground = ContextCompat.getColor(context, R.color.fill_color)

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_layer_render,  null)
        dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .show()
        initData(view)
    }

    private fun initData(view: View) {
        view.tv_title.text = context.getString(R.string.txt_render)
        view.btn_back.setOnClickListener {
            dialog.dismiss()
        }
        // 如果是未标注
        view.sc_label.isChecked = layer.isLabel != 0

        view.sc_label.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                layer.isLabel = 1
            } else {
                layer.isLabel = 0
            }
            suspend {
                dataBase.layerDao().update(layer)
            }
        }

        view.line_color.setOnClickListener {
            getLineColorPickerDialog(view).show()
        }

        view.fill_color.setOnClickListener {
            getFillColorPickerDialog(view).show()
        }

    }



    private fun getLineColorPickerDialog(view: View): androidx.appcompat.app.AlertDialog {
        return ColorPickerDialogBuilder
            .with(context)
            .setTitle("请选择边线颜色")
            .initialColor(lineColorBackground)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(context.getString(R.string.txt_confirm)) { _, _selectColor, _ ->
                lineColorBackground = _selectColor
                view.line_color.setBackgroundColor(lineColorBackground)
            }
            .setNegativeButton(context.getString(R.string.txt_cancel)) { _, _ ->

            }
            .build()

    }

    private fun getFillColorPickerDialog(view: View): androidx.appcompat.app.AlertDialog {
        return ColorPickerDialogBuilder
            .with(context)
            .setTitle("请选择填充颜色")
            .initialColor(fillColorBackground)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(context.getString(R.string.txt_confirm)) { _, _selectColor, _ ->
                fillColorBackground = _selectColor
                view.fill_color.setBackgroundColor(fillColorBackground)
            }
            .setNegativeButton(context.getString(R.string.txt_cancel)) { _, _ ->

            }
            .build()

    }



}