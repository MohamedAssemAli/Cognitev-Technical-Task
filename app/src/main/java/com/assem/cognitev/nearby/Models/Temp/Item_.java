
package com.assem.cognitev.nearby.Models.Temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item_ {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reasonName")
    @Expose
    private String reasonName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item_() {
    }

    /**
     * 
     * @param summary
     * @param reasonName
     * @param type
     */
    public Item_(String summary, String type, String reasonName) {
        super();
        this.summary = summary;
        this.type = type;
        this.reasonName = reasonName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Item_ withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item_ withType(String type) {
        this.type = type;
        return this;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public Item_ withReasonName(String reasonName) {
        this.reasonName = reasonName;
        return this;
    }

}
