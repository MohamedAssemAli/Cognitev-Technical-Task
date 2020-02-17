package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.PlaceModel;

import java.util.List;

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

    public Call<List<PlaceModel>> getPlaces(){
        return placeInterface.getPlaces();
    }
}
