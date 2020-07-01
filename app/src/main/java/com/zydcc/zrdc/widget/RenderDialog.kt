package com.zydcc.zrdc.widget

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.entity.dic.Project
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

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_layer_render,  null)
        dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .show()
        initData(view)
    }

    private fun initData(view: View) {
        view.tool_bar.setNavigationOnClickListener {
            dialog.dismiss()
        }
    }

}