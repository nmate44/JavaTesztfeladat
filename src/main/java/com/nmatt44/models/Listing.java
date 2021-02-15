package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
import kong.unirest.json.JSONObject;

import java.util.UUID;

public class Listing {

    private UUID id;
    private String title;
    private String description;
    private UUID inventoryItemLocationId;
    private double listingPrice;
    private String currency;
    private int quantity;
    private int listingStatus;
    private int marketplace;
    private String uploadTime;
    private String ownerEmailAddress;

    JsonHandler jsonHandler = new JsonHandler();

    public Listing(JSONObject listingObject) {
        try {
            this.id = jsonHandler.getUUIDFromJSON(listingObject, "id");
        } catch (IllegalArgumentException exception) {
            this.id = null;
            System.out.println("Invalid UUID material, set to null.");
        }
        this.title = jsonHandler.getStringFromJSON(listingObject, "title");
        this.description = jsonHandler.getStringFromJSON(listingObject, "description");
        try {
            this.inventoryItemLocationId = jsonHandler.getUUIDFromJSON(listingObject, "location_id");
        } catch (IllegalArgumentException exception) {
            this.inventoryItemLocationId = null;
            System.out.println("Invalid UUID material, set to null.");
        }
        this.listingPrice = jsonHandler.getDoubleFromJSON(listingObject, "listing_price");
        this.currency = jsonHandler.getStringFromJSON(listingObject, "currency");
        this.quantity = jsonHandler.getIntFromJSON(listingObject, "quantity");
        this.listingStatus = jsonHandler.getIntFromJSON(listingObject, "listing_status");
        this.marketplace = jsonHandler.getIntFromJSON(listingObject, "marketplace");
        this.uploadTime = jsonHandler.getStringFromJSON(listingObject, "upload_time");
        this.ownerEmailAddress = jsonHandler.getStringFromJSON(listingObject, "owner_email_address");
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getInventoryItemLocationId() {
        return inventoryItemLocationId;
    }

    public double getListingPrice() {
        return listingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getListingStatus() {
        return listingStatus;
    }

    public int getMarketplace() {
        return marketplace;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }
}
