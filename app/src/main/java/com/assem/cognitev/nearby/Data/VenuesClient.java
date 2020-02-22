package com.assem.cognitev.nearby.Data;

import android.location.Location;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Models.photos.PhotoRespone;
import com.assem.cognitev.nearby.Models.places.PlacesResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class VenuesClient {

    private VenuesInterface venuesInterface;
    private static VenuesClient INSTANCE;

    public VenuesClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
//                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        venuesInterface = retrofit.create(VenuesInterface.class);
    }

    public static VenuesClient getClient() {
        if (INSTANCE == null) {
            INSTANCE = new VenuesClient();
        }
        return INSTANCE;
    }

    public Observable<PlacesResponse> getVenuesRes(Location location) {
        return venuesInterface
                .getVenuesRes(
                        AppConfig.ID,
                        AppConfig.SECRET,
                        location.getLatitude() + "," + location.getLongitude(),
                        AppConfig.RADIUS_VALUE,
                        "20200215")
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PhotoRespone> getVenuePhotosRes(String venueId) {
        return venuesInterface
                .getVenuePhotosRes(
                        venueId,
                        AppConfig.ID,
                        AppConfig.SECRET,
                        "20200215")
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
