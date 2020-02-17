package com.assem.cognitev.nearby.Ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.BuildViews;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    // Vars
    private BuildViews buildViews;
    private PlacesAdapter placesAdapter;
    private PlacesViewModel placesViewModel;

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
        buildViews = new BuildViews();
        placesAdapter = new PlacesAdapter(this);
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_realtime:
                Toast.makeText(this, "Realtime is clicked!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_item_single_update:
                Toast.makeText(this, "Single update is clicked!", Toast.LENGTH_LONG).show();
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
