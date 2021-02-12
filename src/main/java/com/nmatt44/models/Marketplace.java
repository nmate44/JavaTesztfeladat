package com.nmatt44.models;

import kong.unirest.json.JSONObject;

public class Marketplace {

    private int id;
    private String marketplaceName;

    // Constructor
    public Marketplace(int id, String marketplaceName) {
        this.id = id;
        this.marketplaceName = marketplaceName;
    }

    public Marketplace(JSONObject marketplaceObject) {
        this.id = marketplaceObject.getInt("id");
        this.marketplaceName = marketplaceObject.getString("marketplace_name");
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

}
