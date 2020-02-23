package com.assem.cognitev.nearby.Models.Responses.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    public Place getPlace() {
        return place;
    }

    @SerializedName("venue")
    @Expose
    private Place place;
}
