package com.assem.cognitev.nearby.UI;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Data.VenuesClient;
import com.assem.cognitev.nearby.Models.Item;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class VenuesViewModel extends ViewModel {

    private final String TAG = VenuesViewModel.class.getSimpleName();

    MutableLiveData<ArrayList<Item>> itemsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isEmptyMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> onErrorMutableLiveData = new MutableLiveData<>();

    public Observable<Response<JsonObject>> getVenues(Location location) {
        Observable<Response<JsonObject>> observable =
                VenuesClient
                        .getClient()
                        .getVenues(location)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(jsonObject -> {
                    try {
                        Log.d(TAG, "getVenues: " + jsonObject.toString());
                        Log.d(TAG, "getVenues: " + jsonObject.body());
                        JsonArray jsonArray = jsonObject.body()
                                .getAsJsonObject(AppConfig.RESPONSE)
                                .getAsJsonArray(AppConfig.GROUPS).get(0).getAsJsonObject()
                                .getAsJsonArray(AppConfig.ITEMS);
                        ArrayList<Item> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Item>>() {
                        }.getType());
                        itemsMutableLiveData.setValue(items);
                        onErrorMutableLiveData.setValue(false);
                        if (jsonArray.size() == 0)
                            isEmptyMutableLiveData.setValue(true);
                        else
                            isEmptyMutableLiveData.setValue(false);
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception =>" + e.getMessage());
                        onErrorMutableLiveData.setValue(true);
                    }
                },
                e -> {
                    onErrorMutableLiveData.setValue(true);
                });
        return observable;
    }

    public Observable<Response<JsonObject>> getVenuePhotos(String venueId) {
        Observable<Response<JsonObject>> observable =
                VenuesClient
                        .getClient()
                        .getVenuePhotos(venueId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(jsonObject -> {
                    try {

                        JsonObject responseObject = jsonObject.body()
                                .getAsJsonObject(AppConfig.RESPONSE)
                                .getAsJsonObject(AppConfig.PHOTOS)
                                .getAsJsonArray(AppConfig.ITEMS).get(0).getAsJsonObject();

                        Log.d(TAG, "getVenuePhotos: " + responseObject.get("suffix"));

//                        ArrayList<Item> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Item>>() {
//                        }.getType());
//                        itemsMutableLiveData.setValue(items);
//                        onErrorMutableLiveData.setValue(false);
//                        if (jsonArray.size() == 0)
//                            isEmptyMutableLiveData.setValue(true);
//                        else
//                            isEmptyMutableLiveData.setValue(false);
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception =>" + e.getMessage());
//                        onErrorMutableLiveData.setValue(true);
                    }
                },
                e -> {
//                    onErrorMutableLiveData.setValue(true);
                });
        return observable;
    }
}


/*
String getToday() {
        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(todayDate).replace("-", "");
    }

 */