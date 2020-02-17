package com.assem.cognitev.nearby.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.Models.PlaceModel;
import com.assem.cognitev.nearby.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceHolder> {

    private Context context;
    private ArrayList<PlaceModel> placeModelArrayList = new ArrayList<>();

    public PlacesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        return new PlaceHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        final PlaceModel placeModel = placeModelArrayList.get(position);
        holder.placeTitle.setText(placeModel.getTitle());
        holder.placeAddress.setText(placeModel.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeModelArrayList.size();
    }

    public void setList(ArrayList<PlaceModel> placeModelArrayList) {
        this.placeModelArrayList = placeModelArrayList;
        notifyDataSetChanged();
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_place_title)
        TextView placeTitle;
        @BindView(R.id.item_place_address)
        TextView placeAddress;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
