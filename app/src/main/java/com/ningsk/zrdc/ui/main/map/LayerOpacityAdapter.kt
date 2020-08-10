package com.ningsk.zrdc.ui.main.map

import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ningsk.zrdc.R
import com.ningsk.zrdc.entity.dic.Layer

/**
 * =======================================
 * 图层透明度适配器
 * Create by ningsikai 2020/7/2:8:43 AM
 * ========================================
 */
class LayerOpacityAdapter: BaseQuickAdapter<Layer, BaseViewHolder>(R.layout.item_layer_opacity, null) {
    override fun convert(holder: BaseViewHolder, item: Layer) {
        holder.setText(R.id.item_opacity, "100%")
            .setText(R.id.item_layer, item.layerName)
        holder.getView<AppCompatSeekBar>(R.id.item_opacity_seekbar).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (_onSeekBarChangeListener != null) {
                    _onSeekBarChangeListener?.onProgressChanged(getItemPosition(item), seekBar, progress, fromUser)


                }
            }
        })

    }


    var _onSeekBarChangeListener: OnSeekBarChangeListener ?= null


    fun setOnSeekBarChangeListener(onSeekBarChangeListener: OnSeekBarChangeListener) {
        this._onSeekBarChangeListener = onSeekBarChangeListener
    }



    interface OnSeekBarChangeListener {
        fun onProgressChanged(position: Int, seekBar: SeekBar, progress: Int, fromUser: Boolean)
    }


}