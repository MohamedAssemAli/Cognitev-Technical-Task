package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.PlaceModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceInterface {


    @GET(AppConfig.PLACES)
    public Call<ArrayList<PlaceModel>> getPlaces();
}
