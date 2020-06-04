package com.zydcc.arcgis.core.layermgr;

import com.esri.arcgisruntime.data.Geodatabase;
import com.esri.arcgisruntime.data.GeodatabaseFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.zydcc.arcgis.core.GisMapView;

import java.util.List;

/**
 * =======================================
 * 特征图层管理类
 * Create by ningsikai 2020/6/2:9:45 AM
 * ========================================
 */
public class GisGeoDatabaseLayer extends GisFeatureLayer{
    public GisGeoDatabaseLayer(GisMapView mapView) {
        super(mapView);
    }

    /**
     * 根据key值批量从geodatabase中查询添加特征图层并添加到地图上
     */
    public void addFeatureLayerFromGeoDatabase(Geodatabase geodatabase, String... keys) {
        List<GeodatabaseFeatureTable> list = geodatabase.getGeodatabaseFeatureTables();
        if (keys == null || keys.length == 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                GeodatabaseFeatureTable gdbFeatureTable = list.get(i);
                if (gdbFeatureTable.hasGeometry()) {
                    FeatureLayer featureLayer = new FeatureLayer(gdbFeatureTable);
                    featureLayer.setLabelsEnabled(true);
                    String tableName = gdbFeatureTable.getTableName();
                    addLayer(tableName, featureLayer);
                }
            }
        } else {
            for (String key : keys) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    GeodatabaseFeatureTable gdbFeatureTable = list.get(i);
                    if (gdbFeatureTable.getTableName().equals(key) && gdbFeatureTable.hasGeometry()) {
                        FeatureLayer featureLayer = new FeatureLayer(gdbFeatureTable);
                        featureLayer.setLabelsEnabled(true);
                        String tableName = gdbFeatureTable.getTableName();
                        addLayer(tableName, featureLayer);
                        break;
                    }
                }
            }
        }
    }

}
