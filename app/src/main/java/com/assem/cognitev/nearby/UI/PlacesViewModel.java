package com.assem.cognitev.nearby.UI;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Data.PlacesClient;
import com.assem.cognitev.nearby.Models.Temp.Item;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {

    private final String TAG = PlacesViewModel.class.getSimpleName();

    MutableLiveData<ArrayList<Item>> itemsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isEmptyMutableLiveData = new MutableLiveData<>();

    public void getVenues(Location location) {
        PlacesClient
                .getClient()
                .getVenues(location)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JsonArray jsonArray = response.body()
                                    .getAsJsonObject(AppConfig.RESPONSE)
                                    .getAsJsonArray(AppConfig.GROUPS).get(0).getAsJsonObject()
                                    .getAsJsonArray(AppConfig.ITEMS);
                            ArrayList<Item> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Item>>() {
                            }.getType());
                            itemsMutableLiveData.setValue(items);
                            if (jsonArray.size() == 0)
                                isEmptyMutableLiveData.setValue(true);
                            else
                                isEmptyMutableLiveData.setValue(false);
                        } catch (Exception e) {
                            Log.d(TAG, "onResponse: exception =>" + e.getMessage());
                            isEmptyMutableLiveData.setValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        isEmptyMutableLiveData.setValue(true);
                    }
                });
    }
}
