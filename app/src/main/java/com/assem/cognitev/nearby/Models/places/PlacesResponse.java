package com.assem.cognitev.nearby.Models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResponse  {

    @SerializedName("response")
    @Expose
    private Response response;


    public Response getResponse() {
        return response;
    }

    public List<Item> getItems() {
        return this.response.getGroups().get(0).getItems();
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
