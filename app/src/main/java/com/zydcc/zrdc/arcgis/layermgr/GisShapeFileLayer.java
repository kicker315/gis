package com.zydcc.zrdc.arcgis.layermgr;

import android.graphics.Color;

import com.esri.arcgisruntime.data.ShapefileFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.zydcc.zrdc.arcgis.GisMapView;

/**
 * =======================================
 * Shape图层管理类
 * Create by ningsikai 2020/6/2:9:45 AM
 * ========================================
 */
public class GisShapeFileLayer extends GisFeatureLayer {

    public GisShapeFileLayer(GisMapView mapView) {
        super(mapView);
    }

    public void addFeatureLayerFromShapefile(ShapefileFeatureTable shapefileFeatureTable) {
        shapefileFeatureTable.addDoneLoadingListener(() -> {
            FeatureLayer featureLayer = new FeatureLayer(shapefileFeatureTable);
            addLayer(shapefileFeatureTable.getTableName(), featureLayer);

            SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 1.0f);
            SimpleFillSymbol fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.YELLOW, lineSymbol);
            SimpleRenderer renderer = new SimpleRenderer(fillSymbol);
            featureLayer.setRenderer(renderer);
            featureLayer.setSelectionWidth(5);//设置选中颜色
            featureLayer.setSelectionColor(Color.GREEN);
        });
        shapefileFeatureTable.loadAsync();
    }
}
