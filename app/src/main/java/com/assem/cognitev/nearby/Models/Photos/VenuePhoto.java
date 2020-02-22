package com.assem.cognitev.nearby.Models.Photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenuePhoto {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private Integer createdAt;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     *
     */
    public VenuePhoto() {
    }

    /**
     *
     * @param createdAt
     * @param prefix
     * @param width
     * @param id
     * @param source
     * @param suffix
     * @param user
     * @param height
     */
    public VenuePhoto(String id, Integer createdAt, Source source, String prefix, String suffix, Integer width, Integer height, User user) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.source = source;
        this.prefix = prefix;
        this.suffix = suffix;
        this.width = width;
        this.height = height;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
