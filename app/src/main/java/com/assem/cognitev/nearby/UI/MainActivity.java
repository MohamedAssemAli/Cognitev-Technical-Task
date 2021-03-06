package com.assem.cognitev.nearby.UI;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.App.MyApplication;
import com.assem.cognitev.nearby.Helper.PrefManager;
import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.BuildViews;
import com.assem.cognitev.nearby.Utils.ConnectivityReceiver;
import com.assem.cognitev.nearby.Utils.LocationUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private final String TAG = MainActivity.class.getSimpleName();
    // instances
    private BuildViews buildViews;
    private VenuesAdapter venuesAdapter;
    private VenuesViewModel venuesViewModel;
    // pref instances
    private PrefManager prefManager;
    // location module
    private LocationUtil locationUtil;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
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
        locationCallback = new LocationCallback();
        locationRequest = new LocationRequest();
        // setup recyclerView
        buildViews.setupLinearVerticalRecView(placesRecyclerView, this);
        placesRecyclerView.setAdapter(venuesAdapter);
        disposable = new CompositeDisposable();
        locationUtil = new LocationUtil(this, fusedLocationClient, locationRequest);
        // setup viewModel
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
        venuesViewModel.isPermissionGranted.observe(this, aBoolean -> {
            if (aBoolean)
                venuesViewModel.initLocationService(locationUtil);
            else
                Toast.makeText(MainActivity.this, "Permissions not granted", Toast.LENGTH_LONG).show();
        });
        venuesViewModel.isLocationEnabled.observe(this, aBoolean -> {
            if (!aBoolean)
                Toast.makeText(MainActivity.this, getResources().getString(R.string.enable_gps), Toast.LENGTH_LONG).show();
        });
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
                startRealtimeMode();
                return true;
            case R.id.menu_item_single_update:
                startSingleUpdateMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startRealtimeMode() {
        Toast.makeText(this, "Realtime mode started!", Toast.LENGTH_LONG).show();
        prefManager.setRealtime(true);
        venuesViewModel.onChangeModeListener(true);
        locationRequest.setSmallestDisplacement(500);
        toggleLayout(true);
    }

    private void startSingleUpdateMode() {
        Toast.makeText(this, "Single update mode started!", Toast.LENGTH_LONG).show();
        prefManager.setRealtime(false);
        venuesViewModel.onChangeModeListener(false);
        if (locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
        locationRequest.setNumUpdates(1);
        toggleLayout(true);
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
