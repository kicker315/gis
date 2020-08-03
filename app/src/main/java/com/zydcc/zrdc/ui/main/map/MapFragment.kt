package com.zydcc.zrdc.ui.main.map

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.arcgisservices.LabelDefinition
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.data.TileCache
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.location.LocationDataSource
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.esri.arcgisruntime.mapping.view.WrapAroundMode
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer
import com.esri.arcgisruntime.symbology.TextSymbol
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.zydcc.zrdc.App
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import com.zydcc.zrdc.entity.dic.Layer
import com.zydcc.zrdc.entity.dic.Project
import com.zydcc.zrdc.event.Location2Map
import com.zydcc.zrdc.service.LocationService
import com.zydcc.zrdc.ui.listener.MeasureAreaListener
import com.zydcc.zrdc.ui.listener.MeasureDistanceListener
import com.zydcc.zrdc.ui.main.MainFragmentDirections
import com.zydcc.zrdc.utils.MyLocationDataSource
import com.zydcc.zrdc.widget.DrawLayerDialog
import com.zydcc.zrdc.widget.ProjectManagerDialog
import kotlinx.android.synthetic.main.actionbar_common.*
import kotlinx.android.synthetic.main.dialog_layer_opacity.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import java.io.File

/**
 * =======================================
 * Map
 * Create by ningsikai 2020/5/19:1:40 PM
 * ========================================
 */
class MapFragment: Fragment() {



    private var baseLayer: ArcGISTiledLayer ?= null

    private var shapefilePoint: ShapefileFeatureTable ?= null
    private var shapefilePolygon: ShapefileFeatureTable ?= null
    private var shapefilePolyline: ShapefileFeatureTable ?= null

    private var shapes = mutableListOf<ShapefileFeatureTable>()
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var currentProject: Project
    private var shpLayerList = mutableListOf<Layer>()
    private var opacityAdapter = LayerOpacityAdapter()
    private var defaultMapViewOnTouchListener: DefaultMapViewOnTouchListener?= null
    private var mLocationService: LocationService ?= null
    private lateinit var mLocationDisplay: LocationDisplay
    private lateinit var mLocationDataSource: MyLocationDataSource
    private val viewModel by viewModels<MapViewModel>()

