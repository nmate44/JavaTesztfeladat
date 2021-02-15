package com.nmatt44.service;

import com.nmatt44.models.Listing;
import com.nmatt44.models.ListingStatus;
import com.nmatt44.models.Location;
import com.nmatt44.models.Marketplace;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataHandler {

    private static ArrayList<Marketplace> marketplaces;
    private static ArrayList<Location> locations;
    private static ArrayList<ListingStatus> listingStatuses;
    private static ArrayList<Listing> listings;
    private static ArrayList<Listing> validatedListings;
    private QueryTool queryTool = new QueryTool();
    private DataValidator dataValidator = new DataValidator();

    public void syncMarketplaceData(String apiUrl) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray marketplaceArray = apiResponse.getBody().getArray();
        generateListOfMarketplaces(marketplaceArray);
    }

    private void generateListOfMarketplaces(JSONArray marketplaceArray) {
        marketplaces = new ArrayList<>();
        for(int i = 0; i < marketplaceArray.length(); i++) {
            JSONObject marketplaceObject = marketplaceArray.getJSONObject(i);
            marketplaces.add(new Marketplace(marketplaceObject));
            System.out.println("Marketplace added: " + marketplaces.get(i).getMarketplaceName());
        }
    }

    public void syncLocationData(String apiUrl) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray locationArray = apiResponse.getBody().getArray();
        generateListOfLocations(locationArray);
    }

    private void generateListOfLocations(JSONArray locationArray) {
        locations = new ArrayList<>();
        for(int i = 0; i < locationArray.length(); i++) {
            JSONObject locationObject = locationArray.getJSONObject(i);
            locations.add(new Location(locationObject));
            System.out.println("Location added: " + locations.get(i).getId()
                    + "; Country: " + locations.get(i).getCountry()
                    + "; Town: " + locations.get(i).getTown());
        }
    }

    public void syncListingStatusData(String apiUrl) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray listingStatusArray = apiResponse.getBody().getArray();
        generateListOfListingStatuses(listingStatusArray);
    }

    private void generateListOfListingStatuses(JSONArray listingStatusArray) {
        listingStatuses = new ArrayList<>();
        for(int i = 0; i < listingStatusArray.length(); i++) {
            JSONObject listingStatusObject = listingStatusArray.getJSONObject(i);
            listingStatuses.add(new ListingStatus(listingStatusObject));
            System.out.println("Listing status added: " + listingStatuses.get(i).getStatusName());
        }
    }

    public void syncListingData(String apiUrl, Connection dbConnection) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray listingArray = apiResponse.getBody().getArray();
        generateListOfListings(listingArray);
        validatedListings = dataValidator.validateListings(listings, dbConnection);
    }

    private void generateListOfListings(JSONArray listingArray) {
        listings = new ArrayList<>();
        for(int i = 0; i < listingArray.length(); i++) {
            JSONObject listingObject = listingArray.getJSONObject(i);
            listings.add(new Listing(listingObject));
            System.out.println(
                    "Listing added: " + listings.get(i).getTitle()
                    + "; upload_time: " + listings.get(i).getUploadTime()
            );
        }
    }

    public void uploadListingsToDb(Connection dbConnection) {
        for(int i = 0; i < validatedListings.size(); i++) {
            try {
                queryTool.insertListing(validatedListings.get(i), dbConnection);
            } catch (SQLException exception) {
                System.out.println("SQLException thrown: " + exception);
            }
        }
    }

    public void uploadListingStatusesToDb(Connection dbConnection) {
        for(int i = 0; i < listingStatuses.size(); i++) {
            try {
                queryTool.insertListingStatus(listingStatuses.get(i), dbConnection);
            } catch (SQLException exception) {
            System.out.println("SQLException thrown: " + exception);
            }
        }
    }

    public void uploadMarketplacesToDb(Connection dbConnection) {
        for(int i = 0; i < marketplaces.size(); i++) {
            try {
                queryTool.insertMarketplace(marketplaces.get(i), dbConnection);
            } catch (SQLException exception) {
                System.out.println("SQLException thrown: " + exception);
            }
        }
    }

    public void uploadLocationsToDb(Connection dbConnection) {
        for(int i = 0; i < locations.size(); i++) {
            try {
                queryTool.insertLocation(locations.get(i), dbConnection);
            } catch(SQLException exception) {
                System.out.println("SQLException thrown: " + exception);
            }
        }
    }

}
