package com.assem.cognitev.nearby.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.assem.cognitev.nearby.Models.Temp.Location;

public class PrefManager {

    // LogCat tag
    private static String TAG = PrefManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "NearByApp";
    // Keys
    private static final String KEY_IS_REALTIME = "isRealtime";
    private static final String KEY_LAST_SAVED_LOCATION = "last_saved_location";

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
        editor.putString(KEY_IS_REALTIME, location.toString());
        editor.commit();
    }
}