    private var mLayersHashMap = hashMapOf<Layer, com.esri.arcgisruntime.layers.Layer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        sharePreferences = requireActivity().getSharedPreferences("zydcc", MODE_PRIVATE)
        observe(viewModel.currentProject) {
            currentProject = it
        }
        initListener()
        initLayerOpacity()
        initMap()
        dealError()
    }

    private fun initListener() {
        /// ----------------- 底部栏开始 ---------------------

        // 绘制图层
        rb_draw_layer.setOnClickListener {
            drawLayerDialog()
        }
        // 选择工程
        rb_project_manager.setOnClickListener {
            projectManagerDialog()
        }

        btn_back.setOnClickListener {
            opacity_list.visibility = View.GONE
        }

        // 透明度
        rb_transparent.setOnClickListener {
            if (opacity_list.visibility == View.VISIBLE) {
                opacity_list.visibility = View.GONE
            } else {
                opacity_list.visibility = View.VISIBLE
            }
        }
        // 跳转自多媒体
        rb_media.setOnClickListener {
            navToMedia()
        }
        // 全图
        rb_all_map.setOnClickListener {
            fullMap()
        }
        // 定位自当前位置
        rb_location.setOnClickListener {
            onLocation()
        }

        /// ---------------- 底部栏结束 -----------------------


        /// ---------------- 主操作栏状态变更开始 --------------------

        rg_all.setOnCheckedChangeListener { group, checkedId ->
            resetMainOperate()
            when (checkedId) {
                R.id.rb_selection -> {
                    rg_selection.visibility = View.VISIBLE
                }
                R.id.rb_draw -> {
                    layout_draw.visibility = View.VISIBLE
                }
                R.id.rb_tools -> {
                    rg_toolbox.visibility = View.VISIBLE
                }
                R.id.rb_confirm_info -> {

                }
            }
        }

        /// ---------------- 主操作栏变更结束 ------------------------

        /// ---------------- 工具栏开始 -----------------------


        // 跳转自截屏
        rb_screenshot_manager.setOnClickListener {
            navToScreenshotManager()
        }

        rb_distance.setOnClickListener {
            measureDistance()
        }

        rb_area.setOnClickListener {
            measureArea()
        }

        confirm_hide.setOnClickListener {
            confirmHide()
        }

        /// --------------  工具栏结束 ---------------------------

        /// -------------- 绘制开始 -----------------------------
        /// -------------- 绘制结束 -----------------------------
    }

    private fun initLayerOpacity() {
        rcv_layer.adapter = opacityAdapter
        opacityAdapter.setOnSeekBarChangeListener(object: LayerOpacityAdapter.OnSeekBarChangeListener  {
            override fun onProgressChanged(
                position: Int,
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {

            }
        })
    }



    // 初始化地图
    private fun initMap() {
        // 去水印
        ArcGISRuntimeEnvironment.setLicense("runtimeadvanced,1000,rud000228325,none,3M10F7PZB0YH463EM164")
        var oldSize = -1
        observe(viewModel.tpkDatasourceList) {
            // 默认为网络
            if (oldSize == -1 && it.isEmpty()) {
                addNetBaseMap()
                return@observe
            }

            if (oldSize != it.size) {
                resetBaseMap(it[0].layerUrl)
            }
            oldSize = it.size
        }
        observe(viewModel.shpDatasourceList) {
            shpLayerList = it.toMutableList()
            // 变更
            opacityAdapter.setNewInstance(shpLayerList)
            for (item in shpLayerList) {
                addShpLayer(item)
            }
        }

        initBDLoc()
    }

    @SuppressLint("SetTextI18n")
    private fun initBDLoc() {
        mLocationDisplay = map_view.locationDisplay
        mLocationDisplay.isShowLocation = true
        mLocationDataSource = MyLocationDataSource()
        mLocationDisplay.locationDataSource = mLocationDataSource
        mLocationDisplay.autoPanMode = LocationDisplay.AutoPanMode.RECENTER
        mLocationDisplay.startAsync()
        mLocationService = App.locationService
        mLocationService?.registerListener(viewModel.bdListener)
        mLocationService?.setLocationOption(mLocationService?.getDefaultLocationClientOption())
        mLocationService?.start()
        viewModel.onLocationCallback =  {
            tv_lat.text = "纬度：" + viewModel.viewStateLiveData.value!!.latitude
            tv_lon.text =  "经度：" + viewModel.viewStateLiveData.value!!.longitude
            tv_xzb.text =  "x坐标：" + viewModel.viewStateLiveData.value!!.x
            tv_yzb.text = "y坐标：" + viewModel.viewStateLiveData.value!!.y
            mLocationService?.stop()
            val location = LocationDataSource.Location(viewModel.currentPt)
            mLocationDataSource.UpdateLocation(location)
            // 定位到当前
            map_view.setViewpointAsync(Viewpoint(viewModel.currentPt!!, 5000.0))
        }

    }

    /**
     * 添加网络底图
     */
    private fun addNetBaseMap() {
        val map = ArcGISMap(Basemap.createImagery())
        map_view.map = map
        map_view.isAttributionTextVisible = false
        map_view.wrapAroundMode = WrapAroundMode.DISABLED
    }

    private fun addShpLayer(layer: Layer) {
        val layerUrl = layer.layerUrl
        if (!FileUtils.isFileExists(File(layerUrl))) {
            return
        }
        val isShow = layer.isShow
        // 如果显示
        if (isShow == 1) {
            val shapefileFeatureTable = ShapefileFeatureTable(layerUrl)
            shapefileFeatureTable.loadAsync()
            shapefileFeatureTable.addDoneLoadingListener {
                if (shapefileFeatureTable.loadStatus == LoadStatus.LOADED && map_view.map != null) {
                    val featureLayer = FeatureLayer(shapefileFeatureTable)
                    featureLayer.renderer = initSimpleRender(layer)
                    // 允许显示标注
                    featureLayer.isLabelsEnabled = true
                    featureLayer.labelDefinitions.add(initLabel(layer))
                    map_view.map.operationalLayers.add(featureLayer)
                    mLayersHashMap[layer] = featureLayer
                    map_view.setViewpointAsync(Viewpoint(featureLayer.fullExtent))
                }
                if (shapefileFeatureTable.loadStatus == LoadStatus.FAILED_TO_LOAD) {
                    val sb = StringBuilder()
                    sb.append(shapefileFeatureTable.loadError.message)
                        .append(": 请检测图层数据是否正确")
                    ToastUtils.showLong(sb.toString())
                }
            }
        }

    }

    private fun initLabel(layer: Layer): LabelDefinition {
        val textSymbol = TextSymbol()
        textSymbol.size = 8.0f
        textSymbol.haloColor = -1
        textSymbol.haloWidth = 0.8f
        val labelColor = layer.labelColor
        val json = JsonObject()
        if (labelColor.isNotEmpty()) {
            textSymbol.color = labelColor.toInt()
            json.add("expression", JsonPrimitive(labelJsonPrimitive(layer)))
            json.add("labelExpressionInfo", JsonObject())
            json.add("labelPlacement", JsonPrimitive("esriServerPolygonPlacementAlwaysHorizontal"))
            json.add("where", JsonPrimitive("FID <> ''"))
            json.add("symbol", JsonParser().parse(textSymbol.toJson()))
        }
        return LabelDefinition.fromJson(json.toString())
    }

    private fun labelJsonPrimitive(layer: Layer): String {
        val sb =
            StringBuilder("\$feature.BSM + ' \\n ' + \$feature.YSDM")
        if (layer.labelField.isNotEmpty()) {
            val labelFields = layer.labelField.split(",")
            for (i in 0 until labelFields.size -1) {
                if (i != labelFields.size -1) {
                    sb.append("\$feature.")
                        .append(labelFields[i])
                        .append(" + '\\n' +")
                } else {
                    sb.append("\$feature.")
                    sb.append(labelFields[i])
                }
            }
        } else {
            sb.clear()
            sb.append("\$feature.NAME")
        }
        return sb.toString()
    }

    private fun initSimpleRender(layer: Layer): SimpleRenderer {
        val simpleFillSymbol =  getFillSymbolByLayer(layer, getLineSymbolByLayer(layer))
        return SimpleRenderer(simpleFillSymbol)
    }

    private fun getLineSymbolByLayer(layer: Layer): SimpleLineSymbol {
        return SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, layer.lineColor.toInt(), 1.0f)
    }

    private fun getFillSymbolByLayer(layer: Layer, lineSimple: SimpleLineSymbol): SimpleFillSymbol {
        return SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, layer.fillColor.toInt(), lineSimple)
    }



    private fun resetBaseMap(path: String) {
        baseLayer = ArcGISTiledLayer(TileCache(path))
        map_view.map = ArcGISMap(Basemap(baseLayer))
        map_view.isAttributionTextVisible = false
        map_view.wrapAroundMode = WrapAroundMode.DISABLED
    }



    private fun resetMainOperate() {
        // 隐藏选择栏
        rg_selection.visibility = View.GONE
        rg_selection.clearCheck()
        // 隐藏绘制栏
        layout_draw.visibility = View.GONE
        rg_draw_up.clearCheck()
        rg_draw_down.clearCheck()
        // 隐藏工具栏
        rg_toolbox.visibility = View.GONE
        rg_toolbox.clearCheck()
    }



    private fun dealError() {
        viewModel.onErrorCallback = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }




    private fun navToMedia() {
        val navDirections =
            MainFragmentDirections.actionMainFragmentToMediaFragment()
        findNavController().navigate(navDirections)
    }

    private fun navToScreenshotManager() {
        val navDirections =
            MainFragmentDirections.actionScreenshotManagerFragment()
        findNavController().navigate(navDirections)
    }


    private fun saveButtonStation(defaultMapViewOnTouchListener: DefaultMapViewOnTouchListener?) {

    }

    private fun drawLayerDialog() {
        val layerMap = hashMapOf<Layer, com.esri.arcgisruntime.layers.Layer>()
        DrawLayerDialog.getInstance(requireActivity(), layerMap).showDialog()
    }

    private fun projectManagerDialog() {
        ProjectManagerDialog(requireActivity() as AppCompatActivity,sharePreferences, currentProject).showDialog()
    }

    private fun fullMap() {
        baseLayer?.apply {
            val envelope = fullExtent
            map_view.setViewpointAsync(Viewpoint(envelope))
        }
    }

    private fun confirmHide() {
        message_show.visibility = View.GONE

        if (defaultMapViewOnTouchListener == null) {
            return
        }
        if (defaultMapViewOnTouchListener is MeasureAreaListener) {
            (defaultMapViewOnTouchListener as MeasureAreaListener).pointClear()
        }
        if (defaultMapViewOnTouchListener is MeasureDistanceListener) {
            (defaultMapViewOnTouchListener as MeasureDistanceListener).pointClear()
        }
        text_tool1.text = ""
        text_tool2.text = ""
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun measureDistance() {
        if (message_show.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "请确认查询或测量信息",Toast.LENGTH_SHORT).show()
            saveButtonStation(defaultMapViewOnTouchListener)
            return
        }
        defaultMapViewOnTouchListener = null
        defaultMapViewOnTouchListener = MeasureDistanceListener.getInstance(requireContext(), map_view, message_show, text_tool1)
        map_view.onTouchListener = defaultMapViewOnTouchListener
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun measureArea() {
        if (message_show.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "请确认查询或测量信息",Toast.LENGTH_SHORT).show()
            saveButtonStation(defaultMapViewOnTouchListener)
            return
        }
        defaultMapViewOnTouchListener = null
        defaultMapViewOnTouchListener = MeasureAreaListener.getInstance(requireContext(), map_view, message_show, text_tool1)
        map_view.onTouchListener = defaultMapViewOnTouchListener
    }

    private fun onLocation() {
        rb_location.isChecked = false
        mLocationService?.start()
    }



    @Subscriber
    private fun zoom2Select(event: Location2Map) {
        ToastUtils.showShort("zoomToSelect")
        if (event.layer != null) {
            val layer = event.layer!!
            if (layer.isBaseMap  == 0) {
                val arcgisLayer = mLayersHashMap[layer] ?: return
                map_view.setViewpointAsync(Viewpoint(arcgisLayer.fullExtent))
                return
            }
            if (baseLayer != null) {
                val fullExtent = baseLayer!!.fullExtent
                map_view.setViewpointAsync(Viewpoint(fullExtent))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationService?.unregisterListener(viewModel.bdListener)
        EventBus.getDefault().unregister(this)
    }




}