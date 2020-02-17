package com.assem.cognitev.nearby.Ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class PlacesAdapter {


    class PlaceHolder extends RecyclerView.ViewHolder{


        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
