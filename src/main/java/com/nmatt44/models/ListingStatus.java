package com.nmatt44.models;

import kong.unirest.json.JSONObject;

public class ListingStatus {

    private int id;
    private String statusName;

    public ListingStatus(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public ListingStatus(JSONObject listingStatusObject) {
        this.id = listingStatusObject.getInt("id");
        this.statusName = listingStatusObject.getString("status_name");
    }

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }
}
