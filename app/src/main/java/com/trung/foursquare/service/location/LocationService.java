package com.trung.foursquare.service.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.trung.foursquare.MainApplication;

public class LocationService {
    private static LocationService sInstance;

    private LocationManager mLocationManager;

    private LocationService(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationService getInstance() {
        if (sInstance == null) {
            sInstance = new LocationService(MainApplication.getInstance());
        }
        return sInstance;
    }

    public Location getLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            return null;
        }
        if (isNetworkEnabled) {
            try {
                return mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (SecurityException exception) {
                exception.printStackTrace();
            }

        }
        try {
            return mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
