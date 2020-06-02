package com.zydcc.arcgis.core;

import androidx.annotation.IntDef;

import com.zydcc.arcgis.core.tiledservice.BaseTiledParam;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/2:9:43 AM
 * ========================================
 */
public class GisMapConfig {

    @IntDef({GisMapView.TYPE_BASETILED_VEC, GisMapView.TYPE_BASETILED_IMG})
    public @interface BASETILED_TYPE {

    }

    /**
     * 初始化底图的类型（优先展示的类型）
     */
    private int baseMapType = GisMapView.TYPE_BASETILED_VEC;

    /**
     * 底图参数
     */
    private BaseTiledParam tiledParam;

    /**
     * 初始化的中心点坐标
     */
    private double[] centerPoint;

    /**
     * 初始化缩放比
     */
    private double initScale;

    /**
     * 是否缓存底图图层
     */
    private boolean isCacheBaseMap = true;

    /**
     * 底图缓存路径
     */
    private String baseMapCachePath = "";

    private double[] fullExtent;

    /**
     * 获取初始化底图类型
     */
    public int getBaseMapType() {
        return baseMapType;
    }

    public GisMapConfig setBaseMapType(@BASETILED_TYPE  int baseMapType) {
        this.baseMapType = baseMapType;
        return this;
    }

    /**
     * 获取mapView的地图数据
     */
    public BaseTiledParam getTiledParam() {
        return  tiledParam;
    }

    /**
     * 设置mapview的地图数据
     */
    public GisMapConfig setTileParam(BaseTiledParam tileParam) {
        this.tiledParam = tileParam;
        return this;
    }

    /**
     * 获取mapView的中心点坐标
     */
    public double[] getCenterPoint() {
        return centerPoint;
    }

    /**
     * 设置mapView的中心点坐标
     */
    public GisMapConfig setCenterPoint(double[] centerPoint) {
        this.centerPoint = centerPoint;
        return this;
    }

    /**
     * 获取mapView的初始缩放比
     */
    public double getInitScale() {
        return initScale;
    }

    /**
     * 设置mapView的初始缩放比
     */
    public GisMapConfig setInitScale(double initScale) {
        this.initScale = initScale;
        return this;
    }


    /**
     * 获取是否缓存底图
     */
    public boolean isCacheBaseMap() {
        return isCacheBaseMap;
    }

    /**
     * 设置是否缓存底图
     */
    public void setCacheBaseMap(boolean cacheBaseMap) {
        isCacheBaseMap = cacheBaseMap;
    }

    /**
     * 获取底图缓存路径
     */
    public String getBaseMapCachePath() {
        return baseMapCachePath;
    }

    /**
     * 设置底图缓存路径
     */
    public void setBaseMapCachePath(String baseMapCachePath) {
        this.baseMapCachePath = baseMapCachePath;
    }

    /**
     * 获取地图范围
     */
    public double[] getFullExtent() {
        return fullExtent;
    }

    /**
     * 设置地图范围
     */
    public void setFullExtent(double[] fullExtent) {
        this.fullExtent = fullExtent;
    }

}
