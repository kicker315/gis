package com.zydcc.zrdc.location;

import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.location.LocationDataSource.Location;

/**
 * =======================================
 *
 * Create by ningsikai 2020/6/18:11:02 AM
 * ========================================
 */
public class MyLocationDataSource
        extends LocationDataSource
{
    public void UpdateLocation(Location location)
    {
        updateLocation(location);
    }

    protected void onStart()
    {
        onStartCompleted(null);
    }

    protected void onStop() {}
}
