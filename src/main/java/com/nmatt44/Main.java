package com.nmatt44;

import com.nmatt44.service.DataHandler;
import com.nmatt44.service.QueryTool;

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
        Properties config = new Properties();
        Connection dbConnection = null;

        try(InputStream inputFile = new FileInputStream("src/main/resources/config.properties")) {
            System.out.println("Properties file found, start configuration.");
            config.load(inputFile);

            apiListingUrl = config.getProperty("apiListingUrl");
            apiLocationUrl = config.getProperty("apiLocationUrl");
            apiListingStatusUrl = config.getProperty("apiListingStatusUrl");
            apiMarketplaceUrl = config.getProperty("apiMarketplaceUrl");
            System.out.println("API data set.");

            try {
                dbConnection = connectToDatabase(config);
                System.out.println("Connected to Database.");
            } catch (SQLException exception) {
                System.out.println("SQL exception thrown: " + exception);
            }

        } catch(IOException exception) {
            System.out.println("IO exception thrown: " + exception);
        }

        dataHandler.syncMarketplaceData(apiMarketplaceUrl);
        dataHandler.uploadMarketplacesToDb(dbConnection);

        dataHandler.syncListingStatusData(apiListingStatusUrl);
        dataHandler.uploadListingStatusesToDb(dbConnection);

        dataHandler.syncLocationData(apiLocationUrl);
        dataHandler.uploadLocationsToDb(dbConnection);

        dataHandler.syncListingData(apiListingUrl, dbConnection);

    }

    private static Connection connectToDatabase(Properties config) throws SQLException {
        String connectionUrl = config.getProperty("dbUrl")
                + "?user=" + config.getProperty("dbUser")
                + "&password=" + config.getProperty("dbPassword");
        System.out.println("Database URL set.");
        return DriverManager.getConnection(connectionUrl);
    }

}
