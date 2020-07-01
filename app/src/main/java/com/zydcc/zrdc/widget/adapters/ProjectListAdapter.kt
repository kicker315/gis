package com.zydcc.zrdc.widget.adapters

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.Project

/**
 * =======================================
 * 工程列表
 * Create by ningsikai 2020/6/19:10:09 AM
 * ========================================
 */
class ProjectListAdapter: BaseQuickAdapter<Project, BaseViewHolder> (R.layout.item_project_manager, null) {
    override fun convert(holder: BaseViewHolder, item: Project) {
        holder.setText(R.id.tv_project_name, item.projectName)
            .setText(R.id.tv_project_man, item.projectMan)
            .setText(R.id.tv_project_time, TimeUtils.date2String(item.startTime))
            .setText(R.id.tv_coordinate, item.coordinate)
    }

}