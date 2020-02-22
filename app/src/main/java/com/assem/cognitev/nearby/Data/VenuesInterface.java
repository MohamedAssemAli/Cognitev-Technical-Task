package com.assem.cognitev.nearby.Data;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.photos.PhotoRespone;
import com.assem.cognitev.nearby.Models.places.PlacesResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VenuesInterface {

    /*
https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5&client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ&v=20200215
 */


    /*
    https://api.foursquare.com/v2/venues/{venue_id}/photos?
    client_id=KJ5KJ0Q4GBFAR5MFM2DP0BTH0CAGOBGFSAXI034VXN5N5B1S
    &client_secret=2CSX2PIGUL0HJUQKV0KSDZKAU2P2KKUJD4SIXGMTJJVCF3L5
    &v=20200215

     */



    @GET("venues/explore")
    public Single<PlacesResponse> getVenuesRes
            (@Query(AppConfig.CLIENT_ID) String clientId,
             @Query(AppConfig.CLIENT_SECRET) String clientSecret,
             @Query(AppConfig.LL) String location,
             @Query(AppConfig.RADIUS) String radius,
             @Query(AppConfig.VERSION) String version);

    @GET("venues/{venue_id}/photos")
    public Single<PhotoRespone> getVenuePhotosRes
            (@Path(AppConfig.VENUE_ID) String venueId,
             @Query(AppConfig.CLIENT_ID) String clientId,
             @Query(AppConfig.CLIENT_SECRET) String clientSecret,
             @Query(AppConfig.VERSION) String version);


}
