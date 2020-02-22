
package com.assem.cognitev.nearby.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Venue {

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
    private List<Category> categories = null;
    @SerializedName("photos")
    @Expose
    private Photos photos;

    private String photoPrefix;
    private String photoSuffix;
    private String photoWidth;
    private String photoHeight;

    /**
     * No args constructor for use in serialization
     */
    public Venue() {
    }

    /**
     * @param name
     * @param location
     * @param id
     * @param categories
     * @param photos
     */
    public Venue(String id, String name, Location location, List<Category> categories, Photos photos) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.categories = categories;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Venue withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Venue withName(String name) {
        this.name = name;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Venue withLocation(Location location) {
        this.location = location;
        return this;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Venue withCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public Venue withPhotos(Photos photos) {
        this.photos = photos;
        return this;
    }

    public String getPhotoPrefix() {
        return photoPrefix;
    }

    public void setPhotoPrefix(String photoPrefix) {
        this.photoPrefix = photoPrefix;
    }

    public String getPhotoSuffix() {
        return photoSuffix;
    }

    public void setPhotoSuffix(String photoSuffix) {
        this.photoSuffix = photoSuffix;
    }

    public String getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(String photoWidth) {
        this.photoWidth = photoWidth;
    }

    public String getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(String photoHeight) {
        this.photoHeight = photoHeight;
    }
}
