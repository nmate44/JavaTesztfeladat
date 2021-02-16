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

    private final QueryTool queryTool = new QueryTool();
    private final DataValidator dataValidator = new DataValidator();

    public void syncMarketplaceData(String apiUrl, Connection dbConnection) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray marketplaceArray = apiResponse.getBody().getArray();
        generateListOfMarketplaces(marketplaceArray);
        uploadMarketplacesToDb(dbConnection);
        System.out.println("> Marketplaces synchronized.");
    }

    private void generateListOfMarketplaces(JSONArray marketplaceArray) {
        marketplaces = new ArrayList<>();
        for(int i = 0; i < marketplaceArray.length(); i++) {
            JSONObject marketplaceObject = marketplaceArray.getJSONObject(i);
            marketplaces.add(new Marketplace(marketplaceObject));
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
        System.out.println("  > Marketplaces are in database.");
    }

    public void syncLocationData(String apiUrl, Connection dbConnection) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray locationArray = apiResponse.getBody().getArray();
        generateListOfLocations(locationArray);
        uploadLocationsToDb(dbConnection);
        System.out.println("> Locations synchronized.");
    }

    private void generateListOfLocations(JSONArray locationArray) {
        locations = new ArrayList<>();
        for(int i = 0; i < locationArray.length(); i++) {
            JSONObject locationObject = locationArray.getJSONObject(i);
            locations.add(new Location(locationObject));
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
        System.out.println("  > Locations are in database.");
    }

    public void syncListingStatusData(String apiUrl, Connection dbConnection) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray listingStatusArray = apiResponse.getBody().getArray();
        generateListOfListingStatuses(listingStatusArray);
        uploadListingStatusesToDb(dbConnection);
        System.out.println("> Listing statuses synchronized.");
    }

    private void generateListOfListingStatuses(JSONArray listingStatusArray) {
        listingStatuses = new ArrayList<>();
        for(int i = 0; i < listingStatusArray.length(); i++) {
            JSONObject listingStatusObject = listingStatusArray.getJSONObject(i);
            listingStatuses.add(new ListingStatus(listingStatusObject));
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
        System.out.println("  > Listing statuses are in database.");
    }

    public void syncListingData(String apiUrl, Connection dbConnection) {
        HttpResponse<JsonNode> apiResponse = Unirest.get(apiUrl).asJson();
        JSONArray listingArray = apiResponse.getBody().getArray();
        generateListOfListings(listingArray);
        validatedListings = dataValidator.validateListings(listings, dbConnection);
        uploadListingsToDb(dbConnection);
        System.out.println("> Listings validated and synchronized.");
    }

    private void generateListOfListings(JSONArray listingArray) {
        listings = new ArrayList<>();
        int checker = 0;
        for(int i = 0; i < listingArray.length(); i++) {
            JSONObject listingObject = listingArray.getJSONObject(i);
            listings.add(new Listing(listingObject));
            checker++;
        }
        System.out.println("Listing ArrayList check: " + checker);
    }

    public void uploadListingsToDb(Connection dbConnection) {
        int insertCounter;
        for(insertCounter = 0; insertCounter < validatedListings.size(); insertCounter++) {
            try {
                queryTool.insertListing(validatedListings.get(insertCounter), dbConnection);
            } catch (SQLException exception) {
                System.out.println("SQLException thrown: " + exception);
            }
        }
        System.out.println("  > Valid listing records are in database, new records: " + insertCounter);
    }

}
