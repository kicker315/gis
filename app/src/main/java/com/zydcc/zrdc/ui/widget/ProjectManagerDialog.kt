package com.zydcc.zrdc.ui.widget

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.TimeUtils
import com.zydcc.zrdc.R
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.greendao.ProjectDao
import com.zydcc.zrdc.model.bean.Project
import com.zydcc.zrdc.ui.adapters.ProjectListAdapter
import com.zydcc.zrdc.utilities.DimenUtils
import kotlinx.android.synthetic.main.dialog_project_manager.view.*
import kotlinx.android.synthetic.main.headerview_project_manager.view.*
import org.greenrobot.greendao.Property

/**
 * =======================================
 * 工程管理
 * Create by ningsikai 2020/6/19:8:32 AM
 * ========================================
 */
class ProjectManagerDialog(
    private var context: Context,
    private var sharedPreferences: SharedPreferences,
    private var currentProject: Project
) {


    private var alertDialog: AlertDialog?= null

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_project_manager, null)
        alertDialog = AlertDialog.Builder(context)
            .setView(
                view
            )
            .setCancelable(false)
            .create()
        alertDialog?.apply {
            show()
            val width = (DimenUtils.getScreenHeight(context))
            val height = ((DimenUtils.getScreenHeight(context) * 0.7).toInt())
            window?.setLayout(width, height)
            window?.setGravity(Gravity.CENTER)
        }
        initData(view)
        view.tool_bar.setOnClickListener {
            alertDialog?.dismiss()
        }
    }


    private fun initData(view: View) {
        val projects = App.mDaoSession.projectDao.queryBuilder().orderDesc(ProjectDao.Properties.StartTime).list()
        val mAdapter = ProjectListAdapter()
        mAdapter.setNewInstance(projects)
        mAdapter.setHeaderView(getHeaderView(view.rv_project_manager))
        view.rv_project_manager.adapter = mAdapter
    }

    private fun getHeaderView(view: RecyclerView): View {
        val headerview = LayoutInflater.from(context).inflate(R.layout.headerview_project_manager, view.parent as ViewGroup, false)
        headerview.tv_project_name.text = currentProject.projectName
        headerview.tv_project_man.text = currentProject.projectMan
        headerview.tv_project_time.text = TimeUtils.date2String(currentProject.startTime)
        headerview.tv_coordinate.text = currentProject.coordinate
        return headerview
    }

    companion object {
       @Volatile private var instance: ProjectManagerDialog ?= null
        fun getInstance(context: Context, sharedPreferences: SharedPreferences,currentProject: Project):ProjectManagerDialog {
            return  instance ?: ProjectManagerDialog(
                context, sharedPreferences, currentProject
            ).also {
                instance = it
            }
        }
    }

}