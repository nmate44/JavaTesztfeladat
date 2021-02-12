package com.nmatt44;

import com.nmatt44.service.DataHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    // API URLs (set in config)
    private static String apiListing;
    private static String apiLocation;
    private static String apiListingStatus;
    private static String apiMarketplace;

    public static void main(String[] args) {

	    // Initialization w/ config file
        try(InputStream inputFile = new FileInputStream("src/main/resources/config.properties")) {

            System.out.println("Properties file found, start configuration.");

            // Set Properties
            Properties config = new Properties();
            config.load(inputFile);

            // Set API data
            apiListing = config.getProperty("apiListing");
            apiLocation = config.getProperty("apiLocation");
            apiListingStatus = config.getProperty("apiListingStatus");
            apiMarketplace = config.getProperty("apiMarketplace");
            System.out.println("API data set.");

            // Set DB URL according to parameters
            String connectionUrl = config.getProperty("dbUrl")
                    + "?user=" + config.getProperty("dbUser")
                    + "&password=" + config.getProperty("dbPassword");
            System.out.println("DB URL set: " + connectionUrl);

            // Connect to DB
            try (Connection dbConnection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Connected to DB.");
            } catch(SQLException exception) {
                System.out.println("SQL exception thrown: " + exception);
            }

        } catch(IOException exception) {
            System.out.println("IO exception thrown: " + exception);
        }
        // End of initialization

        // Data sync from API
        DataHandler.syncMarketplaceData(apiMarketplace);
        DataHandler.syncLocationData(apiLocation);
        // End of data sync

    }
}
