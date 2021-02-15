package com.nmatt44.service;

import com.nmatt44.models.Listing;
import com.nmatt44.models.LogRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logger {

    private ArrayList<LogRecord> logRecords = new ArrayList<>();
    private QueryTool queryTool = new QueryTool();

    public void logInvalidDataRecord(LogRecord logRecord) {
        logRecords.add(logRecord);
        System.out.println("Log record added: " + logRecord.getListingId() + ";" + logRecord.getMarketplaceName() + ";" + logRecord.getInvalidField());
    }

    public LogRecord setActualRecord(Listing actualListing, String actualField, Connection dbConnection) {
        String listingId = actualListing.getId().toString();
        String marketplaceName = null;
        try {
            marketplaceName = queryTool.selectMarketplaceNameById(actualListing.getMarketplace(), dbConnection);
        } catch(SQLException exception) {
            System.out.print("SQLException thrown: " + exception);
        }
        return new LogRecord(listingId, marketplaceName, actualField);
    }

}
