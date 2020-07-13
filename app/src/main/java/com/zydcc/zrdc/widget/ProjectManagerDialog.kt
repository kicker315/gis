package com.zydcc.zrdc.widget

import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.TimeUtils
import com.zydcc.zrdc.R
import com.zydcc.zrdc.db.AppDatabase
import com.zydcc.zrdc.entity.dic.Project
import com.zydcc.zrdc.widget.adapters.ProjectListAdapter
import com.zydcc.zrdc.utils.DimenUtils
import kotlinx.android.synthetic.main.dialog_project_manager.view.*
import kotlinx.android.synthetic.main.headerview_project_manager.view.*

/**
 * =======================================
 * 工程管理
 * Create by ningsikai 2020/6/19:8:32 AM
 * ========================================
 */
class ProjectManagerDialog(
    var context: AppCompatActivity,
    var sharedPreferences: SharedPreferences,
    var currentProject: Project
) {


    private lateinit var alertDialog: AlertDialog
    private val dataBase = AppDatabase.getInstance(context.applicationContext)

    fun showDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_project_manager, null)
        alertDialog = AlertDialog.Builder(context)
            .setView(
                view
            )
            .setCancelable(false)
            .create()
        alertDialog.apply {
            show()
            val width = (DimenUtils.getScreenHeight(context))
            val height = ((DimenUtils.getScreenHeight(context) * 0.7).toInt())
            window?.setLayout(width, height)
            window?.setGravity(Gravity.CENTER)
        }
        initData(view)
        view.tool_bar.setNavigationOnClickListener {
            alertDialog.dismiss()
        }
    }


    private fun initData(view: View) {
        dataBase.projectDao().getProjectList().observe(context, Observer {
            val projects = it
            val mAdapter = ProjectListAdapter()
            mAdapter.setNewInstance(projects.toMutableList())
            mAdapter.setHeaderView(getHeaderView(view.rv_project_manager))
            view.rv_project_manager.adapter = mAdapter
            mAdapter.setOnItemClickListener { adapter, _, position ->
                val item = adapter.data[position] as Project
                ProjectInfoDialog(context, alertDialog, project = item, sharedPreferences = sharedPreferences).showDialog()
            }
        })

    }

    private fun getHeaderView(view: RecyclerView): View {
        val headerview = LayoutInflater.from(context).inflate(R.layout.headerview_project_manager, view.parent as ViewGroup, false)
        headerview.tv_project_name.text = currentProject.projectName
        headerview.tv_project_man.text = currentProject.projectMan
        headerview.tv_project_time.text = TimeUtils.date2String(currentProject.startTime)
        headerview.tv_coordinate.text = currentProject.coordinate
        return headerview
    }

}