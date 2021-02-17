package com.nmatt44;

import com.nmatt44.service.DataHandler;
import com.nmatt44.service.Reporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {
            Properties config = loadConfigFile("src/main/resources/config.properties");
            Connection dbConnection;
            try {
                dbConnection = connectToDatabase(config);
                syncAndHandleDataFromAPI(config, dbConnection);
                reportStatistics(dbConnection);

            }
            catch (SQLException exception) {
                System.out.println("SQLException at connecting to Database: " + exception);
            }
        }
        catch(IOException exception) {
            System.out.println("IOException at loading config file: " + exception);
        }

    }

    private static Properties loadConfigFile(String configFilePath) throws IOException {
        System.out.println("Find config.properties file...");
        InputStream inputFile = new FileInputStream(configFilePath);
        Properties configFile = new Properties();
        configFile.load(inputFile);
        return configFile;
    }

    private static Connection connectToDatabase(Properties config) throws SQLException {
        System.out.println("Connecting to database...");
        String connectionUrl = config.getProperty("dbUrl")
                + "?user=" + config.getProperty("dbUser")
                + "&password=" + config.getProperty("dbPassword");
        return DriverManager.getConnection(connectionUrl);
    }

    private static void syncAndHandleDataFromAPI(Properties config, Connection dbConnection) {
        DataHandler dataHandler = new DataHandler();
        System.out.println("Start data synchronization and handling from API...");
        dataHandler.syncMarketplaceData(config.getProperty("apiMarketplaceUrl"), dbConnection);
        dataHandler.syncListingStatusData(config.getProperty("apiListingStatusUrl"), dbConnection);
        dataHandler.syncLocationData(config.getProperty("apiLocationUrl"), dbConnection);
        dataHandler.syncListingData(config.getProperty("apiListingUrl"), dbConnection);
        System.out.println("Done!");
    }

    private static void reportStatistics(Connection dbConnection) throws SQLException {
        Reporter reporter = new Reporter(dbConnection);
        try{
            reporter.generateReport();
        } catch(IOException exception) {
            System.out.println(exception);
        }

    }

}
