package com.zydcc.zrdc.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.databinding.ItemFileListBinding
import com.zydcc.zrdc.model.bean.FileItem

/**
 * =======================================
 * 文件列表
 * Create by ningsikai 2020/5/25:11:58 AM
 * ========================================
 */
class FileListAdapter: ListAdapter<FileItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemFileListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dltb = getItem(position)
        (holder as ViewHolder).bind(dltb)
    }

    inner class ViewHolder(
        private val binding: ItemFileListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                onClickListener.invoke(binding.entity!!)
            }
        }

        fun bind(item: FileItem) {
            binding.apply {
                entity = item
                executePendingBindings()
            }
        }
    }

    var onClickListener: (FileItem) -> Unit = {}

}

private class DiffCallback: DiffUtil.ItemCallback<FileItem>() {

    override fun areItemsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: FileItem, newItem: FileItem): Boolean {
        return oldItem == newItem
    }

}