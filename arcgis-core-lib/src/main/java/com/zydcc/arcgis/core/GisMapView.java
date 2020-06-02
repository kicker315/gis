package com.zydcc.arcgis.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/2:9:43 AM
 * ========================================
 */
public class GisMapView extends FrameLayout {

    private static final String MAP_LICENSE = "runtimeadvanced,1000,rud000228325,none,3M10F7PZB0YH463EM164";
    public static final  int    TYPE_BASETILED_VEC = 0;//普通地图
    public static final  int    TYPE_BASETILED_IMG = 1;//影像地图

    public GisMapView(@NonNull Context context) {
        super(context);
    }

    public GisMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GisMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GisMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
