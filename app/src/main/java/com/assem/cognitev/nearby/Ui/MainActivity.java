package com.assem.cognitev.nearby.Ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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

    @BindView(R.id.main_activity_places_recycler)
    RecyclerView placesRecyclerView;

//    @BindView(R.id.no_connection_layout)
//    LinearLayout noConnectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
