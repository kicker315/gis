package com.zydcc.zrdc.ui.widget

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.FileUtils
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.zydcc.zrdc.R
import com.zydcc.zrdc.ui.adapters.FileListAdapter
import com.zydcc.zrdc.databinding.DialogDatasourceChooseBinding
import com.zydcc.zrdc.model.bean.FileItem
import com.zydcc.zrdc.utilities.DimenUtils
import com.zydcc.zrdc.utilities.IntentConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileFilter
import java.util.*

/**
 * =======================================
 * 数据源选择页面
 * Create by ningsikai 2020/5/25:11:03 AM
 * ========================================
 */
class DatasourceChooseDialog : DialogFragment() {



    private var mAdapter = FileListAdapter()
    private var mRefreshLayout: SwipeRefreshLayout ?= null

    private var suffix = ".shp"

    var onDatasourceSelector: (FileItem) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogDatasourceChooseBinding.inflate(inflater, container, false)
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
            mRefreshLayout?.isRefreshing = true
            refreshDatasource()
        }
        return binding.root
    }


   private fun binds() {

       suffix = arguments?.getString(IntentConstants.SUFFIX) ?: "shp"
       mRefreshLayout?.setOnRefreshListener {
           refreshDatasource()
       }
       mAdapter.onClickListener = { it ->
           onDatasourceSelector.invoke(it)
           dismiss()
       }
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

    private fun refreshDatasource() {
        GlobalScope.launch {
            val data = getData(suffix)
            mRefreshLayout?.post {
                mRefreshLayout?.isRefreshing = false
                mRefreshLayout?.isEnabled = false
                mAdapter.submitList(data)
            }
        }

    }

    private  fun getData(suffix: String): List<FileItem> {
        val sb = StringBuilder()
            .append(Environment.getExternalStorageDirectory().absolutePath)
            .append("/")
            .append(getString(R.string.app_name))
            .append("/")
        val files = FileUtils.listFilesInDirWithFilter(File(sb.toString()),
            FileFilter {
                return@FileFilter it.name.toUpperCase(Locale.CHINA).endsWith(suffix.toUpperCase(Locale.CHINA))
            }, true)
        val data = mutableListOf<FileItem>()
        for (file in files) {
            data.add(FileItem(file.name, file.absolutePath))
        }
        return data
    }

}