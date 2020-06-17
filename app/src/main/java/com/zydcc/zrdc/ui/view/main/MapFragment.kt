package com.zydcc.zrdc.ui.view.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.FileUtils
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.data.TileCache
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.WrapAroundMode
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.data.CodeBrush
import com.zydcc.zrdc.data.Layer
import com.zydcc.zrdc.databinding.FragmentMapBinding
import com.zydcc.zrdc.ui.interfaces.MapOperate
import com.zydcc.zrdc.ui.listener.MeasureAreaListener
import com.zydcc.zrdc.ui.listener.MeasureDistanceListener
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.ui.viewmodels.MapViewModel
import java.io.File

/**
 * =======================================
 * Map
 * Create by ningsikai 2020/5/19:1:40 PM
 * ========================================
 */
class MapFragment: Fragment(), MapOperate {

    lateinit var binding: FragmentMapBinding


    private var baseLayer: ArcGISTiledLayer ?= null

    private var shapefilePoint: ShapefileFeatureTable ?= null
    private var shapefilePolygon: ShapefileFeatureTable ?= null
    private var shapefilePolyline: ShapefileFeatureTable ?= null

    private var shapes = mutableListOf<ShapefileFeatureTable>()
    private var sharePreferences: SharedPreferences ?= null
    private var shpLayerList = mutableListOf<Layer>()

    private var defaultMapViewOnTouchListener: DefaultMapViewOnTouchListener?= null

    private val viewModel : MapViewModel by viewModels {
        InjectorUtils.providerMapViewModelFactory(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.viewModel = viewModel
        binding.baseView = this
        initMap()
        dealError()
        return binding.root
    }


    // 初始化地图
    private fun initMap() {
        // 去水印
        ArcGISRuntimeEnvironment.setLicense("runtimeadvanced,1000,rud000228325,none,3M10F7PZB0YH463EM164")
        var oldSize = -1
        observe(viewModel.tpkDatasourceList) {

            if (oldSize == -1) {
                addNetBaseMap()
                return@observe
            }

            if (oldSize != it.size) {
                resetBaseMap(it[0].layerUrl)
            }
            oldSize = it.size
        }
        observe(viewModel.shpDatasourceList) {
            // shape
        }
    }

    /**
     * 添加网络底图
     */
    private fun addNetBaseMap() {
        val map = ArcGISMap(Basemap.createImagery())
        binding.mapView.map = map
        binding.mapView.isAttributionTextVisible = false
        binding.mapView.wrapAroundMode = WrapAroundMode.DISABLED
    }

    private fun addShpLayer(layer: Layer) {
        val layerUrl = layer.layerUrl
        if (!FileUtils.isFileExists(File(layerUrl))) {
            return
        }
        val isShow = layer.isShow
        // 如果显示
        if (isShow == 1) {

        }
    }

    private fun resetBaseMap(path: String) {
        baseLayer = ArcGISTiledLayer(TileCache(path))
        binding.mapView.map = ArcGISMap(Basemap(baseLayer))
        binding.mapView.isAttributionTextVisible = false
        binding.mapView.wrapAroundMode = WrapAroundMode.DISABLED
    }




    private fun dealError() {
        viewModel.onErrorCallback = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun navToMedia() {
        val navDirections =
            MainFragmentDirections.actionMainFragmentToMediaFragment()
        findNavController().navigate(navDirections)
    }

    override fun navToScreenshotManager() {
        val navDirections =
            MainFragmentDirections.actionScreenshotManagerFragment()
        findNavController().navigate(navDirections)
    }


    private fun saveButtonStation(defaultMapViewOnTouchListener: DefaultMapViewOnTouchListener?) {

    }

    override fun fullMap() {
        baseLayer?.apply {
            val envelope = fullExtent
            binding.mapView.setViewpointAsync(Viewpoint(envelope))
        }
    }

    override fun confirmHide() {

        binding.messageShow.visibility = View.GONE

        if (defaultMapViewOnTouchListener == null) {
            return
        }
        if (defaultMapViewOnTouchListener is MeasureAreaListener) {
            (defaultMapViewOnTouchListener as MeasureAreaListener).pointClear()
        }
        if (defaultMapViewOnTouchListener is MeasureDistanceListener) {
            (defaultMapViewOnTouchListener as MeasureDistanceListener).pointClear()
        }
        binding.textTool1.text = ""
        binding.textTool2.text = ""
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun measureDistance() {
        if (binding.messageShow.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "请确认查询或测量信息",Toast.LENGTH_SHORT).show()
            saveButtonStation(defaultMapViewOnTouchListener)
            return
        }
        defaultMapViewOnTouchListener = null
        defaultMapViewOnTouchListener = MeasureDistanceListener.getInstance(requireContext(), binding.mapView, binding.messageShow, binding.textTool1)
        binding.mapView.onTouchListener = defaultMapViewOnTouchListener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun measureArea() {
        if (binding.messageShow.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "请确认查询或测量信息",Toast.LENGTH_SHORT).show()
            saveButtonStation(defaultMapViewOnTouchListener)
            return
        }
        defaultMapViewOnTouchListener = null
        defaultMapViewOnTouchListener = MeasureAreaListener.getInstance(requireContext(), binding.mapView, binding.messageShow, binding.textTool1)
        binding.mapView.onTouchListener = defaultMapViewOnTouchListener
    }

    override fun onLocation() {
        binding.rbLocation.isChecked = false
    }

    override fun showCodeBrushList(data: LiveData<List<CodeBrush>>) {
        data.observe(viewLifecycleOwner,
            Observer { codes ->
                Log.d("code", codes.toString())
            })
    }

    override fun showToast(type: Int, msg: String?) {

    }

    override fun showProgressDialog(title: String, iscancel: Boolean) {

    }

    override fun closeProgressDialog() {

    }


}