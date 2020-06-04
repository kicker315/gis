package com.zydcc.arcgis.core.callout;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.zydcc.arcgis.core.GisMapView;
import com.zydcc.arcgis.utils.DisplayUtil;

/**
 * =======================================
 * 地图弹出菜单管理类
 * Create by ningsikai 2020/6/2:9:44 AM
 * ========================================
 */
public class GisCalloutMgr {

    private int backgroundColor = Color.WHITE;
    private int borderColor = Color.WHITE;
    private int cornerRadius = 20;
    private int borderWidth = 2;
    private int leaderLength = 12;
    private int leaderWidth = 12;
    private int maxHeight;
    private int minHeight;
    private int maxWidth;
    private int minWidth;
    private int offSetX;
    private int offSetY;
    private Callout callout;
    private View contentView;
    private Callout.ShowOptions showOptions;
    private Callout.Style.LeaderPosition leaderPosition = Callout.Style.LeaderPosition.LOWER_MIDDLE;

    public static GisCalloutMgr instance (Callout callout, Context context, String title) {
        TextView textView = new TextView(context);
        textView.setText(title);
        int paddingHor = DisplayUtil.dip2px(context, 10);
        textView.setPadding(paddingHor, 0, paddingHor, 0);
        return new GisCalloutMgr(callout, textView);
    }


    public GisCalloutMgr(Callout callout, View view) {
        this.callout = callout;
        this.contentView = view;
    }

    public GisCalloutMgr offSet(int offSetX, int offSetY) {
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        return this;
    }

    public GisCalloutMgr leaderPosition(Callout.Style.LeaderPosition position) {
        this.leaderPosition = position;
        return this;
    }

    public GisCalloutMgr backgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public GisCalloutMgr borderColor(int color) {
        this.borderColor = color;
        return this;
    }

    public GisCalloutMgr maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public GisCalloutMgr maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public GisCalloutMgr minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public GisCalloutMgr minWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public GisCalloutMgr cornerRadius(int corner) {
        this.cornerRadius = corner;
        return this;
    }

    public GisCalloutMgr borderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public GisCalloutMgr leaderLength(int leaderLength) {
        this.leaderLength = leaderLength;
        return this;
    }

    public GisCalloutMgr leaderWidth(int leaderWidth) {
        this.leaderWidth = leaderWidth;
        return this;
    }

    public GisCalloutMgr showOptions(Callout.ShowOptions showOptions) {
        this.showOptions = showOptions;
        return this;
    }

    public GisCalloutMgr outSideTouchDismiss(GisMapView gisMapView) {
        gisMapView.addSingleTapListener(new GisMapView.SingleTapListener() {
            @Override
            public boolean onSingleTap(MotionEvent p) {
                callout.dismiss();
                gisMapView.removeSingleTapListener(this);
                return false;
            }
        });
        return this;
    }

    public void show(Point centerPoint) {
        Callout.Style calloutStyle = new Callout.Style(contentView.getContext());
        calloutStyle.setLeaderPosition(leaderPosition);
        calloutStyle.setBackgroundColor(backgroundColor);
        calloutStyle.setBorderColor(borderColor);
        calloutStyle.setCornerRadius(cornerRadius);
        calloutStyle.setBorderWidth(borderWidth);
        calloutStyle.setLeaderLength(leaderLength);
        calloutStyle.setLeaderWidth(leaderWidth);
        calloutStyle.setMaxHeight(maxHeight);
        calloutStyle.setMaxWidth(maxWidth);
        calloutStyle.setMinHeight(minHeight);
        calloutStyle.setMinWidth(minWidth);
        callout.setStyle(calloutStyle);
        if (offSetX != 0 || offSetY != 0) {
            centerPoint = new Point(centerPoint.getX() - offSetX, centerPoint.getY() - offSetY, centerPoint.getSpatialReference());
        }

        if (showOptions != null) {
            callout.setShowOptions(showOptions);
        }

        callout.show(contentView, centerPoint);
    }

}
