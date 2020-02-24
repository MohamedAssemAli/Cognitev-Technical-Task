package com.assem.cognitev.nearby.UI;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.App.MyApplication;
import com.assem.cognitev.nearby.Helper.PrefManager;
import com.assem.cognitev.nearby.Models.places.Item;
import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.BuildViews;
import com.assem.cognitev.nearby.Utils.ConnectivityReceiver;
import com.assem.cognitev.nearby.Utils.LocationUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private final String TAG = MainActivity.class.getSimpleName();
    // instances
    private BuildViews buildViews;
    private VenuesAdapter venuesAdapter;
    private VenuesViewModel venuesViewModel;
    // pref instances
    private PrefManager prefManager;
    // location module
    private LocationUtil locationUtil;
    private final int RC_LOCATION_PERM = 124;
    private String[] permissionsList = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private long UPDATE_INTERVAL = 10 * 6000;  /* 60 secs */
    private long FASTEST_INTERVAL = 10 * 3000; /* 6 sec */
    private CompositeDisposable disposable;
    // vars
    private boolean isEmpty = false;
    private boolean onRequestError = false;
    // Views
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_activity_places_recycler)
    RecyclerView placesRecyclerView;
    @BindView(R.id.no_data_found_layout)
    LinearLayout noDataFoundLayout;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.something_went_wrong_layout)
    LinearLayout somethingWrongLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        init();

        Log.d(TAG, "onCreate: Location => " + prefManager.getLastSavedLocation().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver(this);
        registerReceiver(connectivityReceiver, intentFilter);
        /*register connection status listener*/
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void init() {
        // show progressBar on app start
        toggleLayout(false);
        // Initialize instances
        prefManager = new PrefManager(this);
        buildViews = new BuildViews();
        venuesAdapter = new VenuesAdapter(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // setup recyclerView
        buildViews.setupLinearVerticalRecView(placesRecyclerView, this);
        placesRecyclerView.setAdapter(venuesAdapter);
        disposable = new CompositeDisposable();
        locationUtil = new LocationUtil(this, fusedLocationClient);
        //Inside MyActivity
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new VenuesViewModel(locationUtil, disposable);
            }
        };
        venuesViewModel = ViewModelProviders.of(this, factory).get(VenuesViewModel.class);
        venuesViewModel.setMode(prefManager.isRealtime());
        venuesViewModel.checkLocationPermissions(new RxPermissions(this));

        venuesViewModel.isPermissionGranted.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    venuesViewModel.initLocationService(locationUtil);
                else
                    Toast.makeText(MainActivity.this, "Permissions not granted", Toast.LENGTH_LONG).show();
            }
        });
//
//        if (venuesViewModel.isPermissionGranted())
//            venuesViewModel.initLocationService(locationUtil);
//        else
//            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_LONG).show();

        venuesViewModel.isLocationEnabled.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean)
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enable_gps), Toast.LENGTH_LONG).show();
            }
        });

//        // get user location
//        getLocation();
//
//        // get user pref
//        if (prefManager.isRealtime()) {
//            // do Realtime mode work
//            startLocationChangeListener();
//        } else {
//            // do single update mode work
//        }


        // setup viewModel
        venuesViewModel.places.observe(this, items -> {
            if (items != null) {
                venuesAdapter.setList(items);
                toggleLayout(true);
            }
        });

        venuesViewModel.updatedPlace.observe(this, item -> {
            if (item != null) {
                Log.d(TAG, "onChanged: " + item.getPlace().getPhotoResponse().getPhotoUrl());
                venuesAdapter.updatePlace(item);
                toggleLayout(true);
            }
        });

        venuesViewModel.isEmptyMutableLiveData.observe(this, aBoolean -> {
            Log.d(TAG, "init: isEmpty => " + aBoolean);
            isEmpty = aBoolean;
            toggleLayout(true);
        });

        venuesViewModel.onErrorMutableLiveData.observe(this, aBoolean -> {
            Log.d(TAG, "init: isEmpty => " + aBoolean);
            onRequestError = aBoolean;
            toggleLayout(true);
        });
    }

    // get current user location
//    private void getLocation() {
//        if (hasPermissions()) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, location -> {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            Log.d(TAG, "getLocation: location =>" + location.getLatitude() + " - " + location.getLongitude());
//                            getNearByVenues(location);
//                            prefManager.setLastSavedLocation(location);
//                        } else {
//                            isEmpty = true;
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
//            // Ask for one permission
//            EasyPermissions.requestPermissions(
//                    this,
//                    getString(R.string.rationale_location),
//                    RC_LOCATION_PERM,
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//            isEmpty = true;
//        }
//    }

    // call ViewModel actions
    private void getNearByVenues(Location location) {
        venuesViewModel.fetchPlaces(location);
    }

    private void startLocationChangeListener() {
        locationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    Log.d(TAG, "onLocationResult: new location =>" + location.getLatitude() + " - " + location.getLongitude());
                    Location lastSaveLocation = prefManager.getLastSavedLocation();
                    if (lastSaveLocation != null)
                        if (isDistanceChanged(lastSaveLocation, location)) {
                            prefManager.setLastSavedLocation(location);
                        }
                    getNearByVenues(location);
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

    }

    private boolean isDistanceChanged(Location oldLocation, Location newLocation) {
        return oldLocation.distanceTo(newLocation) >= 500;
    }

    // handle RunTimePermissions
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, permissionsList);
    }

    // EasyPermissions callbacks
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    // inflate mainMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // handle mainMenu callBacks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_realtime:
                Toast.makeText(this, "Realtime mode started!", Toast.LENGTH_LONG).show();
                prefManager.setRealtime(true);
                startLocationChangeListener();
                return true;
            case R.id.menu_item_single_update:
                Toast.makeText(this, "Single update mode started!", Toast.LENGTH_LONG).show();
                prefManager.setRealtime(false);
                if (locationCallback != null)
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                Location lastSaveLocation = prefManager.getLastSavedLocation();
                getNearByVenues(lastSaveLocation);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // handle data retrieving & rendering / no data retrieved
    private void toggleLayout(boolean flag) {
        if (flag) {
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
            if (onRequestError) {
                somethingWrongLayout.setVisibility(View.VISIBLE);
                placesRecyclerView.setVisibility(View.GONE);
            } else {
                somethingWrongLayout.setVisibility(View.GONE);
                placesRecyclerView.setVisibility(View.VISIBLE);
            }
            if (isEmpty) {
                noDataFoundLayout.setVisibility(View.VISIBLE);
                placesRecyclerView.setVisibility(View.GONE);
            } else {
                noDataFoundLayout.setVisibility(View.GONE);
                placesRecyclerView.setVisibility(View.VISIBLE);
            }

        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
        }
    }

    // Checking internet connection

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        isConnected(isConnected);
    }

    // handle network status changing
    private void isConnected(boolean isConnected) {
        if (isConnected) {
            somethingWrongLayout.setVisibility(View.GONE);
            Log.d(TAG, "onNetworkConnectionChanged: true");
        } else {
            Toast.makeText(this, R.string.check_your_connection, Toast.LENGTH_LONG).show();
            Log.d(TAG, "onNetworkConnectionChanged: No network connection!");
            somethingWrongLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
