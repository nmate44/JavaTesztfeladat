package com.nmatt44.models;

public class LogRecord {

    private String listingId;
    private String marketplaceName;
    private String invalidField;

    public LogRecord(String listingId, String marketplaceName, String invalidField) {
        this.listingId = listingId;
        this.marketplaceName = marketplaceName;
        this.invalidField = invalidField;
    }

    public String getListingId() {
        return listingId;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    public String getInvalidField() {
        return invalidField;
    }

}
