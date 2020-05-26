package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.zydcc.zrdc.adapters.ShpManagerAdapter
import com.zydcc.zrdc.adapters.TpkManagerAdapter
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.databinding.FragmentLayerBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.utilities.IntentConstants
import com.zydcc.zrdc.viewmodels.LayerManagerViewModel
import com.zydcc.zrdc.widget.DatasourceChooseFragment

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


    private var mShpChooseDialog: DatasourceChooseFragment ?= null
    private var mTpkChooseDialog: DatasourceChooseFragment ?= null

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
                        mShpChooseDialog = DatasourceChooseFragment()
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.SUFFIX, "shp")
                        mShpChooseDialog?.arguments = bundle
                    }
                    mShpChooseDialog!!.show(activity!!.supportFragmentManager, "shp")
                }

                override fun addTpk() {
                    if (mTpkChooseDialog == null) {
                        mTpkChooseDialog = DatasourceChooseFragment()
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.SUFFIX, "tpk")
                        mTpkChooseDialog?.arguments = bundle
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


        observe(viewModel.geoDatasourceList) { last ->
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