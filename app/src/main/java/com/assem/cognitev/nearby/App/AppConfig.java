package com.assem.cognitev.nearby.App;

public class AppConfig {

    // END_POINTS

    // Explore end point
    /*
     https://api.foursquare.com/v2/venues/explore?
     ll=29.978391,30.954928
     &client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5
     &client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ
     &v=20200215
     &radius=500
     */

    // Photos end point
    /*
    https://api.foursquare.com/v2/venues/{venue_id}/photos?
    client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5
    &client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ
    &v=20200215

     */
    // photo url
    /*
    https://fastly.4sqi.net/img/general/720x540/3871084_dyOWRHNigS0wzBIyA9H91CuoO8JAkmjTCoyU8EFLw4U.jpg
     */

    public static final String BASE_URL = "https://api.foursquare.com/v2/";
    public static final String LL = "ll";
    public static final String CLIENT_ID = "client_id";
    public static final String ID = "KJ5KJ0Q4GBFAR5MFM2DP0BTH0CAGOBGFSAXI034VXN5N5B1S";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String SECRET = "2CSX2PIGUL0HJUQKV0KSDZKAU2P2KKUJD4SIXGMTJJVCF3L5";
    public static final String V = "v";
    public static final String RADIUS = "radius";

    public static final String RESPONSE = "response";
    public static final String GROUPS = "groups";
    public static final String ITEMS = "items";
    public static final String VENUE = "venue";

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