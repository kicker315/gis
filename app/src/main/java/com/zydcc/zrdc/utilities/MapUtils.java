package com.zydcc.zrdc.utilities;


import android.content.Context;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.util.ListenableList;

import java.util.Iterator;
import java.util.List;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/16:3:03 PM
 * ========================================
 */
public class MapUtils {

    private static Point downPoint;
    private static boolean isSuccess = false;
    private static Point leftPoint;
    private static PointCollection mPointCollection = new PointCollection(SpatialReferences.getWebMercator());
    private static Point rightPoint;
    private static Point upPoint;


    public static void MeasureClear(List<Point> points, MapView mapView) {
        ListenableList<GraphicsOverlay> graphicsOverlays = mapView.getGraphicsOverlays();
        if (graphicsOverlays.size() > 0 && points.size() > 0) {
            mapView.getGraphicsOverlays().removeAll(graphicsOverlays);
        }
    }

    public static double areaMeasure(List<Point> points, MapView mapView) {
        PolygonBuilder builder = new PolygonBuilder(mapView.getSpatialReference());
        for (Point point : points) {
            builder.addPoint(point);
        }
        return GeometryEngine.area(builder.toGeometry());

    }

    public static void drawClear() {

    }

}
