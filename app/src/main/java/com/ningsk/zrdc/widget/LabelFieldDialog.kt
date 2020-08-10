package com.ningsk.zrdc.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.ToastUtils
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.ningsk.zrdc.R
import com.ningsk.zrdc.core.ext.observe
import com.ningsk.zrdc.db.AppDatabase
import com.ningsk.zrdc.entity.bean.IField
import com.ningsk.zrdc.entity.dic.Dltb
import com.ningsk.zrdc.entity.dic.Layer
import com.ningsk.zrdc.utils.BundleConstants
import com.ningsk.zrdc.utils.DimenUtils
import com.ningsk.zrdc.widget.adapters.FeatureAttrFieldAdapter
import com.ningsk.zrdc.widget.adapters.FeatureLabelFieldAdapter
import kotlinx.android.synthetic.main.actionbar_common.*
import kotlinx.android.synthetic.main.dialog_feature_attr.view.*
import kotlinx.android.synthetic.main.item_feature_attr_field.view.*
import java.util.*

/**
 * =======================================
 * 标注信息弹出框
 * Create by ningsikai 2020/8/3:9:40 AM
 * ========================================
 */
class LabelFieldDialog : DialogFragment() {

    private var mAdapter = FeatureLabelFieldAdapter()
    private lateinit var layer: Layer
    private var iFieldList = mutableListOf<IField>()
    private lateinit var dataBase: AppDatabase
    private var labelAttr = arrayOf<String>()
    private var labelFieldList = arrayListOf<IField>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_feature_attr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = AppDatabase.getInstance(requireContext().applicationContext)
        btn_back.setOnClickListener {
            doneChecked.invoke(labelFieldList)
            dismiss()
        }
        tv_title.text = getString(R.string.txt_layer_label_info)
        initData(view)

    }

    private fun initData(view: View) {
        mAdapter.setOnItemClickListener { adapter, _view, position ->
            val data = adapter.data[position] as IField
            val cb = _view.cb_field_select
            if (cb.isChecked) {
                cb.isChecked = false
                labelFieldList.remove(data)
                data.checked = 0
            } else if (labelFieldList.size < 3) {
                cb.isChecked = true
                data.checked = 1
                labelFieldList.add(data)
            } else {
                ToastUtils.showShort(getString(R.string.tip_max_3_label_field))
            }
            adapter.notifyItemChanged(position)
        }
        
        layer = requireArguments().getParcelable(BundleConstants.BUNDLE_LAYER) as Layer
        checkedField()
        val shapeFiFeatureTable = ShapefileFeatureTable(layer.layerUrl)
        shapeFiFeatureTable.loadAsync()
        shapeFiFeatureTable.addDoneLoadingListener{

            val fields = shapeFiFeatureTable.fields
            for (index in 0 until  fields.size) {
                val field = fields[index]
                var checked = 0
                for (item in labelAttr) {
                    if (item == field.name) {
                        checked = 1
                    }
                }

                val iField = IField(
                    checked = checked,
                    dltb = null,
                    field = field
                )

                if (checked == 1) {
                    labelFieldList.add(iField)
                }

                iFieldList.add(iField)

            }
            mAdapter.setNewInstance(iFieldList)
            view.rv_feature_attr.adapter = mAdapter
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

    private fun checkedField() {
        val str = layer.labelField
        if (str.isNotEmpty()) {
            this.labelAttr = str.split(",").toTypedArray()
        }
    }

    var doneChecked: (List<IField>) -> Unit = {}

}