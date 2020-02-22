package com.assem.cognitev.nearby.Models.places;


import com.assem.cognitev.nearby.Models.photos.PhotoRespone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("categories")
    @Expose
    private List<Category> categories;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getCategory() {
        return categories.get(0).getName();
    }

    public String getFormattedAddress() {
        return location.getFormatted_address().get(0);
    }

    public void setPhotoRespone(PhotoRespone photoRespone) {
        this.photoRespone = photoRespone;
    }

    private PhotoRespone photoRespone;

    public PhotoRespone getPhotoRespone() {
        return photoRespone;
    }


}
