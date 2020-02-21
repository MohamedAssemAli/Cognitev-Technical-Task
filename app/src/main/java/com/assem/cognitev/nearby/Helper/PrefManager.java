package com.assem.cognitev.nearby.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

public class PrefManager {
    private static String TAG = PrefManager.class.getSimpleName();
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;  // Shared pref mode
    private int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "NearByApp";
    // Keys
    private final String KEY_IS_REALTIME = "is_realtime";
    private final String KEY_LAST_SAVED_LOCATION = "last_saved_location";
    private final String KEY_LAST_SAVED_LOCATION_LAT = "KEY_LAST_SAVED_LOCATION_LAT";
    private final String KEY_LAST_SAVED_LOCATION_LON = "KEY_LAST_SAVED_LOCATION_LON";

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setRealtime(boolean isRealtime) {
        editor.putBoolean(KEY_IS_REALTIME, isRealtime);
        editor.commit();
    }

    public boolean isRealtime() {
        return pref.getBoolean(KEY_IS_REALTIME, true);
    }

    public void setLastSavedLocation(Location location) {
        editor.putString(KEY_LAST_SAVED_LOCATION_LAT, String.valueOf(location.getLatitude()));
        editor.putString(KEY_LAST_SAVED_LOCATION_LON, String.valueOf(location.getLongitude()));
        editor.commit();
    }

    public Location getLastSavedLocation() {
        Location location = new Location("");
        try {
            double lat = Double.parseDouble(pref.getString(KEY_LAST_SAVED_LOCATION_LAT, ""));
            double lon = Double.parseDouble(pref.getString(KEY_LAST_SAVED_LOCATION_LON, ""));
            location.setLatitude(lat);
            location.setLongitude(lon);
        } catch (Exception e) {
            Log.d(TAG, "getLastSavedLocation: exception =>" + e.getMessage());
        }
        return location;
    }
}

