package com.assem.cognitev.nearby.Models;

public class PlaceModel {

    private String id;
    private String title;
    private String address;
    private String image;

    public PlaceModel(String id, String title, String address, String image) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
