
package com.assem.cognitev.nearby.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reasons {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("items")
    @Expose
    private List<Item_> items = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Reasons() {
    }

    /**
     * 
     * @param count
     * @param items
     */
    public Reasons(int count, List<Item_> items) {
        super();
        this.count = count;
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Reasons withCount(int count) {
        this.count = count;
        return this;
    }

    public List<Item_> getItems() {
        return items;
    }

    public void setItems(List<Item_> items) {
        this.items = items;
    }

    public Reasons withItems(List<Item_> items) {
        this.items = items;
        return this;
    }

}
