package com.assem.cognitev.nearby.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

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

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void setRealtime(boolean isRealtime) {
        editor.putBoolean(KEY_IS_REALTIME, isRealtime);
        editor.commit();
    }

    public boolean isRealtime() {
        return pref.getBoolean(KEY_IS_REALTIME, true);
    }

    public void setLastSavedLocation(Location location) {
        Gson gson = new Gson();
        String json = gson.toJson(location);
        editor.putString(KEY_LAST_SAVED_LOCATION, json);
        editor.commit();
    }

    public Location getLastSavedLocation() {
        String json = pref.getString(KEY_LAST_SAVED_LOCATION, "");
        return gson.fromJson(json, Location.class);
    }
}

