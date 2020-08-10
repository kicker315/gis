package com.ningsk.zrdc.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.esri.arcgisruntime.util.ListenableList;
import com.ningsk.zrdc.widget.ClearDrawView;

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


    public static void MeasureClear(List<Point> points, MapView mapView, Context context) {
        ListenableList<GraphicsOverlay> graphicsOverlays = mapView.getGraphicsOverlays();
        if (graphicsOverlays.size() > 0 && points.size() > 0) {
            mapView.getGraphicsOverlays().removeAll(graphicsOverlays);
            points.clear();
        }
    }

    public static double areaMeasure(List<Point> points, MapView mapView) {
        PolygonBuilder builder = new PolygonBuilder(mapView.getSpatialReference());
        for (Point point : points) {
            builder.addPoint(point);
        }
        return GeometryEngine.area(builder.toGeometry());

    }

    public static void clearCanvas(RelativeLayout rlt, Context ctx) {
        View view = new ClearDrawView(ctx);
        view.setMinimumHeight(rlt.getMinimumHeight());
        view.setMinimumWidth(rlt.getMinimumWidth());
        view.invalidate();
        rlt.addView(view);
    }

    private static void drawLine(Point point1, Point point2, MapView mapView, SimpleLineSymbol.Style style) {
        PolylineBuilder builder = new PolylineBuilder(SpatialReferences.getWebMercator());
        builder.addPoint(point1);
        builder.addPoint(point2);
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(style, Color.BLACK, 2f);
        Graphic graphic = new Graphic(builder.toGeometry());
        GraphicsOverlay overlay = new GraphicsOverlay();
        overlay.setRenderer(new SimpleRenderer(lineSymbol));
        overlay.getGraphics().add(graphic);
        mapView.getGraphicsOverlays().add(overlay);
    }

    public static double drawLineAndMeasure(Point point1, Point point2, MapView mapView, SimpleLineSymbol.Style style) {
        PolylineBuilder builder = new PolylineBuilder(SpatialReferences.getWebMercator());
        builder.addPoint(point1);
        builder.addPoint(point2);
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(style, Color.BLACK, 2f);
        Graphic graphic = new Graphic(builder.toGeometry());
        GraphicsOverlay overlay = new GraphicsOverlay();
        overlay.setRenderer(new SimpleRenderer(lineSymbol));
        overlay.getGraphics().add(graphic);
        mapView.getGraphicsOverlays().add(overlay);
        drawSinglePoint(point2, mapView);
        return  GeometryEngine.length(builder.toGeometry());
    }

    public static void drawSinglePoint(Point point, MapView mapView) {
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        MarkerSymbol markerSymbol  = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,Color.RED, 12f);
        Graphic graphic = new Graphic(point, markerSymbol);
        graphicsOverlay.getGraphics().add(graphic);
        mapView.getGraphicsOverlays().add(graphicsOverlay);
    }

    public static void drawPointAndLine(List<Point> points, MapView mapView, boolean notPoint) {
        int size = points.size();
        PolylineBuilder builder = new PolylineBuilder(SpatialReferences.getWebMercator());
        builder.addPoint(points.get(size - 2));
        builder.addPoint(points.get(size - 1));
        if (size > 3 && notPoint) {
            int graphicSize =  mapView.getGraphicsOverlays().size();
            if (graphicSize >= 5) {
                mapView.getGraphicsOverlays().remove(graphicSize - 1);
            }
        }
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2f);
        Graphic graphic = new Graphic(builder.toGeometry());
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        graphicsOverlay.setRenderer(new SimpleRenderer(lineSymbol));
        graphicsOverlay.getGraphics().add(graphic);
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        drawSinglePoint(points.get(size -1), mapView);
        if (size >= 3 && notPoint) {
            drawLine(points.get(size - 1), points.get(0), mapView, SimpleLineSymbol.Style.DASH);
        }
    }

    public static void drawClear() {

    }

}
