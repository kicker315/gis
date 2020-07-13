package com.zydcc.zrdc.ui.main.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.entity.bean.IField
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.ui.main.query.adapter.QueryFieldSelectAdapter
import com.zydcc.zrdc.ui.main.query.adapter.QueryShowFieldSelectAdapter
import com.zydcc.zrdc.ui.main.query.adapter.QueryLayerSelectAdapter
import com.zydcc.zrdc.ui.main.query.adapter.QueryOperationAdapter
import com.zydcc.zrdc.widget.ClassicPopupWindow
import kotlinx.android.synthetic.main.fragment_query.*
import java.lang.StringBuilder

/**
 * =======================================
 * 查询
 * Create by ningsikai 2020/5/19:1:43 PM
 * ========================================
 */
class QueryFragment : Fragment() {


    // 图层弹出框
    private var mLayerPopupWindow: ClassicPopupWindow? = null

    // 显示字段弹出框
    private var mShowFieldPopupWindow: ClassicPopupWindow? = null

    // 字段弹出框
    private var mFieldPopupWindow: ClassicPopupWindow? = null

    // 操作符弹出框
    private var mOperationPopupWindow: ClassicPopupWindow? = null

    // 图层列表
    private var rcvLayerSelect: RecyclerView? = null
    private var mLayerSelectAdapter: QueryLayerSelectAdapter =
        QueryLayerSelectAdapter()

    // 选择字段列表
    private var rcvShowField: RecyclerView? = null
    private var mShowFieldAdpter = QueryShowFieldSelectAdapter()

    // 字段列表
    private var rcvField: RecyclerView? = null
    private var mFieldAdapter = QueryFieldSelectAdapter()

    // 操作符列表
    private var rcvOperation: RecyclerView? = null
    private var mOperationAdapter = QueryOperationAdapter()

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
        initRecyclerView()
        mOperationAdapter.setNewInstance(viewModel.operateList)
        observe(viewModel.shpDatasourceList) {
            if (it.isNotEmpty()) {
                mLayerSelectAdapter.setNewInstance(it.toMutableList())
            }
        }

        // 当显示字段变更时
        observe(viewModel.showFields) {
            val sb = StringBuilder()
            for (iField in it) {
                sb.append(iField.field.name)
                    .append(",")
            }
            sp_selectfield.text = sb.toString()
        }

        // 当字段变更时
        observe(viewModel.fields) {
            sp_field.text = it.field.name
        }
        // 当操作符变更时
        observe(viewModel.operateName) {
            sp_selectopera.text = it
        }


    }

    private fun initListener() {
        // 选择图层
        sp_layerlist.setOnClickListener {
            if (mLayerPopupWindow == null) {
                mLayerPopupWindow = ClassicPopupWindow.Builder(requireContext())
                    .setView(rcvLayerSelect)
                    .setWidth(it.width)
                    .build()
            }
            mLayerPopupWindow?.showCenter(it)
        }
        // 选择显示字段
        sp_selectfield.setOnClickListener {
            if (mShowFieldPopupWindow == null) {
                mShowFieldPopupWindow = ClassicPopupWindow.Builder(requireContext())
                    .setView(rcvShowField)
                    .setWidth(it.width)
                    .build()
            }
            mShowFieldPopupWindow?.showCenter(it)
        }
        // 选择字段
        sp_field.setOnClickListener {
            if (mFieldPopupWindow == null) {
                mFieldPopupWindow = ClassicPopupWindow.Builder(requireContext())
                    .setView(rcvField)
                    .setWidth(it.width)
                    .build()
            }
            mFieldPopupWindow?.showCenter(it)
        }
        // 选择操作符
        sp_selectopera.setOnClickListener {
            if (mOperationPopupWindow == null) {
                mOperationPopupWindow = ClassicPopupWindow.Builder(requireContext())
                    .setView(rcvOperation)
                    .setWidth(it.width)
                    .build()
            }
            mOperationPopupWindow?.showCenter(it)
        }
        mLayerSelectAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position] as Layer
            sp_layerlist.text = item.layerName
            observe(viewModel.dltbDao.getAll()) {
                viewModel.whenLayerSelected(item, it)

            }
            mLayerPopupWindow?.dismiss()
        }
        // 监听字段列表改变
        observe(viewModel.iFieldList){
            mShowFieldAdpter.setNewInstance(it.toMutableList())
            mFieldAdapter.setNewInstance(it.toMutableList())
        }

        mShowFieldAdpter.addChildClickViewIds(R.id.rv_layerselect)
        mShowFieldAdpter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id != R.id.rv_layerselect) {
                return@setOnItemChildClickListener
            }
            val item = adapter.data[position] as IField
            if (item.checked == 0) {
                viewModel.showFields.setNext {
                    if (it.size >= 6) {
                        Toast.makeText(requireContext(), getString(R.string.tip_multiselect_up_to_6), Toast.LENGTH_SHORT).show()
                        return@setOnItemChildClickListener
                    }
                    // 如果选中了
                    item.checked = 1
                    it.add(item)
                    return@setNext it
                }
            } else {
               viewModel.showFields.setNext {
                   item.checked = 0
                   it.remove(item)
                   return@setNext it
               }
            }
            adapter.notifyItemChanged(position)
        }

        mFieldAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position] as IField
            viewModel.fields.postValue(item)
            mFieldPopupWindow?.dismiss()
        }

        mOperationAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position] as String
            viewModel.operateName.postValue(item)
            mOperationPopupWindow?.dismiss()
        }
    }


    private fun initRecyclerView() {
        rcvLayerSelect = RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mLayerSelectAdapter
        }
        rcvShowField = RecyclerView(requireContext())
            .apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mShowFieldAdpter
            }
        rcvField = RecyclerView(requireContext())
            .apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mFieldAdapter
            }
        rcvOperation = RecyclerView(requireContext())
            .apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mOperationAdapter
            }
    }

}