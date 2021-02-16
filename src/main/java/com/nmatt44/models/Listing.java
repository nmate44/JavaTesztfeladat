package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
import kong.unirest.json.JSONObject;

import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private java.sql.Date uploadTime;
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
        try {
            this.uploadTime = generateSQLDate(listingObject);
        } catch (ParseException exception) {
            System.out.println("ParseException thrown: " + exception);
            this.uploadTime = null;
        }
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

    public java.sql.Date getUploadTime() {
        return uploadTime;
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    private java.sql.Date generateSQLDate(JSONObject listingObject) throws ParseException {
        String jsonDate = jsonHandler.getStringFromJSON(listingObject, "upload_time");
        if(jsonDate == null) {
            return null;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date parsedDate = dateFormat.parse(jsonDate);
            return new java.sql.Date(parsedDate.getTime());
        }
    }
}
