package com.assem.cognitev.nearby.Ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.App.MyApplication;
import com.assem.cognitev.nearby.Helper.PrefManager;
import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.BuildViews;
import com.assem.cognitev.nearby.Utils.ConnectivityReceiver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private final String TAG = MainActivity.class.getSimpleName();
    // Vars
    private BuildViews buildViews;
    private PlacesAdapter placesAdapter;
    private PlacesViewModel placesViewModel;
    private PrefManager prefManager;
    private final int RC_LOCATION_PERM = 124;
    private String[] permissionsList = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private FusedLocationProviderClient fusedLocationClient;
    private boolean isEmpty = false;

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
//    @BindView(R.id.no_connection_layout)
//    LinearLayout noConnectionLayout;

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
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void init() {
        toggleLayout(false);
        prefManager = new PrefManager(this);
        buildViews = new BuildViews();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        placesAdapter = new PlacesAdapter(this);
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        if (prefManager.isRealtime())
            Toast.makeText(this, "Realtime is clicked!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Single update is clicked!", Toast.LENGTH_LONG).show();

        // setup recyclerView
        buildViews.setupLinearVerticalRecView(placesRecyclerView, this);
        placesRecyclerView.setAdapter(placesAdapter);
        // get user location
        getLocation();
        // setup viewModel
        placesViewModel.itemsMutableLiveData.observe(this, items -> {
            Log.d(TAG, "init: venue =>" + items.get(0).getVenue());
            placesAdapter.setList(items);
            toggleLayout(true);
        });
        placesViewModel.isEmptyMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isEmpty = aBoolean;
                toggleLayout(true);
            }
        });
    }

    // GetCurrentUserLocation
    private void getLocation() {
        if (hasPermissions()) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d(TAG, "getLocation: location =>" + location.getLatitude() + " - " + location.getLongitude());
//                            latLong = location;
                            placesViewModel.getVenues(location);
                        } else {
                            isEmpty = true;
                        }
                    });
        } else {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            isEmpty = true;
        }
    }

    // handle RunTimePermissions
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, permissionsList);
    }

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

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermissions()) {
                getLocation();
            }
        }
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
                Toast.makeText(this, "Realtime is clicked!", Toast.LENGTH_LONG).show();
                prefManager.setRealtime(true);
                return true;
            case R.id.menu_item_single_update:
                Toast.makeText(this, "Single update is clicked!", Toast.LENGTH_LONG).show();
                prefManager.setRealtime(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Checking internet connection
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            //            noConnectionLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No network connection!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Network connection is working!", Toast.LENGTH_LONG).show();
            //            noConnectionLayout.setVisibility(View.GONE);
        }
    }


    // handle data loading and no data retrieved
    private void toggleLayout(boolean flag) {
        if (flag) {
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
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
}
