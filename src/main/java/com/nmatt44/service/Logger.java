package com.nmatt44.service;

import com.nmatt44.models.Listing;
import com.nmatt44.models.LogRecord;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logger {

    private ArrayList<LogRecord> logRecords = new ArrayList<>();
    private final QueryTool queryTool = new QueryTool();

    public void logInvalidDataRecord(LogRecord logRecord) {
        logRecords.add(logRecord);
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

    public void generateCSVLog() throws IOException {
        File logOutputFile = new File(
                "C:/Users/Lenovo/IdeaProjects/JavaTesztfeladat/src/main/log/importLog.csv"
        );
        logOutputFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(logOutputFile);
        printWriter.println("ListingId;MarketplaceName;InvalidField");
        int lineCounter = 0;
        for (LogRecord logRecord : logRecords) {
            printWriter.println(convertToCSV(logRecord));
            printWriter.flush();
            lineCounter++;
        }
        printWriter.close();
        System.out.println("CSV Logging done, logged errors: " + lineCounter);
    }

    private String convertToCSV(LogRecord logRecord) {
        return logRecord.getListingId() + ";"
                + logRecord.getMarketplaceName() + ";"
                + logRecord.getInvalidField();
    }

}
