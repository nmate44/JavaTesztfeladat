package com.nmatt44;

import com.nmatt44.service.DataHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    // API URLs (set in config)
    private static String apiListing;
    private static String apiLocation;
    private static String apiListingStatus;
    private static String apiMarketplace;

    public static void main(String[] args) {
	    System.out.println("Test: ");

	    // Load config.properties (and catch errors)
        try(InputStream inputFile = new FileInputStream("src/main/resources/config.properties")) {
            Properties config = new Properties();
            config.load(inputFile);

            apiListing = config.getProperty("apiListing");
            apiLocation = config.getProperty("apiLocation");
            apiListingStatus = config.getProperty("apiListingStatus");
            apiMarketplace = config.getProperty("apiMarketplace");

            System.out.println("Configuration done. Test data: " + config.getProperty("dbUrl"));
        } catch(IOException exception) {
            System.out.println("IO exception thrown: " + exception);
        }

        // Get data from API (testing out routes)
	    DataHandler.getDataFromAPI(apiListing);
        DataHandler.getDataFromAPI(apiLocation);
        DataHandler.getDataFromAPI(apiListingStatus);
        DataHandler.getDataFromAPI(apiMarketplace);
    }
}
