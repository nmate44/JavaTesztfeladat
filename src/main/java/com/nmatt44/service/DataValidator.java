package com.nmatt44.service;

import com.nmatt44.models.Listing;
import com.nmatt44.models.LogRecord;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DataValidator {

    private Listing actualListing;
    private final QueryTool queryTool = new QueryTool();
    private final Logger logger = new Logger();

    public ArrayList<Listing> validateListings(ArrayList<Listing> listings, Connection dbConnection) {
        System.out.println("Validating listings...");
        ArrayList<Listing> validListings = new ArrayList<>();
        for(int i = 0; i < listings.size(); i++) {
            actualListing = listings.get(i);
            if(validateOneListing(actualListing, dbConnection)) {
                validListings.add(actualListing);
            }
        }
        System.out.println("Done!");
        try {
            logger.generateCSVLog();
        } catch (IOException exception) {
            System.out.println("Exception occurred during logging: " + exception);
        }
        return validListings;
    }

    private boolean validateOneListing(Listing listing, Connection dbConnection) {
        boolean isValid = true;
        boolean[] checkResults = new boolean[10];
        checkResults[0] = checkIdValidity(listing.getId(), dbConnection);
        checkResults[1] = checkTitleValidity(listing.getTitle(), dbConnection);
        checkResults[2] = checkDescriptionValidity(listing.getDescription(), dbConnection);
        checkResults[3] = checkLocationIdValidity(listing.getInventoryItemLocationId(), dbConnection);
        checkResults[4] = checkListingPriceValidity(listing.getListingPrice(), dbConnection);
        checkResults[5] = checkCurrencyValidity(listing.getCurrency(), dbConnection);
        checkResults[6] = checkQuantityValidity(listing.getQuantity(), dbConnection);
        checkResults[7] = checkListingStatusValidity(listing.getListingStatus(), dbConnection);
        checkResults[8] = checkMarketplaceValidity(listing.getMarketplace(), dbConnection);
        checkResults[9] = checkOwnerEmailValidity(listing.getOwnerEmailAddress(), dbConnection);
        for (boolean checkResult : checkResults) {
            if (!checkResult) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private boolean checkIdValidity(UUID listingId, Connection dbConnection) {
        if(listingId == null) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "id",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTitleValidity(String listingTitle, Connection dbConnection) {
        if(listingTitle == null) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "title",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDescriptionValidity(String listingDescription, Connection dbConnection) {
        if(listingDescription == null) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "description",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
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
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "location_id",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkListingPriceValidity(double listingPrice, Connection dbConnection) {
        if(listingPrice <= 0) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "listing_price",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        }
        String priceAsString = Double.toString(listingPrice);
        int integersInPrice = priceAsString.indexOf(".");
        if(integersInPrice == -1) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "listing_price",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        }
        int decimalsInPrice = priceAsString.length() - integersInPrice - 1;
        if(decimalsInPrice != 2) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "listing_price",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkCurrencyValidity(String listingCurrency, Connection dbConnection) {
        if(listingCurrency == null) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "currency",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else if(listingCurrency.length() != 3) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "currency",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkQuantityValidity(int listingQuantity, Connection dbConnection) {
        if(listingQuantity <= 0) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "quantity",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkListingStatusValidity(int listingStatus, Connection dbConnection) {
        String queryResult = null;
        try {
            queryResult = queryTool.selectListingStatusById(listingStatus, dbConnection);

        } catch(SQLException exception) {
            System.out.println("SQLException thrown at query: " + exception);
        }
        if(queryResult == null) {
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "listing_status",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
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
            LogRecord actualRecord = logger.setActualRecord(actualListing,
                    "marketplace",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkOwnerEmailValidity(String listingOwnerEmail, Connection dbConnection) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(listingOwnerEmail)) {
            LogRecord actualRecord = logger.setActualRecord(
                    actualListing,
                    "owner_email_address",
                    dbConnection);
            logger.logInvalidDataRecord(actualRecord);
            return false;
        } else {
            return true;
        }
    }

}
