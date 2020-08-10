package com.ningsk.zrdc.widget

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.ningsk.zrdc.R
import com.ningsk.zrdc.db.AppDatabase
import com.ningsk.zrdc.entity.dic.Layer
import com.ningsk.zrdc.entity.dic.Project
import com.ningsk.zrdc.utils.BundleConstants
import kotlinx.android.synthetic.main.actionbar_common.view.*
import kotlinx.android.synthetic.main.dialog_layer_render.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * =======================================
 * 渲染界面
 * Create by ningsikai 2020/6/19:8:33 AM
 * ========================================
 */
class RenderDialog(
    private var context: FragmentActivity,
    private var currentProject: Project,
    private var layer: Layer

) {

    private lateinit var dialog: AlertDialog
    private val dataBase = AppDatabase.getInstance(context.applicationContext)

    private var lineColorBackground = ContextCompat.getColor(context, R.color.primary_text)
    private var fillColorBackground = ContextCompat.getColor(context, R.color.fill_color)
    private var labelColorBackground = ContextCompat.getColor(context, R.color.primary_text)

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
        if (layer.fillColor != "") {
           fillColorBackground = layer.fillColor.toInt()
           view.fill_color.setBackgroundColor(fillColorBackground)
        }
        if (layer.lineColor != "") {
            lineColorBackground = layer.lineColor.toInt()
            view.line_color.setBackgroundColor(lineColorBackground)
        }
        if (layer.labelColor != "") {
            labelColorBackground = layer.labelColor.toInt()
            view.label_color.setBackgroundColor(labelColorBackground)
        }

        view.label_field.text = layer.labelField

        view.btn_back.setOnClickListener {
            layer.fillColor = fillColorBackground.toString()
            layer.lineColor = lineColorBackground.toString()
            layer.labelColor = labelColorBackground.toString()
            GlobalScope.launch {
                dataBase.layerDao().update(layer)
            }
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

        }

        view.line_color.setOnClickListener {
            getLineColorPickerDialog(view).show()
        }

        view.fill_color.setOnClickListener {
            getFillColorPickerDialog(view).show()
        }

        view.label_color.setOnClickListener {
            getLabelColorPickerDialog(view)
        }

        view.label_field.setOnClickListener {
            val dialog = LabelFieldDialog()
            val bundle = Bundle()
            bundle.putParcelable(BundleConstants.BUNDLE_LAYER, layer)
            dialog.arguments = bundle
            dialog.doneChecked = {
                if (it.isNotEmpty()) {
                    var str = ""
                    var i = 0
                    while (i < it.size) {
                        val sb = StringBuilder()
                        sb.append(str)
                            .append(it[i].field.name)
                            .append(",")
                        str = sb.toString()
                        i++
                    }
                    if (str.isNotEmpty()) {
                        str = str.substring(0, str.length - 1)
                    }
                    layer.labelField = str
                    view.label_field.text = str
                    GlobalScope.launch {
                        dataBase.layerDao().update(layer)
                    }
                }
            }
            dialog.show(context.supportFragmentManager, "featureLayer")
        }

    }



    private fun getLabelColorPickerDialog(view: View): androidx.appcompat.app.AlertDialog {
        return ColorPickerDialogBuilder
            .with(context)
            .setTitle("请选择标注颜色")
            .initialColor(lineColorBackground)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(context.getString(R.string.txt_confirm)) { _, _selectColor, _ ->
                labelColorBackground = _selectColor
                view.label_color.setBackgroundColor(labelColorBackground)
            }
            .setNegativeButton(context.getString(R.string.txt_cancel)) { _, _ ->

            }
            .build()

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