package com.nmatt44.models;

import kong.unirest.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Date uploadTime;
    private String ownerEmailAddress;

    public Listing(JSONObject listingObject) {
        this.id = UUID.fromString(listingObject.getString("id"));
        this.title = listingObject.getString("title");
        this.description = listingObject.getString("description");
        this.inventoryItemLocationId = UUID.fromString(listingObject.getString("location_id"));
        this.listingPrice = listingObject.getDouble("listing_price");
        this.currency = listingObject.getString("currency");
        this.quantity = listingObject.getInt("quantity");
        this.listingStatus = listingObject.getInt("listing_status");
        this.marketplace = listingObject.getInt("marketplace");
        this.uploadTime = generateDateFromString(listingObject.getString("upload_time"));
        this.ownerEmailAddress = listingObject.getString("owner_email_address");
    }

    private Date generateDateFromString(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(stringDate);
        } catch(ParseException exception) {
            System.out.println("ParseException thrown at Date gen: " + exception);
            parsedDate = null;
        }
        return parsedDate;
    }

    public String getTitle() {
        return title;
    }
}
