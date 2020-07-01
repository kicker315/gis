package com.zydcc.zrdc.ui.main.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.widget.ClassicPopupWindow
import kotlinx.android.synthetic.main.fragment_query.*

/**
 * =======================================
 * 查询
 * Create by ningsikai 2020/5/19:1:43 PM
 * ========================================
 */
class QueryFragment: Fragment() {

    private var mLayerSelectAdapter: QueryLayerSelectAdapter =
        QueryLayerSelectAdapter()
    private var rcvLayerSelect: RecyclerView?= null
    private var mLayerPopupWindow: ClassicPopupWindow?= null

    private val viewModel by viewModels<QueryStaticsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
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
       sp_layerlist.setOnClickListener {
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
            mLayerPopupWindow?.dismiss()
        }
    }

}