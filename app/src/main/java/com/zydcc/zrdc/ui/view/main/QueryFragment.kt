package com.zydcc.zrdc.ui.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.databinding.FragmentQueryBinding
import com.zydcc.zrdc.model.bean.Layer
import com.zydcc.zrdc.ui.adapters.QueryLayerSelectAdapter
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.ui.viewmodels.QueryStaticsViewModel
import com.zydcc.zrdc.ui.widget.ClassicPopupWindow

/**
 * =======================================
 * 查询
 * Create by ningsikai 2020/5/19:1:43 PM
 * ========================================
 */
class QueryFragment: Fragment() {

    private lateinit var binding: FragmentQueryBinding
    private var mLayerSelectAdapter: QueryLayerSelectAdapter = QueryLayerSelectAdapter()
    private var rcvLayerSelect: RecyclerView?= null
    private var mLayerPopupWindow: ClassicPopupWindow?= null

    val viewModel: QueryStaticsViewModel by viewModels {
        InjectorUtils.providerQueryStaticsViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQueryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        context?: return binding.root
        initData()
        return binding.root
    }

    private fun initData() {
        initListener()
        initLayer()
        observe(viewModel.shpDatasourceList) {
            if (it.isNotEmpty()) {
                mLayerSelectAdapter.setNewInstance(it.toMutableList())
            }
        }
    }

    private fun initListener() {
       binding.spLayerlist.setOnClickListener {
           if (mLayerPopupWindow == null) {
               mLayerPopupWindow = ClassicPopupWindow.Builder(requireContext())
                   .setView(rcvLayerSelect)
                   .setWidth(it.width)
                   .build()
           }
           mLayerPopupWindow?.showCenter(it)
       }
    }


    private fun initLayer() {
        rcvLayerSelect = RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mLayerSelectAdapter
        }
        mLayerSelectAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position] as Layer
            viewModel.queryData.layerName.set(item.layerName)
            viewModel.queryData.layerUrl.set(item.layerUrl)
            mLayerPopupWindow?.dismiss()
        }
    }

}