package com.zydcc.zrdc.arcgis.layermgr;

import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.zydcc.zrdc.arcgis.GisMapView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * =======================================
 * 业务图层管理抽象类
 * Create by ningsikai 2020/6/2:9:44 AM
 * ========================================
 */
public class GisAbstractOperationalLayer {

    protected HashMap<String, Layer> mapLayer = new HashMap<>(); // 所有特征图层的集合
    protected GisMapView mapView;

    public GisAbstractOperationalLayer(GisMapView mapView) {
        this.mapView = mapView;
    }

    public ArcGISMap getArcMap() {
        return mapView.getMap();
    }

    public Basemap getBaseMap() {
        return mapView.getMap().getBasemap();
    }

    public Layer getOperationLayer(int index) {
        return getArcMap().getOperationalLayers().get(index);
    }

    /**
     * 根据名称获取图层
     * @param key 图层名称
     * @return Layer
     */
    public Layer getLayer(String key) {
        return mapLayer.get(key);
    }

    public boolean addLayer(String key, Layer layer) {
        mapLayer.put(key, layer);
        return getArcMap().getOperationalLayers().add(layer);
    }

    public void addLayer(int index, String key, Layer layer) {
        mapLayer.put(key, layer);
        getArcMap().getOperationalLayers().add(index, layer);
    }

    /**
     * 移除图层，可传入多个图层
     * @param keys
     */
    public void removeLayer(String... keys) {
        if (keys == null) {
            return;
        }

        Set<Map.Entry<String, Layer>> entrySet = mapLayer.entrySet();
        for (String key : keys) {
            for (Map.Entry<String, Layer> entry : entrySet) {
                if (entry.getKey().equals(key)) {
                    getArcMap().getOperationalLayers().remove(entry.getValue());
                    break;
                }
            }
        }
    }

    /**
     * 移除所有图层
     */
    public void removeAllLayer() {
        getArcMap().getOperationalLayers().clear();
        mapLayer.clear();
    }

    /**
     * 设置是否显示图层，可传入多个图层
     * @param isVisible
     * @param keys
     */
    public void setLayerVisible(boolean isVisible, String... keys) {
        if (keys == null) {
            return;
        }
        Set<Map.Entry<String, Layer>> entrySet = mapLayer.entrySet();
        for (Map.Entry<String, Layer> entry: entrySet) {
           for (String key: keys) {
               if (key.equals(entry.getKey())) {
                   if (getArcMap().getOperationalLayers().contains(entry.getValue())) {
                       entry.getValue().setVisible(isVisible);
                       break;
                   }
               }
           }
        }
    }

    /**
     * 设置所有图层可见性
     */
    public void setAllLayerVisible(boolean isVisible) {
        Set<Map.Entry<String, Layer>> entrySet = mapLayer.entrySet();
        for (Map.Entry<String, Layer> entry : entrySet) {
            if (getArcMap().getOperationalLayers().contains(entry.getValue())) {
                entry.getValue().setVisible(isVisible);
            }
        }
    }

    /**
     * 除了传入的值，其他的都设置为相反
     */
    public void setLayerVisibleAndOtherInverse(boolean isVisible, String... keys) {
        Set<Map.Entry<String, Layer>> entrySet = mapLayer.entrySet();
        for (Map.Entry<String, Layer> entry : entrySet) {
            if (getArcMap().getOperationalLayers().contains(entry.getValue())) {
                if (keys == null) {
                    entry.getValue().setVisible(!isVisible);
                } else {
                    boolean isHave = false;
                    for (String key : keys) {
                        if (entry.getKey().equals(key)) {
                            entry.getValue().setVisible(isVisible);
                            isHave = true;
                            break;
                        }
                    }

                    if (!isHave) {
                        entry.getValue().setVisible(!isVisible);
                    }
                }
            }
        }
    }


}
