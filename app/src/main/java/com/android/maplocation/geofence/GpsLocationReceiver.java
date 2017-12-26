package com.android.maplocation.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @author Sachin Narang
 */

public class GpsLocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Intent intentGps = new Intent("gpsDetectionIntent");
            intentGps.putExtra("isGpsEnabled", isGpsEnabled(context));
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentGps);
        }
    }

    private boolean isGpsEnabled(Context context) {


        boolean gpsEnabled = false;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager != null)
                gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gpsEnabled;
    }
}
