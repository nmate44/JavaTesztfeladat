package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
import kong.unirest.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Listing {

    private String id;
    private String title;
    private String description;
    private String inventoryItemLocationId;
    private double listingPrice;
    private String currency;
    private int quantity;
    private int listingStatus;
    private int marketplace;
    private String uploadTime;
    private String ownerEmailAddress;

    JsonHandler jsonHandler = new JsonHandler();

    public Listing(JSONObject listingObject) {
        this.id = jsonHandler.getStringFromJSON(listingObject, "id");
        this.title = jsonHandler.getStringFromJSON(listingObject, "title");
        this.description = jsonHandler.getStringFromJSON(listingObject, "description");
        this.inventoryItemLocationId = jsonHandler.getStringFromJSON(listingObject, "location_id");
        this.listingPrice = jsonHandler.getDoubleFromJSON(listingObject, "listing_price");
        this.currency = jsonHandler.getStringFromJSON(listingObject, "currency");
        this.quantity = jsonHandler.getIntFromJSON(listingObject, "quantity");
        this.listingStatus = jsonHandler.getIntFromJSON(listingObject, "listing_status");
        this.marketplace = jsonHandler.getIntFromJSON(listingObject, "marketplace");
        this.uploadTime = jsonHandler.getStringFromJSON(listingObject, "upload_time");
        this.ownerEmailAddress = jsonHandler.getStringFromJSON(listingObject, "owner_email_address");
    }

    public String getTitle() {
        return title;
    }

    public String getUploadTime() {
        return uploadTime;
    }
}
