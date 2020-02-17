package com.assem.cognitev.nearby.Ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.Helper.PrefManager;
import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.BuildViews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private final String TAG = MainActivity.class.getSimpleName();
    // Vars
    private BuildViews buildViews;
    private PlacesAdapter placesAdapter;
    private PlacesViewModel placesViewModel;
    private PrefManager prefManager;
    private final int RC_LOCATION_PERM = 124;
    String[] permissionsList = {Manifest.permission.ACCESS_FINE_LOCATION};

    // Views
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_activity_places_recycler)
    RecyclerView placesRecyclerView;

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

    private void init() {
        prefManager = new PrefManager(this);
        buildViews = new BuildViews();
        placesAdapter = new PlacesAdapter(this);
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        if (prefManager.isRealtime())
            Toast.makeText(this, "Realtime is clicked!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Single update is clicked!", Toast.LENGTH_LONG).show();

  /*
        placesViewModel.getPosts();
        // setup recyclerView
        buildViews.setupLinearVerticalRecView(placesRecyclerView, this);
        placesRecyclerView.setAdapter(placesAdapter);
        // setup viewModel
        placesViewModel.placesMutableLiveData.observe(this, new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> postModels) {
                placesAdapter.setList(postModels);
            }
        });
         */


        placesViewModel.getResponse();
        placesViewModel.responseAsString.observe(this, s -> {
            Log.i(TAG, "response -> ");
            Log.i(TAG, "response -> " + s);
        });
    }

    // handle RunTimePermissions
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, permissionsList);
    }

    private void getUserLatLan() {
        if (hasPermissions()) {
            Toast.makeText(this, "TODO: Location things", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
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


//    private void isConnected(boolean isConnected) {
//        if (isConnected) {
//            noConnectionLayout.setVisibility(View.GONE);
//        } else {
//            noConnectionLayout.setVisibility(View.VISIBLE);
//        }
//    }


    //    private void toggleLayout(boolean flag) {
//        if (flag) {
//            progressLayout.setVisibility(View.GONE);
//            progressBar.hide();
//            if (moreProductsArrayList.isEmpty()) {
//                emptyRecyclerTxt.setVisibility(View.VISIBLE);
//                moreSellerProductsImagesRecycler.setVisibility(View.GONE);
//            } else {
//                emptyRecyclerTxt.setVisibility(View.GONE);
//                moreSellerProductsImagesRecycler.setVisibility(View.VISIBLE);
//            }
//        } else {
//            progressLayout.setVisibility(View.VISIBLE);
//            progressBar.show();
//        }
//    }

}
