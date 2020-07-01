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
import com.blankj.utilcode.util.FileUtils
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.zydcc.zrdc.R
import com.zydcc.zrdc.widget.adapters.FileListAdapter
import com.zydcc.zrdc.entity.bean.FileItem
import com.zydcc.zrdc.utilities.DimenUtils
import com.zydcc.zrdc.utilities.IntentConstants
import kotlinx.android.synthetic.main.dialog_datasource_choose.*
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

    private var suffix = ".shp"

    var onDatasourceSelector: (FileItem) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_datasource_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

   private fun binds() {



       tool_bar.setNavigationOnClickListener {
           dismiss()
       }
       suffix = arguments?.getString(IntentConstants.SUFFIX) ?: "shp"
       swipe_refresh.setOnRefreshListener {
           refreshDatasource()
       }
       mAdapter.setOnItemClickListener { adapter, view, position ->
           val data = adapter.data[position] as FileItem
           onDatasourceSelector.invoke(data)
           dismiss()
       }

       swipe_refresh.isRefreshing = true

       RecyclerViewDivider.with(requireContext())
           .color(ContextCompat.getColor(requireContext(), R.color.white_translucent_dark))
           .size(1)
           .build()
           .addTo(rcv_files)
       rcv_files.adapter = mAdapter
       refreshDatasource()
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
            swipe_refresh.post {
                swipe_refresh.isRefreshing = false
                swipe_refresh.isEnabled = false
                mAdapter.setNewInstance(data.toMutableList())
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