package com.assem.cognitev.nearby.Ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.Data.PlacesClient;
import com.assem.cognitev.nearby.Models.PlaceModel;
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
    
    public void getVenues() {
        PlacesClient.getClient().getV().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArray = response.body()
                        .getAsJsonObject("response")
                        .getAsJsonArray("groups").get(0).getAsJsonObject()
                        .getAsJsonArray("items");

                ArrayList<Item> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Item>>() {
                }.getType());
                itemsMutableLiveData.setValue(items);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
