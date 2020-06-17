package com.zydcc.zrdc.ui.view.main

import android.graphics.Color
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
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.data.TileCache
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.WrapAroundMode
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer
import com.esri.arcgisruntime.symbology.Symbol
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.data.CodeBrush
import com.zydcc.zrdc.databinding.FragmentMapBinding
import com.zydcc.zrdc.ui.interfaces.MapOperate
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.ui.viewmodels.MapViewModel

/**
 * =======================================
 * Map
 * Create by ningsikai 2020/5/19:1:40 PM
 * ========================================
 */
class MapFragment: Fragment(), MapOperate {

    lateinit var binding: FragmentMapBinding
    // 定位点图标
    private var mLocSymbol: Symbol?= null
    //定位点图层
    private var mLocOverlay: GraphicsOverlay? = null

    private var isLocation = true

    private var baseLayer: ArcGISTiledLayer ?= null

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
        initBDLocation()
        return binding.root
    }


    // 初始化地图
    private fun initMap() {
        // 去水印
        ArcGISRuntimeEnvironment.setLicense("runtimeadvanced,1000,rud000228325,none,3M10F7PZB0YH463EM164")
        // 去除版权声明
        binding.mapView.isAttributionTextVisible = false
        addNetBaseMap()
        mLocOverlay = GraphicsOverlay()
        binding.mapView.graphicsOverlays.add(mLocOverlay)
        mLocSymbol = binding.mapView.locationDisplay.defaultSymbol
        observe(viewModel.tpkDatasourceList) {
            if (it.isNotEmpty()) {
               resetBaseMap(it[0].layerUrl)
            }
            mLocOverlay = GraphicsOverlay()
            binding.mapView.graphicsOverlays.add(mLocOverlay)
            mLocSymbol = binding.mapView.locationDisplay.defaultSymbol
        }
        observe(viewModel.shpDatasourceList) {
            if (it.isEmpty()) {
                return@observe
            }
            val shapefileFeatureTable = ShapefileFeatureTable(it[0].layerUrl)
            shapefileFeatureTable.loadAsync()
            shapefileFeatureTable.addDoneLoadingListener{
                if (shapefileFeatureTable.loadStatus == LoadStatus.LOADED) {
                    val featureLayer = FeatureLayer(shapefileFeatureTable)
                    // create the Symbol
                    val lineSymbol =
                        SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 1.0f)
                    val fillSymbol =
                        SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.YELLOW, lineSymbol)
                    // create the Renderer
                    val renderer = SimpleRenderer(fillSymbol)
                    // set the Renderer on the Layer
                    featureLayer.renderer = renderer
                    featureLayer.selectionWidth = 5.0 // 设置选中颜色
                    featureLayer.selectionColor = Color.GREEN
                    binding.mapView.map.operationalLayers.add(featureLayer)
                    binding.mapView.setViewpointAsync(Viewpoint(featureLayer.fullExtent))
                } else {
                    val error =
                        "Shapefile feature table failed to load: " + shapefileFeatureTable.loadError
                            .toString()
                    Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                    Log.d("Shapefile", error)
                }
            }
        }
    }

    private fun addNetBaseMap() {
        val map = ArcGISMap(Basemap.Type.OPEN_STREET_MAP, 37.432949, 121.497014, 10)
        binding.mapView.map = map
        binding.mapView.isAttributionTextVisible = false
        binding.mapView.wrapAroundMode = WrapAroundMode.DISABLED
    }

    private fun resetBaseMap(path: String) {
        baseLayer = ArcGISTiledLayer(TileCache(path))
        binding.mapView.map = ArcGISMap(Basemap(baseLayer))
        binding.mapView.isAttributionTextVisible = false
        binding.mapView.wrapAroundMode = WrapAroundMode.DISABLED
    }


    // 初始化百度定位
    private fun initBDLocation() {
        try {
            App.locationService?.registerListener(viewModel.bdListener)
            App.locationService?.setLocationOption(App.locationService?.defaultLocationClientOption)
            App.locationService?.start()
            viewModel.onLocationCallback = { pt ->
                if (isLocation) {
                    mLocSymbol?.let {
                        val locGraphic = Graphic(pt, it)
                        mLocOverlay?.graphics?.clear()
                        mLocOverlay?.graphics?.add(locGraphic)
                        binding.mapView.setViewpointCenterAsync(pt, 5000.0)
                    }
                    isLocation = false
                    App.locationService?.stop()
                }


            }

        } catch (e: Exception) {
            Log.d("tag", "error$e")
        }
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

    override fun fullMap() {
        baseLayer?.apply {
            val envelope = fullExtent
            binding.mapView.setViewpointAsync(Viewpoint(envelope))
        }
    }

    override fun onLocation() {
        binding.rbLocation.isChecked = false
        isLocation = true
        App.locationService?.start()
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