package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.PlaceModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceInterface {


    @GET(AppConfig.USERS)
    public Call<PlaceModel> getPlaces();
}
