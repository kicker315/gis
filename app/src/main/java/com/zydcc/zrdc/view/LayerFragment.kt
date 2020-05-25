package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.zydcc.zrdc.databinding.FragmentLayerBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.widget.DatasourceChooseFragment

/**
 * =======================================
 * Layer
 * Create by ningsikai 2020/5/19:1:42 PM
 * ========================================
 */
class LayerFragment : Fragment() {

    val viewModel: ViewModel by viewModels {
        InjectorUtils.providerLayerManagerModelFactory(this)
    }


    private var mGeoChooseDialog: DatasourceChooseFragment ?= null
    private var mTpkChooseDialog: DatasourceChooseFragment ?= null

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
                    if (mGeoChooseDialog == null) {
                        mGeoChooseDialog = DatasourceChooseFragment()
                    }
                    mGeoChooseDialog!!.show(activity!!.supportFragmentManager, "geo")
                }

                override fun addTpk() {
                    if (mTpkChooseDialog == null) {
                        mTpkChooseDialog = DatasourceChooseFragment()
                    }
                    mTpkChooseDialog!!.show(activity!!.supportFragmentManager, "tpk")
                }

            }
        }

        return binding.root
    }

    interface Callback {

        fun addShp()

        fun addTpk()

    }

}