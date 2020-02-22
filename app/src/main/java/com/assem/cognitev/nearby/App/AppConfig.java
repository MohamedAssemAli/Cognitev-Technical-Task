package com.assem.cognitev.nearby.App;

public class AppConfig {

    // END_POINTS

    // Explore end point
    /*
     https://api.foursquare.com/v2/venues/explore?
     ll=29.978391,30.954928
     &client_id=WIYK400JVZ2MZ2UASKFQFKBRXYNBQZMVIKFHW1ZME43FGSG0
     &client_secret=PKGEAAH4M25CDNU1HSTVCE5U3T5GQIQISZEVG0CVQ5HY22CB
     &v=20200215
     &radius=500
     */

    // Photos end point
    /*
    https://api.foursquare.com/v2/venues/{venue_id}/photos?
    client_id=WIYK400JVZ2MZ2UASKFQFKBRXYNBQZMVIKFHW1ZME43FGSG0
    &client_secret=PKGEAAH4M25CDNU1HSTVCE5U3T5GQIQISZEVG0CVQ5HY22CB
    &v=20200215

     */
    // photo url
    /*
    https://fastly.4sqi.net/img/general/720x540/3871084_dyOWRHNigS0wzBIyA9H91CuoO8JAkmjTCoyU8EFLw4U.jpg
     */

    public static final String BASE_URL = "https://api.foursquare.com/v2/";
    public static final String LL = "ll";
    public static final String CLIENT_ID = "client_id";
    public static final String ID = "WIYK400JVZ2MZ2UASKFQFKBRXYNBQZMVIKFHW1ZME43FGSG0";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String SECRET = "PKGEAAH4M25CDNU1HSTVCE5U3T5GQIQISZEVG0CVQ5HY22CB";
    public static final String VERSION = "v";
    public static final String RADIUS = "radius";
    public static final String RADIUS_VALUE = "500";
    public static final String RESPONSE = "response";
    public static final String GROUPS = "groups";
    public static final String ITEMS = "items";
    public static final String VENUE = "venue";
    public static final String VENUE_ID = "venue_id";
    public static final String PHOTOS = "photos";



}


/*
    @GET("venues/explore")
    public Call<Explore> getPlaces
            (@Query(AppConfig.CLIENT_ID) String clientId,
             @Query(AppConfig.CLIENT_SECRET) String clientSecret,
             @Query(AppConfig.LL) String lat,
             @Query(AppConfig.RADIUS) String radius,
             @Query(AppConfig.V) String date);
 */