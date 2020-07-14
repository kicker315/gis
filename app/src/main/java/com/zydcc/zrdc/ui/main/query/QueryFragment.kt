package com.zydcc.zrdc.ui.main.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.esri.arcgisruntime.data.Feature
import com.esri.arcgisruntime.data.Field
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.entity.bean.IField
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.ui.main.query.adapter.*
import com.zydcc.zrdc.widget.ClassicPopupWindow
import kotlinx.android.synthetic.main.fragment_query.*
import java.lang.StringBuilder
import java.util.concurrent.CopyOnWriteArrayList

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

    private var selectLayer: Layer? = null
    private var showFieldSelect = mutableListOf<Field>()
    private var field: Field?= null

    private lateinit var mResultAdapter: QueryResultAdapter

    private val pageSize = 20
    private var nextRequestPage = 0
    private var mResultList = mutableListOf<Feature>()

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

        initResult()
        mOperationAdapter.setNewInstance(viewModel.operateList)
        observe(viewModel.shpDatasourceList) {
            if (it.isNotEmpty()) {
                mLayerSelectAdapter.setNewInstance(it.toMutableList())
            }
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
            selectLayer = item
            reset()
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
                // 如果选中了
                if (showFieldSelect.size >= 6) {
                    ToastUtils.showShort(getString(R.string.tip_multiselect_up_to_6))
                    return@setOnItemChildClickListener
                }
                item.checked = 1
                showFieldSelect.add(item.field)
            } else {
                item.checked = 0
                showFieldSelect.remove(item.field)
            }
            val sb = StringBuilder()
            for (field in showFieldSelect) {
                sb.append(field.name)
                    .append(",")
            }
            sp_selectfield.text = sb.toString()
            adapter.notifyItemChanged(position)
        }

        mFieldAdapter.setOnItemClickListener { adapter, view, position ->
            if (position == 0) {
                sp_field.text = ""
                mFieldPopupWindow?.dismiss()
                return@setOnItemClickListener
            }
            val item = adapter.data[position] as IField
            sp_field.text = item.field.name
            field = item.field
            mFieldPopupWindow?.dismiss()
        }

        mOperationAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position] as String
            sp_selectopera.text = item
            mOperationPopupWindow?.dismiss()
        }

        // 查询
        btn_search.setOnClickListener {
            if (validate()) {
                doSearch()
            }
        }
    }

    private fun validate(): Boolean {
        if (selectLayer == null) {
            ToastUtils.showShort(getString(R.string.please_select_layer))
            return false
        }
        if (showFieldSelect.isEmpty()) {
            ToastUtils.showShort(getString(R.string.please_select_visible_fields))
            return false
        }
        if (field == null) {
            ToastUtils.showShort(getString(R.string.please_select_fields))
            return false
        }
        return true
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


    private fun reset() {
        showFieldSelect.clear()
        field = null
    }

    private fun initResult() {
        mResultAdapter = QueryResultAdapter(showFieldSelect)
        rcv_search_result.layoutManager = LinearLayoutManager(requireContext())
        rcv_search_result.adapter = mResultAdapter
        mResultAdapter.setEmptyView(R.layout.empty_view)
        mResultAdapter.isUseEmpty = true
    }

    private fun doSearch() {
        nextRequestPage = 0
        initResult()
        swipe_refresh.isRefreshing = true
        swipe_refresh.isEnabled = true
        swipe_refresh.setOnRefreshListener {
            viewModel.getSearchResult(selectLayer!!.layerUrl)
        }
        mResultAdapter.loadMoreModule.setOnLoadMoreListener {
            nextRequestPage++
            if (nextRequestPage > 0) {
                val data = CopyOnWriteArrayList<Feature>()
                if (nextRequestPage * pageSize + 20 < mResultList.size) {
                    data.addAll(mResultList.subList(nextRequestPage * pageSize, nextRequestPage * pageSize + 20))
                    mResultAdapter.addData(data)
                    mResultAdapter.loadMoreModule.loadMoreComplete()
                } else {
                    data.addAll(mResultList.subList(nextRequestPage * pageSize, mResultList.size))
                    mResultAdapter.addData(data)
                    mResultAdapter.loadMoreModule.loadMoreComplete()
                    mResultAdapter.loadMoreModule.loadMoreEnd()
                }
            }
        }
        observe(viewModel.mResultList) { it ->
            mResultList = it
            swipe_refresh.isRefreshing = false
            if (mResultList.size > pageSize) {
                mResultAdapter.setNewInstance(mResultList.subList(0, pageSize))
                mResultAdapter.loadMoreModule.loadMoreComplete()
            } else {
                mResultAdapter.setNewInstance(mResultList)
                mResultAdapter.loadMoreModule.loadMoreEnd()
            }

        }
        viewModel.getSearchResult(selectLayer!!.layerUrl)
    }

}