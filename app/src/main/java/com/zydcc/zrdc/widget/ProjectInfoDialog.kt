package com.zydcc.zrdc.widget

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.TimeUtils
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Project
import com.zydcc.zrdc.widget.adapters.ProjectLayerListAdapter
import com.zydcc.zrdc.utils.DimenUtils
import kotlinx.android.synthetic.main.dialog_project_info.view.*
import kotlinx.android.synthetic.main.headerview_project_layer_info.view.*

/**
 * =======================================
 * 工程信息弹出框
 * Create by ningsikai 2020/6/19:11:21 AM
 * ========================================
 */
class ProjectInfoDialog(
    var context: Context,
    var parentDialog: AlertDialog,
    var project: Project,
    var sharedPreferences: SharedPreferences

) {

    private lateinit var dialog: AlertDialog

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_project_info, null)
        dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.apply {
            show()
            // 去掉动画
            window?.setWindowAnimations(R.style.NoAnimationDialog)
            val width = (DimenUtils.getScreenHeight(context))
            val height = ((DimenUtils.getScreenHeight(context) * 0.7).toInt())
            window?.setLayout(width, height)
            window?.setGravity(Gravity.CENTER)
        }

        initData(view)

    }

    private fun initData(view: View) {
        view.tool_bar.setNavigationOnClickListener {
            dialog.dismiss()
        }
        val mAdapter = ProjectLayerListAdapter()
        mAdapter.setHeaderView(getHeaderView(view.rv_project_manager))
        view.rv_project_manager.adapter = mAdapter
    }

    private fun getHeaderView(viewGroup: ViewGroup): View {
        val headerView = LayoutInflater.from(context).inflate(R.layout.headerview_project_layer_info, viewGroup, false)
        headerView.tv_projectName.text = project.projectName
        headerView.tv_projectMan.text = project.projectMan
        headerView.tv_project_time.text = TimeUtils.date2String(project.startTime)
        headerView.tv_project_path.text = project.url
        return headerView
    }


}