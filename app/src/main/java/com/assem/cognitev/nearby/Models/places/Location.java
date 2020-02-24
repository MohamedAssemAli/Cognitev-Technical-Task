package com.assem.cognitev.nearby.Models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("crossStreet")
    @Expose
    private String cross_street;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    @SerializedName("formattedAddress")
    @Expose
    private List<String> formatted_address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCross_street() {
        return cross_street;
    }

    public void setCross_street(String cross_street) {
        this.cross_street = cross_street;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<String> getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(List<String> formatted_address) {
        this.formatted_address = formatted_address;
    }
}
