package com.assem.cognitev.nearby.App;

public class AppConfig {

    // END_POINTS
    /*
https://api.foursquare.com/v2/venues/search?ll=29.978391, 30.954928&client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5&client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZhttps://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5&client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ&v=20200215&radius=500

     https://api.foursquare.com/v2/venues/explore?ll=29.978391,30.954928
     &client_id=QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5
     &client_secret=JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ
     &v=20200215
     &radius=500
     */

    public static final String BASE_URL = "https://api.foursquare.com/v2/";
    public static final String LL = "ll";
    public static final String CLIENT_ID = "client_id";
    public static final String ID = "QI3JN03II2AEFXVUZWUWTDLUWBTRSHXJLPJDPWZ0V0QY2DA5";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String SECRET = "JI23SHUX4JYSPJNLKDMYTZFEVSLBTX2KKVYVSTJKFBFIQKGZ";
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