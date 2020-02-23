package com.assem.cognitev.nearby.UI;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assem.cognitev.nearby.App.AppConfig;
import com.assem.cognitev.nearby.Data.VenuesClient;
import com.assem.cognitev.nearby.Models.Photos.VenuePhoto;
import com.assem.cognitev.nearby.Models.Responses.places.Item;
import com.assem.cognitev.nearby.Models.Responses.places.PlacesResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableAll;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class VenuesViewModel extends ViewModel {

    private final String TAG = VenuesViewModel.class.getSimpleName();

    MutableLiveData<ArrayList<Item>> itemsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isEmptyMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> onErrorMutableLiveData = new MutableLiveData<>();

    MutableLiveData<com.assem.cognitev.nearby.Models.Responses.places.Item> itemsMutableLiveDataRes = new MutableLiveData<com.assem.cognitev.nearby.Models.Responses.places.Item>();

    // RX vars
    private CompositeDisposable disposable = new CompositeDisposable();
    private ArrayList<Item> items;
    private ArrayList<Item> items_;
    private ArrayList<Response> itemsRes;
    private VenuePhoto venuePhoto;
    ConnectableObservable<ArrayList<Item>> venuesObservable;
    ConnectableObservable<Response<JsonObject>> venuesObservableRes;

    // New Rx Methods
//    public void getVenues_(Location location) {
//        venuesObservable = VenuesClient.getClient().getVenues_(location).replay();
//        /**
//         * Fetching all places first
//         * Observable emits ArrayList<Item> at once
//         * */
//        disposable.add(
//                venuesObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ArrayList<Item>>() {
//                            @Override
//                            public void onNext(ArrayList<Item> items) {
//                                // Refreshing list
//                                items_.addAll(items);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: venuesObservable =>", e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: ");
//                            }
//                        })
//
//        );
//
//        /**
//         * Fetching individual venue photo
//         * First FlatMap converts single ArrayList<Item> to multiple emissions
//         * Second FlatMap makes HTTP call on each item emission
//         * */
//        disposable.add(
//                venuesObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        /**
//                         * Converting List<Ticket> emission to single Ticket emissions
//                         * */
//                        .flatMap(new Function<ArrayList<Item>, ObservableSource<Item>>() {
//                            @Override
//                            public ObservableSource<Item> apply(ArrayList<Item> items) throws Exception {
//                                return ObservableAll.fromIterable(items);
//                            }
//                        })
//                        /**
//                         * Fetching price on each Ticket emission
//                         * */
//                        .flatMap(new Function<Item, ObservableSource<Item>>() {
//                            @Override
//                            public ObservableSource<Item> apply(Item item) throws Exception {
//                                return VenuesClient.getClient().getVenuePhotos_(item);
//                            }
//                        })
//                        .subscribeWith(new DisposableObserver<Item>() {
//                            @Override
//                            public void onNext(Item item) {
//                                int position = items.indexOf(item);
//
//                                if (position == -1) {
//                                    // TODO - take action
//                                    // Ticket not found in the list
//                                    // This shouldn't happen
//                                    return;
//                                }
//
//                                items_.set(position, item);
////                                itemsMutableLiveData.setValue(items);
//                                Log.d(TAG, "onNext: " + item.getVenuePhoto().getSuffix());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: ", e);
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        })
//        );
//        // Calling connect to start emission
//        venuesObservable.connect();
//    }


    // test
    private final MutableLiveData<List<com.assem.cognitev.nearby.Models.Responses.places.Item>> places = new MutableLiveData<>();
    private final MutableLiveData<Throwable> loadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFirstRequest = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPermissionGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLocationEnabled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRealTime = new MutableLiveData<>();
    private final MutableLiveData<com.assem.cognitev.nearby.Models.Responses.places.Item> updatedPlace = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();


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
        Log.d(TAG, "onError: " , throwable);
    }

    public void onSuccess(com.assem.cognitev.nearby.Models.Responses.places.Item item) {
        updatedPlace.setValue(item);
        Log.d(TAG, "onSuccess: item " + item.getPlace().getName());
        Log.d(TAG, "onSuccess: item " + item.getPlace().getPhotoRespone().getPhotoUrl());
        isEmptyMutableLiveData.setValue(false);
        onErrorMutableLiveData.setValue(false);
    }


    public Observable<com.assem.cognitev.nearby.Models.Responses.places.Item>
    getPhotoObservable(com.assem.cognitev.nearby.Models.Responses.places.Item place) {
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