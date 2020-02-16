package com.assem.cognitev.nearby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.no_connection_layout)
//    LinearLayout noConnectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
