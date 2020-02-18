
package com.assem.cognitev.nearby.Models.Temp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("reasons")
    @Expose
    private Reasons reasons;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("referralId")
    @Expose
    private String referralId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param venue
     * @param reasons
     * @param referralId
     */
    public Item(Reasons reasons, Venue venue, String referralId) {
        super();
        this.reasons = reasons;
        this.venue = venue;
        this.referralId = referralId;
    }

    public Reasons getReasons() {
        return reasons;
    }

    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

    public Item withReasons(Reasons reasons) {
        this.reasons = reasons;
        return this;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Item withVenue(Venue venue) {
        this.venue = venue;
        return this;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public Item withReferralId(String referralId) {
        this.referralId = referralId;
        return this;
    }

}
