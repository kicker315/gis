package com.zydcc.zrdc.ui.main.layermanager

import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
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
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.entity.dic.Project

import com.zydcc.zrdc.utils.IntentConstants
import com.zydcc.zrdc.widget.DatasourceChooseDialog
import com.zydcc.zrdc.widget.FeatureAttrDialogFragment
import com.zydcc.zrdc.widget.RenderDialog
import com.zydcc.zrdc.utils.BundleConstants
import kotlinx.android.synthetic.main.fragment_layer.*

/**
 * =======================================
 * Layer
 * Create by ningsikai 2020/5/19:1:42 PM
 * ========================================
 */
class LayerManagerFragment : Fragment() {

    private lateinit var sp: SharedPreferences
    private lateinit var currentProject: Project

    private val viewModel by viewModels<LayerManagerViewModel>()


    private var mShpChooseDialog: DatasourceChooseDialog ?= null
    private var mTpkChooseDialog: DatasourceChooseDialog ?= null

    // 矢量图层管理适配器
    private var mShpManagerAdapter =
        ShpManagerAdapter()
    // 栅格图层管理适配器
    private var mTpkManagerAdapter =
        TpkManagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layer, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mShpManagerAdapter.draggableModule.isDragEnabled = true
        mShpManagerAdapter.draggableModule.isSwipeEnabled = false
        mShpManagerAdapter.draggableModule.setOnItemDragListener(onItemDragListener)
        rcv_shp.adapter = mShpManagerAdapter
        rcv_tpk.adapter = mTpkManagerAdapter
        binds()
    }





    private fun binds() {

        sp = requireActivity().getSharedPreferences("zydcc", Context.MODE_PRIVATE)
        btn_add_shp.setOnClickListener {
            if (mShpChooseDialog == null) {
                mShpChooseDialog = DatasourceChooseDialog()
                val bundle = Bundle()
                bundle.putString(IntentConstants.SUFFIX, ".shp")
                mShpChooseDialog?.arguments = bundle
                mShpChooseDialog?.onDatasourceSelector = {
                    val datasource = Layer(
                        layerId = -1,
                        layerName = it.title,
                        layerUrl = it.path,
                        isBaseMap = 1,
                        isEdit = 0,
                        isSelect = 1,
                        isLabel = 1,
                        fillColor = "255,0,210,200",
                        isShow = 1,
                        projectId = 0
                    )
                    viewModel.addDatasource(datasource)
                }
            }
            mShpChooseDialog!!.show(requireActivity().supportFragmentManager, "shp")
        }
        btn_add_tpk.setOnClickListener {
            if (mTpkManagerAdapter.itemCount == 1) {
                Toast.makeText(requireContext(), "请先移除已添加的tpk再添加",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (mTpkChooseDialog == null) {
                mTpkChooseDialog = DatasourceChooseDialog()
                val bundle = Bundle()
                bundle.putString(IntentConstants.SUFFIX, ".tpk")
                mTpkChooseDialog?.arguments = bundle
                mTpkChooseDialog?.onDatasourceSelector = {
                    val datasource = Layer(
                        layerId = -1,
                        layerName = it.title,
                        layerUrl = it.path,
                        isBaseMap = 0,
                        isEdit = 0,
                        isSelect = 1,
                        isLabel = 1,
                        fillColor = "255,0,210,200",
                        isShow = 1,
                        projectId = 0
                    )
                    viewModel.addDatasource(datasource)
                }
            }
            mTpkChooseDialog!!.show(requireActivity().supportFragmentManager, "tpk")
        }

        mShpManagerAdapter.addChildClickViewIds(R.id.rb_feature_attr, R.id.rb_layer_render, R.id.rb_layer_remove)

        mShpManagerAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data[position] as Layer
            when (view.id) {
                R.id.rb_feature_attr -> {
                    val featureAttrDialog = FeatureAttrDialogFragment()
                    val bundle = Bundle()
                    bundle.putParcelable(BundleConstants.BUNDLE_LAYER, data)
                    featureAttrDialog.arguments = bundle
                    featureAttrDialog.show(requireActivity().supportFragmentManager, "featureLayer")
                }
                R.id.rb_layer_render -> {
                    val renderDialog = RenderDialog(requireActivity(), currentProject,data)
                    renderDialog.showDialog()
                }
                R.id.rb_layer_remove -> {
                    viewModel.deleteDatasource(data)
                }
            }
        }

        mTpkManagerAdapter.addChildClickViewIds(R.id.rb_layer_remove)
        mTpkManagerAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data[position] as Layer
            when(view.id) {
                R.id.rb_layer_remove -> {
                    viewModel.deleteDatasource(data)
                }
            }
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
            mTpkManagerAdapter.setNewInstance(last.toMutableList())
        }
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