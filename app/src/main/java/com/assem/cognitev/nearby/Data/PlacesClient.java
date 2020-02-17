package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.PlaceModel;

import java.util.ArrayList;
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

    public Call<ArrayList<PlaceModel>> getPlaces(){
        return placeInterface.getPlaces();
    }

        /*
    https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5&client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ&v=20200215
     */

    public Call<String> getResponse(){
        return placeInterface.getResponse("40.7,-74","QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5","JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ", "20200215");
    }
}
