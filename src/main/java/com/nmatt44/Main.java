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

    private static String apiListingUrl;
    private static String apiLocationUrl;
    private static String apiListingStatusUrl;
    private static String apiMarketplaceUrl;

    public static void main(String[] args) {

        DataHandler dataHandler = new DataHandler();

        try(InputStream inputFile = new FileInputStream("src/main/resources/config.properties")) {
            System.out.println("Properties file found, start configuration.");
            Properties config = new Properties();
            config.load(inputFile);

            apiListingUrl = config.getProperty("apiListingUrl");
            apiLocationUrl = config.getProperty("apiLocationUrl");
            apiListingStatusUrl = config.getProperty("apiListingStatusUrl");
            apiMarketplaceUrl = config.getProperty("apiMarketplaceUrl");
            System.out.println("API data set.");

            String connectionUrl = config.getProperty("dbUrl")
                    + "?user=" + config.getProperty("dbUser")
                    + "&password=" + config.getProperty("dbPassword");
            System.out.println("DB URL set: " + connectionUrl);

            try (Connection dbConnection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Connected to DB.");
            } catch(SQLException exception) {
                System.out.println("SQL exception thrown: " + exception);
            }

        } catch(IOException exception) {
            System.out.println("IO exception thrown: " + exception);
        }

        dataHandler.syncMarketplaceData(apiMarketplaceUrl);
        dataHandler.syncLocationData(apiLocationUrl);
        dataHandler.syncListingStatusData(apiListingStatusUrl);
        dataHandler.syncListingData(apiListingUrl);

    }

}
