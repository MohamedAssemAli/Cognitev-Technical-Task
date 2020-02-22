
package com.assem.cognitev.nearby.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photos {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Photos() {
    }

    /**
     * 
     * @param count
     * @param groups
     */
    public Photos(int count, List<Object> groups) {
        super();
        this.count = count;
        this.groups = groups;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Photos withCount(int count) {
        this.count = count;
        return this;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public Photos withGroups(List<Object> groups) {
        this.groups = groups;
        return this;
    }

}
