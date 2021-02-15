package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
import kong.unirest.json.JSONObject;

public class ListingStatus {

    private int id;
    private String statusName;

    JsonHandler jsonHandler = new JsonHandler();

    public ListingStatus(JSONObject listingStatusObject) {
        this.id = jsonHandler.getIntFromJSON(listingStatusObject, "id");
        this.statusName = jsonHandler.getStringFromJSON(listingStatusObject, "status_name");
    }

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }
}
