package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
import kong.unirest.json.JSONObject;

public class Marketplace {

    private int id;
    private String marketplaceName;

    JsonHandler jsonHandler = new JsonHandler();

    public Marketplace(JSONObject marketplaceObject) {
        this.id = jsonHandler.getIntFromJSON(marketplaceObject, "id");
        this.marketplaceName = jsonHandler.getStringFromJSON(marketplaceObject, "marketplace_name");
    }

    public int getId() {
        return id;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

}
