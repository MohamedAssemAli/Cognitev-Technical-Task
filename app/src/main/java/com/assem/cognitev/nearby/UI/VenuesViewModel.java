package com.assem.cognitev.nearby.UI;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.Data.VenuesClient;
import com.assem.cognitev.nearby.Models.places.Item;
import com.assem.cognitev.nearby.Models.places.PlacesResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VenuesViewModel extends ViewModel {

    private final String TAG = VenuesViewModel.class.getSimpleName();

    MutableLiveData<ArrayList<Item>> itemsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isEmptyMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> onErrorMutableLiveData = new MutableLiveData<>();

    // RX vars
    private CompositeDisposable disposable = new CompositeDisposable();

    // New Rx Methods

    // test
    public final MutableLiveData<List<Item>> places = new MutableLiveData<>();
    private final MutableLiveData<Throwable> loadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFirstRequest = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPermissionGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLocationEnabled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRealTime = new MutableLiveData<>();
    private final MutableLiveData<Item> updatedPlace = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();

    public void fetchPlaces(Location location) {
        disposable.add(placesResponseObservable(location)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(PlacesResponse::getItems)
                .flatMap(this::getPhotoObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));
    }

    public void onError(Throwable throwable) {
        onErrorMutableLiveData.setValue(true);
        loadError.postValue(throwable);
        isFirstRequest.postValue(false);
        onErrorMutableLiveData.setValue(true);
        Log.d(TAG, "onError: "+ throwable.getMessage());
        Log.d(TAG, "onError: "+ throwable.getCause());
    }

    public void onSuccess(Item item) {
        updatedPlace.setValue(item);
        isEmptyMutableLiveData.setValue(false);
        onErrorMutableLiveData.setValue(false);
        Log.d(TAG, "onSuccess: item" + item.getPlace().getName());
        Log.d(TAG, "onSuccess: item" + item.getPlace().getPhotoRespone().getPhotoUrl());
    }

    public Observable<Item>
    getPhotoObservable(Item place) {
        return VenuesClient.getClient().getVenuePhotosRes(place.getPlace().getId())
                .map(photoRespone -> {
                    place.getPlace().setPhotoRespone(photoRespone);
                    return place;
                })
                .subscribeOn(Schedulers.io());
    }


    private void showPlaces(PlacesResponse response) {
        places.postValue(response.getResponse().getGroups().get(0).getItems());
        isFirstRequest.postValue(false);
    }

    private Observable<PlacesResponse> placesResponseObservable(Location location) {
        return VenuesClient.getClient().getVenuesRes(location)
                .map(placesResponse -> {
                    showPlaces(placesResponse);
                    return placesResponse;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}



/*
String getToday() {
        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(todayDate).replace("-", "");
    }

 */