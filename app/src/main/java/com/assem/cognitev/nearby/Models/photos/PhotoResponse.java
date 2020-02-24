package com.assem.cognitev.nearby.Models.Photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoResponse {

    @SerializedName("response")
    @Expose
    private Response response;
    private final String size = "300x300";


    public Response getResponse() {
        return response;
    }

    public String getPhotoUrl() {
        return response.getPhotos().getItems().get(0).getPrefix()
                + size + response.getPhotos().getItems().get(0).getSuffix();
    }
}
