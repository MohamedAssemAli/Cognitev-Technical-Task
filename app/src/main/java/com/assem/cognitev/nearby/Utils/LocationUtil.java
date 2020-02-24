package com.assem.cognitev.nearby.Utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class LocationUtil {

    private final String TAG = LocationUtil.class.getSimpleName();

    // location module
    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationListener locationListener;
    private long UPDATE_INTERVAL = 10 * 3000;  /* 60 secs */
    private long FASTEST_INTERVAL = 10 * 3000; /* 30 sec */

    public LocationUtil(Context context, FusedLocationProviderClient fusedLocationClient) {
        this.context = context;
        this.fusedLocationClient = fusedLocationClient;
    }


    public void setLocationCallBacks(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public void removeCurrentUpdate() {
        if (locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationListener != null)
                    locationListener.onLocationChanged(locationResult.getLastLocation());
            }
        };
    }

    public Boolean isLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // This is Deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);
        }
    }

    public void requestLocation(Boolean isRealTime) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (isRealTime) {
            // Realtime mode
            mLocationRequest.setSmallestDisplacement(500);
        } else
            // Single mode
            mLocationRequest.setNumUpdates(1);

        mLocationRequest
                .setFastestInterval(FASTEST_INTERVAL)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        createLocationCallback();
        fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
    }

    private boolean isDistanceChanged(Location oldLocation, Location newLocation) {
        return oldLocation.distanceTo(newLocation) >= 500;
    }

    public interface LocationListener {
        void onLocationChanged(Location location);
    }
}
