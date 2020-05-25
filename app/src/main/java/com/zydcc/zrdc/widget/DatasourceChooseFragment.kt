package com.zydcc.zrdc.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.zydcc.zrdc.R
import com.zydcc.zrdc.adapters.FileListAdapter
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.databinding.FragmentDatasourceChooseBinding
import com.zydcc.zrdc.model.bean.FileItem
import com.zydcc.zrdc.utilities.DimenUtils
import com.zydcc.zrdc.utilities.FileUtils
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.viewmodels.DatasourceChooseViewModel
import com.zydcc.zrdc.viewmodels.DatasourceViewState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * =======================================
 * 数据源选择页面
 * Create by ningsikai 2020/5/25:11:03 AM
 * ========================================
 */
class DatasourceChooseFragment : DialogFragment() {



    private var mAdapter = FileListAdapter()
    private var mRefreshLayout: SwipeRefreshLayout ?= null

    val viewModel: DatasourceChooseViewModel by viewModels{
        InjectorUtils.providerDatasourceChooseViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDatasourceChooseBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.apply {

            toolBar.setOnClickListener {
                dismiss()
            }
            RecyclerViewDivider.with(requireContext())
                .color(ContextCompat.getColor(requireContext(), R.color.white_translucent_dark))
                .size(1)
                .build()
                .addTo(rcvFiles)
            rcvFiles.adapter = mAdapter
            mRefreshLayout = swipeRefresh
            binds()
        }
        return binding.root
    }


   private fun binds() {
       mRefreshLayout?.setOnRefreshListener {
           viewModel.refreshDatasource()
       }
       observe(viewModel.viewStateLiveData, this::onNextState)
   }





    override fun onStart() {
        super.onStart()
        val dialogWindow = dialog?.window
        if (dialogWindow != null) {
            dialogWindow.decorView.setPadding(0,0,0,0)
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = dialogWindow.attributes
            lp.width = (DimenUtils.getScreenHeight(requireContext()))
            lp.height = ((DimenUtils.getScreenHeight(requireContext()) * 0.7).toInt())
            lp.gravity = Gravity.CENTER
            dialogWindow.attributes = lp
        }
    }

    private fun onNextState(state: DatasourceViewState) {
        if (state.isLoading != mRefreshLayout?.isRefreshing) {
            mRefreshLayout?.isEnabled = state.isLoading
        }
        mAdapter.submitList(state.pagedList)
    }

}