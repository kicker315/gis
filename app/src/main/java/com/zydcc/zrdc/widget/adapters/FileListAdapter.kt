package com.zydcc.zrdc.widget.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.bean.FileItem

/**
 * =======================================
 * 文件列表
 * Create by ningsikai 2020/5/25:11:58 AM
 * ========================================
 */
class FileListAdapter: BaseQuickAdapter<FileItem, BaseViewHolder>(R.layout.item_file_list, null) {

    override fun convert(holder: BaseViewHolder, item: FileItem) {
        holder.setText(R.id.tv_name, item.title)
    }
}

