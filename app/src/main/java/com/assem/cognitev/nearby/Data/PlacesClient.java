package com.assem.cognitev.nearby.Data;

import android.location.Location;

import com.assem.cognitev.nearby.App.AppConfig;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesClient {

    private PlaceInterface placeInterface;
    private static PlacesClient INSTANCE;

    public PlacesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
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


    public Call<String> getResponse() {
        return placeInterface.getResponse("40.7,-74", "QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5", "JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ", "20200215");
    }
}
