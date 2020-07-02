package com.zydcc.zrdc.ui.media

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.entity.dic.ProjectFile

/**
 * =======================================
 *
 * Create by ningsikai 2020/7/2:1:05 PM
 * ========================================
 */
class ImageBrowserAdapter: BaseQuickAdapter<ProjectFile, BaseViewHolder>(R.layout.item_image_browser, null) {
    override fun convert(holder: BaseViewHolder, item: ProjectFile) {
        Glide.with(context)
            .load(item.fileUrl)
            .into(holder.getView(R.id.item_image))
        holder.setText(R.id.tv_filename, if (item.fileName == "") "未命名" else item.fileName)
    }

}
