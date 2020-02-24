package com.assem.cognitev.nearby.UI;


import android.Manifest;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.Data.VenuesClient;
import com.assem.cognitev.nearby.Helper.PrefManager;
import com.assem.cognitev.nearby.Models.places.Item;
import com.assem.cognitev.nearby.Models.places.PlacesResponse;
import com.assem.cognitev.nearby.Utils.LocationUtil;
import com.blankj.utilcode.util.NetworkUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VenuesViewModel extends ViewModel
        implements LocationUtil.LocationListener {

    private final String TAG = VenuesViewModel.class.getSimpleName();
    private LocationUtil locationUtil;
    private PrefManager prefManager;
    MutableLiveData<Boolean> isEmptyMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> onErrorMutableLiveData = new MutableLiveData<>();
    // test
    public final MutableLiveData<List<Item>> places = new MutableLiveData<>();
    private final MutableLiveData<Throwable> loadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFirstRequest = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isPermissionGranted = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLocationEnabled = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRealTime = new MutableLiveData<>();
    public final MutableLiveData<Item> updatedPlace = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();


    // RX vars
    private CompositeDisposable disposable;

    public VenuesViewModel(LocationUtil locationUtil) {
        disposable = new CompositeDisposable();
        this.locationUtil = locationUtil;
    }


    /**
     * Complex situation , we need to fetch photo for each place
     * flatmap operator solve the problem when we have  request depend on the result of the anther one
     * //
     */

    public void fetchPlaces(Location location) {
        disposable.add(placesResponseObservable(location)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(PlacesResponse::getItems)
                .flatMap(this::getPhotoObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError));
    }

    public void onError(Throwable throwable) {
        loadError.postValue(throwable);
        isFirstRequest.postValue(false);
        Log.d(TAG, "onError: ", throwable);
    }

    public void onSuccess(Item item) {
        updatedPlace.setValue(item);
        Log.d(TAG, "onSuccess: item " + item.getPlace().getName());
//        Log.d(TAG, "onSuccess: item " + item.getPlace().getPhotoResponse().getPhotoUrl());
        isEmptyMutableLiveData.setValue(false);
        onErrorMutableLiveData.setValue(false);
    }


    public Observable<Item>
    getPhotoObservable(Item place) {
        return VenuesClient.getClient().getVenuePhotosRes(place.getPlace().getId())
                .map(photoRespone -> {
                    place.getPlace().setPhotoResponse(photoRespone);
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


    public boolean isRealTIme() {
        return isRealTime.getValue();
    }

    public boolean isLocationEnabled() {
        return isLocationEnabled.getValue();
    }

    boolean isPermissionGranted() {
        return isPermissionGranted.getValue();
    }

    void initLocationService(LocationUtil locationUtil) {
        requestLocationUpdates();
    }

    public void setMode(Boolean mode) {

        isRealTime.setValue(mode);
    }

    public void checkLocationPermissions(RxPermissions rxPermissions) {
        isLocationEnabled.setValue(locationUtil.isLocationEnabled());
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(isPermissionGranted::setValue);
    }


    public void onChangeModeListener(Boolean isChecked) {
        isRealTime.setValue(isChecked);
        locationUtil.removeCurrentUpdate();
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (isLocationEnabled.getValue())
            locationUtil.requestLocation(isRealTime.getValue());
        else
            isFirstRequest.setValue(false);
        locationUtil.setLocationCallBacks(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        isConnected.setValue(NetworkUtils.isConnected());
        if (isConnected.getValue())
            fetchPlaces(location);
        else
            isFirstRequest.setValue(false);
    }
}