package com.nmatt44.models;

public class Marketplace {

    private int id;
    private String marketplaceName;

    // Constructor
    public Marketplace(int id, String marketplaceName) {
        this.id = id;
        this.marketplaceName = marketplaceName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    public void setMarketplaceName(String marketplace_name) {
        this.marketplaceName = marketplace_name;
    }

}
