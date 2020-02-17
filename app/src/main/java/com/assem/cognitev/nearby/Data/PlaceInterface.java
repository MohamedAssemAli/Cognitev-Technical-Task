package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.PlaceModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceInterface {


    @GET(AppConfig.PLACES)
    public Call<ArrayList<PlaceModel>> getPlaces();

    /*
https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5&client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ&v=20200215
 */
    @GET("search")
    Call<String> getResponse(@Query(AppConfig.LL) String lat,
                             @Query(AppConfig.CLIENT_ID) String clientId,
                             @Query(AppConfig.CLIENT_SECRET) String secret,
                             @Query(AppConfig.V) String date);
/*

    @GET(".")
    Call<String> getResponse(@Query(AppConfig.LL) String lat,
                             @Query(AppConfig.CLIENT_ID) String lon,
                             @Query(AppConfig.SECRET) String appId,
                             @Query(AppConfig.V) String units);

 */

}
