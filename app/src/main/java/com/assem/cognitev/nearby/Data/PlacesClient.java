package com.assem.cognitev.nearby.Data;

import android.location.Location;

import com.assem.cognitev.nearby.App.AppConfig;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesClient {

    private PlaceInterface placeInterface;
    private static PlacesClient INSTANCE;

    public PlacesClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        placeInterface = retrofit.create(PlaceInterface.class);
    }

    public static PlacesClient getClient() {
        if (INSTANCE == null) {
            INSTANCE = new PlacesClient();
        }
        return INSTANCE;
    }

    public Call<JsonObject> getVenues(Location location) {
        return placeInterface
                .getVenues(
                        AppConfig.ID,
                        AppConfig.SECRET,
                        location.getLatitude() + "," + location.getLongitude(),
                        "500",
                        "20200215");
    }
}
