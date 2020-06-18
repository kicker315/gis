package com.zydcc.zrdc.location;

import android.app.Service;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * =======================================
 * GPS定位服务
 * Create by ningsikai 2020/6/18:11:08 AM
 * ========================================
 */
public class GpsLocationService extends Service
    implements LocationListener, GpsStatus.NmeaListener {

    private boolean blueConnect = false;
    private float heading = 0.0f;
    private double latitude = 0.0d;
    private double longitude = 0.0d;
    private LocationClient bdLocationClient = null;
    private LocationClientOption bdOption;
    private boolean bt;
    private OutputStream btDos;
    private boolean completeLine;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onNmeaReceived(long timestamp, String nmea) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInputStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        public ConnectedThread(BluetoothSocket socket) {
            this.mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInputStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }


    }
}
