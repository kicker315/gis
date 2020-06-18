package com.zydcc.zrdc.ui.view.main

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.ui.adapters.ShpManagerAdapter
import com.zydcc.zrdc.ui.adapters.TpkManagerAdapter
import com.zydcc.zrdc.databinding.FragmentLayerBinding
import com.zydcc.zrdc.model.bean.Layer
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.utilities.IntentConstants
import com.zydcc.zrdc.ui.viewmodels.LayerManagerViewModel
import com.zydcc.zrdc.ui.widget.DatasourceChooseDialog
import com.zydcc.zrdc.ui.widget.FeatureAttrDialogFragment
import com.zydcc.zrdc.utilities.BundleConstants

/**
 * =======================================
 * Layer
 * Create by ningsikai 2020/5/19:1:42 PM
 * ========================================
 */
class LayerFragment : Fragment() {

    val viewModel: LayerManagerViewModel by viewModels {
        InjectorUtils.providerLayerManagerModelFactory(this)
    }


    private var mShpChooseDialog: DatasourceChooseDialog ?= null
    private var mTpkChooseDialog: DatasourceChooseDialog ?= null

    // 矢量图层管理适配器
    private var mShpManagerAdapter = ShpManagerAdapter()
    // 栅格图层管理适配器
    private var mTpkManagerAdapter = TpkManagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLayerBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.apply {
            callback = object : Callback {
                override fun addShp() {
                    if (mShpChooseDialog == null) {
                        mShpChooseDialog = DatasourceChooseDialog()
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.SUFFIX, ".shp")
                        mShpChooseDialog?.arguments = bundle
                        mShpChooseDialog?.onDatasourceSelector = {
                            val datasource = Layer(
                            ).apply {
                                layerId = -1
                                layerName = it.title
                                layerUrl = it.path
                                isBaseMap = 1
                                isEdit = 0
                                isSelect = 1
                                isLabel = 1
                                fillColor = "255,0,210,200"
                                isShow = 1
                                projectId = 0
                            }
                            viewModel.addDatasource(datasource)
                        }
                    }
                    mShpChooseDialog!!.show(activity!!.supportFragmentManager, "shp")
                }

                override fun addTpk() {
                    if (mTpkManagerAdapter.itemCount == 1) {
                        Toast.makeText(requireContext(), "请先移除已添加的tpk再添加",Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (mTpkChooseDialog == null) {
                        mTpkChooseDialog = DatasourceChooseDialog()
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.SUFFIX, ".tpk")
                        mTpkChooseDialog?.arguments = bundle
                        mTpkChooseDialog?.onDatasourceSelector = {
                            val datasource = Layer().apply {
                                layerId = -1
                                layerName = it.title
                                layerUrl = it.path
                                isBaseMap = 0
                                isEdit = 0
                                isSelect = 1
                                isLabel = 1
                                fillColor = "255,0,210,200"
                                isShow = 1
                                projectId = 0
                            }
                            viewModel.addDatasource(datasource)
                        }
                    }
                    mTpkChooseDialog!!.show(activity!!.supportFragmentManager, "tpk")
                }

            }
            mShpManagerAdapter.draggableModule.isDragEnabled = true
            mShpManagerAdapter.draggableModule.isSwipeEnabled = false
            mShpManagerAdapter.draggableModule.setOnItemDragListener(onItemDragListener)
            rcvShp.adapter = mShpManagerAdapter
            rcvTpk.adapter = mTpkManagerAdapter
        }

        binds()

        return binding.root
    }



    private fun binds() {

        mShpManagerAdapter.attrListener = {
            val featureAttrDialog = FeatureAttrDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(BundleConstants.BUNDLE_LAYER, it)
            featureAttrDialog.arguments = bundle
            featureAttrDialog.show(requireActivity().supportFragmentManager, "featureLayer")
        }

        mShpManagerAdapter.removeListener = {
            viewModel.deleteDatasource(it)
        }


        mTpkManagerAdapter.removeListener = {
            viewModel.deleteDatasource(it)
        }

        mTpkManagerAdapter.thumbnailListener = {

        }

        mTpkManagerAdapter.zoomListener = {

        }

        var oldSize = -1;

        observe(viewModel.shpDatasourceList) { last ->
            if (last.size == oldSize) {
                return@observe
            }
            oldSize = last.size
            mShpManagerAdapter.setNewInstance(last.toMutableList())
        }
        observe(viewModel.tpkDatasourceList) { last ->
            mTpkManagerAdapter.submitList(last)
        }
    }

    interface Callback {

        fun addShp()

        fun addTpk()

    }

    private val onItemDragListener = object: OnItemDragListener {



        override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            // 开始时，item背景色变化，这里使用动画渐变
            val holder = viewHolder as BaseViewHolder
            val startColor = Color.WHITE
            val endColor = Color.rgb(245,245,245)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val v = ValueAnimator.ofArgb(startColor, endColor)
                v.addUpdateListener {
                    holder.itemView.setBackgroundColor(it.animatedValue as Int)
                }
                v.duration = 300
                v.start()
            }
        }

        override fun onItemDragMoving(
            source: RecyclerView.ViewHolder,
            from: Int,
            target: RecyclerView.ViewHolder,
            to: Int
        ) {

        }

        override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
            resetShpPosition()
            // 结束时添加一个背景色渐变
            val holder = viewHolder as BaseViewHolder
            val startColor = Color.rgb(245,245,245)
            val endColor = Color.WHITE
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val v = ValueAnimator.ofArgb(startColor, endColor)
                v.addUpdateListener {
                    holder.itemView.setBackgroundColor(it.animatedValue as Int)
                }
                v.duration = 300
                v.start()
            }
        }
    }

    private fun resetShpPosition() {
        for (index in 0 until  mShpManagerAdapter.data.size) {
            val item = mShpManagerAdapter.data[index]
            item.layerIndex = index
            viewModel.updateDatasource(item)
        }
    }

}