package com.assem.cognitev.nearby.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assem.cognitev.nearby.Models.places.Item;
import com.assem.cognitev.nearby.R;
import com.assem.cognitev.nearby.Utils.ImageViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.PlaceHolder> {

    private final String TAG = VenuesAdapter.class.getSimpleName();

    private Context context;
    private List<Item> itemsArrayList = new ArrayList<>();

    public VenuesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        return new PlaceHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        final Item itemModel = itemsArrayList.get(position);
        holder.placeTitle.setText(itemModel.getPlace().getName());
        holder.placeAddress.setText(itemModel.getPlace().getCategories().get(0).getName()
                + " - " + itemModel.getPlace().getLocation().getFormatted_address().toString());
        try {
            ImageViewUtils.fitImage(context, holder.placeImg, itemModel.getPlace().getPhotoResponse().getPhotoUrl());
        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: null", e);
        }
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_place_image)
        ImageView placeImg;
        @BindView(R.id.item_place_title)
        TextView placeTitle;
        @BindView(R.id.item_place_address)
        TextView placeAddress;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setList(List<Item> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        notifyDataSetChanged();
    }

    public void updatePlace(Item item) {
        itemsArrayList.set(itemsArrayList.indexOf(item), item);
        notifyItemChanged(itemsArrayList.indexOf(item));
    }

    public void clearList(ArrayList<Item> itemsArrayList) {
        itemsArrayList.clear();
        notifyDataSetChanged();
    }
}
