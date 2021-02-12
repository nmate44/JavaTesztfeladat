package com.nmatt44.service;

import com.nmatt44.models.Location;
import com.nmatt44.models.Marketplace;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class DataHandler {

    private static ArrayList<Marketplace> marketplaces;
    private static ArrayList<Location> locations;

    public static void syncMarketplaceData(String apiUrl) {
        HttpResponse<JsonNode> response = Unirest.get(apiUrl).asJson();

        JSONArray marketplaceArray = response.getBody().getArray();
        marketplaces = new ArrayList<>();

        for(int i = 0; i < marketplaceArray.length(); i++) {
            JSONObject marketplaceObject = marketplaceArray.getJSONObject(i);
            marketplaces.add( new Marketplace(
              marketplaceObject.getInt("id"),
              marketplaceObject.getString("marketplace_name")
            ));
            System.out.println("Marketplace added: " + marketplaces.get(i).getMarketplaceName());
        }
    }

    public static void syncLocationData(String apiUrl) {
        HttpResponse<JsonNode> response = Unirest.get(apiUrl).asJson();

        JSONArray locationArray = response.getBody().getArray();
        locations = new ArrayList<>();

        for(int i = 0; i < locationArray.length(); i++) {
            JSONObject locationObject = locationArray.getJSONObject(i);
            locations.add( new Location(locationObject));
            System.out.println("Location added: " + locations.get(i).getId()
                    + "; Country: " + locations.get(i).getCountry()
                    + "; Town: " + locations.get(i).getTown());
        }
    }

}
