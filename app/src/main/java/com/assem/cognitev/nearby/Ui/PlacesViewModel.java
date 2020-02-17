package com.assem.cognitev.nearby.Ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.Data.PlacesClient;
import com.assem.cognitev.nearby.Models.PlaceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {

    MutableLiveData<ArrayList<PlaceModel>> placesMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> posts = new MutableLiveData<>();

    public void getPosts() {
        PlacesClient.getClient().getPlaces().enqueue(new Callback<ArrayList<PlaceModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PlaceModel>> call, Response<ArrayList<PlaceModel>> response) {
                placesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<PlaceModel>> call, Throwable t) {
                posts.setValue("errr");
            }
        });
    }
}
