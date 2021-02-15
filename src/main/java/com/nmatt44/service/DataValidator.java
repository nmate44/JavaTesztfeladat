package com.nmatt44.service;

import com.nmatt44.models.Listing;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DataValidator {

    QueryTool queryTool = new QueryTool();

    public ArrayList<Listing> validateListings(ArrayList<Listing> listings, Connection dbConnection) {
        ArrayList<Listing> validListings = new ArrayList<>();
        for(int i = 0; i < listings.size(); i++) {
            if(validateOneListing(listings.get(i), dbConnection)) {
                validListings.add(listings.get(i));
                System.out.println("Valid listing found.");
            }
        }
        return validListings;
    }

    private boolean validateOneListing(Listing listing, Connection dbConnection) {
        boolean isValid = true;
        boolean[] checkResults = new boolean[9];
        checkResults[0] = checkTitleValidity(listing.getTitle());
        checkResults[1] = checkDescriptionValidity(listing.getDescription());
        checkResults[2] = checkLocationIdValidity(listing.getInventoryItemLocationId(), dbConnection);
        checkResults[3] = checkListingPriceValidity(listing.getListingPrice());
        checkResults[4] = checkCurrencyValidity(listing.getCurrency());
        checkResults[5] = checkQuantityValidity(listing.getQuantity());
        checkResults[6] = checkListingStatusValidity(listing.getListingStatus(), dbConnection);
        checkResults[7] = checkMarketplaceValidity(listing.getMarketplace(), dbConnection);
        checkResults[8] = checkOwnerEmailValidity(listing.getOwnerEmailAddress());
        for (boolean checkResult : checkResults) {
            if (!checkResult) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private boolean checkTitleValidity(String listingTitle) {
        if(listingTitle == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDescriptionValidity(String listingDescription) {
        if(listingDescription == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkLocationIdValidity(UUID locationId, Connection dbConnection) {
        ResultSet queryResult = null;
        try {
            queryResult = queryTool.selectLocationById(locationId, dbConnection);
        } catch (SQLException exception) {
            System.out.println("SQLException thrown at query: " + exception);
        }
        if(queryResult == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkListingPriceValidity(double listingPrice) {
        if(listingPrice == 0) return false;
        String priceAsString = Double.toString(listingPrice);
        int integersInPrice = priceAsString.indexOf(".");
        if(integersInPrice == -1) return false;
        int decimalsInPrice = priceAsString.length() - integersInPrice - 1;
        if(decimalsInPrice != 2) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkCurrencyValidity(String listingCurrency) {
        if(listingCurrency == null) {
            return false;
        } else if(listingCurrency.length() != 3) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkQuantityValidity(int listingQuantity) {
        if(listingQuantity <= 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkListingStatusValidity(int listingStatus, Connection dbConnection) {
        ResultSet queryResult = null;
        try {
            queryResult = queryTool.selectListingStatusById(listingStatus, dbConnection);
        } catch(SQLException exception) {
            System.out.println("SQLException thrown at query: " + exception);
        }
        if(queryResult == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkMarketplaceValidity(int listingMarketplace, Connection dbConnection) {
        ResultSet queryResult = null;
        try {
            queryResult = queryTool.selectMarketplaceById(listingMarketplace, dbConnection);
        } catch(SQLException exception) {
            System.out.println("SQLException thrown at query: " + exception);
        }
        if(queryResult == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkOwnerEmailValidity(String listingOwnerEmail) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(listingOwnerEmail);
    }

}
