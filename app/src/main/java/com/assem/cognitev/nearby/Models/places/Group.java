package com.assem.cognitev.nearby.Models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {

    @SerializedName("items")
    @Expose
    private List<Item> places;

    public List<Item> getItems() {
        return places;
    }

    public void setItems(List<Item> items) {
        this.places = items;
    }
}
