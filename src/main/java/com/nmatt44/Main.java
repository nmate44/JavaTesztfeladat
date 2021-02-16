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

    public static void main(String[] args) {

        Properties config = new Properties();
        Connection dbConnection;

        try(InputStream inputFile = new FileInputStream("src/main/resources/config.properties")) {
            config.load(inputFile);
            System.out.println("Config file found.");
            try {
                dbConnection = connectToDatabase(config);
                System.out.println("Success!");
                syncAndHandleDataFromAPI(config, dbConnection);
            }
            catch (SQLException exception) {
                System.out.println("SQLException at connecting to Database: " + exception);
            }
        }
        catch(IOException exception) {
            System.out.println("IOException at loading config file: " + exception);
        }

    }

    private static Connection connectToDatabase(Properties config) throws SQLException {
        String connectionUrl = config.getProperty("dbUrl")
                + "?user=" + config.getProperty("dbUser")
                + "&password=" + config.getProperty("dbPassword");
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(connectionUrl);
    }

    private static void syncAndHandleDataFromAPI(Properties config, Connection dbConnection) {
        DataHandler dataHandler = new DataHandler();
        System.out.println("Start data synchronization and handling from API...");
        dataHandler.syncMarketplaceData(config.getProperty("apiMarketplaceUrl"), dbConnection);
        dataHandler.syncListingStatusData(config.getProperty("apiListingStatusUrl"), dbConnection);
        dataHandler.syncLocationData(config.getProperty("apiLocationUrl"), dbConnection);
        dataHandler.syncListingData(config.getProperty("apiListingUrl"), dbConnection);
        dataHandler.uploadListingsToDb(dbConnection);
        System.out.println("Done!");
    }

}
