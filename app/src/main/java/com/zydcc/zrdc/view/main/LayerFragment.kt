package com.zydcc.zrdc.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zydcc.zrdc.adapters.ShpManagerAdapter
import com.zydcc.zrdc.adapters.TpkManagerAdapter
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.data.Layer
import com.zydcc.zrdc.databinding.FragmentLayerBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.utilities.IntentConstants
import com.zydcc.zrdc.viewmodels.LayerManagerViewModel
import com.zydcc.zrdc.widget.DatasourceChooseDialog

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
                        bundle.putString(IntentConstants.SUFFIX, "shp")
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
                    mShpChooseDialog!!.show(activity!!.supportFragmentManager, "shp")
                }

                override fun addTpk() {
                    if (mTpkChooseDialog == null) {
                        mTpkChooseDialog = DatasourceChooseDialog()
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.SUFFIX, "tpk")
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
                    mTpkChooseDialog!!.show(activity!!.supportFragmentManager, "tpk")
                }

            }
            rcvShp.adapter = mShpManagerAdapter
            rcvTpk.adapter = mTpkManagerAdapter
        }

        binds()

        return binding.root
    }

    private fun binds() {

        mShpManagerAdapter.attrListener = {

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

        observe(viewModel.shpDatasourceList) { last ->
            mShpManagerAdapter.submitList(last)
        }
        observe(viewModel.tpkDatasourceList) { last ->
            mTpkManagerAdapter.submitList(last)
        }
    }

    interface Callback {

        fun addShp()

        fun addTpk()

    }

}