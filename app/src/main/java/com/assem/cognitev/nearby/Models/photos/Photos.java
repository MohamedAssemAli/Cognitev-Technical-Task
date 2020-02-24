package com.assem.cognitev.nearby.Models.photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {


    @SerializedName("items")
    @Expose
    public List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
