package com.zydcc.zrdc.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.db.AppDatabase

import com.zydcc.zrdc.entity.bean.IField
import com.zydcc.zrdc.entity.dic.Dltb
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.widget.adapters.FeatureAttrFieldAdapter
import com.zydcc.zrdc.utils.BundleConstants
import com.zydcc.zrdc.utils.DimenUtils
import kotlinx.android.synthetic.main.actionbar_common.*
import kotlinx.android.synthetic.main.dialog_feature_attr.*
import kotlinx.android.synthetic.main.dialog_feature_attr.view.*
import java.util.*

/**
 * =======================================
 * 属性列表
 * Create by ningsikai 2020/5/26:1:11 PM
 * ========================================
 */
class FeatureAttrDialogFragment : DialogFragment() {

    private var mAdapter = FeatureAttrFieldAdapter()
    private lateinit var layer: Layer
    private var iFieldList = mutableListOf<IField>()
    private lateinit var dataBase: AppDatabase


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
            dismiss()
        }
        tv_title.text = getString(R.string.txt_layer_attr_info)
        val dltbDao = dataBase.dltbDao()
        observe(dltbDao.getAll()) {
            initData(it, view)
        }

    }

    private fun initData(dltbList: List<Dltb>, view: View) {
        layer = requireArguments().getParcelable(BundleConstants.BUNDLE_LAYER) as Layer

        val shapeFiFeatureTable = ShapefileFeatureTable(layer.layerUrl)
        shapeFiFeatureTable.loadAsync()
        shapeFiFeatureTable.addDoneLoadingListener{

            val fields = shapeFiFeatureTable.fields
            for (index in 0 until  fields.size) {
                val field = fields[index]
                val name = field.name.toUpperCase(Locale.CHINA)
                var dltb: Dltb?= null
                if (name != "FID" && name != "OBJECTID" && name != "SHAPE_LENG" && name != "SHAPE_AREA") {
                    for (item in dltbList) {
                        if (name == item.zddm) {
                            dltb = item
                            break
                        }
                    }
                }

                val iField = IField(
                    checked = 0,
                    dltb = dltb,
                    field = field
                )

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

}