package com.nmatt44.service;

import com.nmatt44.models.Listing;
import com.nmatt44.models.ListingStatus;
import com.nmatt44.models.Location;
import com.nmatt44.models.Marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QueryTool {

    // Insert queries:

    public void insertListingStatus(ListingStatus listingStatus, Connection dbConnection) throws SQLException {
        String SQL = "INSERT INTO public.listing_status (id, status_name) " + "VALUES (?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, listingStatus.getId());
        statement.setString(2, listingStatus.getStatusName());
        statement.execute();
    }

    public void insertMarketplace(Marketplace marketplace, Connection dbConnection) throws SQLException {
        String SQL = "INSERT INTO public.marketplace (id, marketplace_name) " + "VALUES (?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, marketplace.getId());
        statement.setString(2, marketplace.getMarketplaceName());
        statement.execute();
    }

    public void insertLocation(Location location, Connection dbConnection) throws SQLException {
        String SQL = "INSERT INTO public.location "
                + "(id, manager_name, phone, address_primary, address_secondary, country, town, postal_code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setObject(1, location.getId());
        statement.setString(2, location.getManagerName());
        statement.setString(3, location.getPhone());
        statement.setString(4, location.getAddressPrimary());
        statement.setString(5, location.getAddressSecondary());
        statement.setString(6, location.getCountry());
        statement.setString(7, location.getTown());
        statement.setString(8, location.getPostalCode());
        statement.execute();
    }

    public void insertListing(Listing listing, Connection dbConnection) throws SQLException {
        String SQL = "INSERT INTO public.listing "
                + "(id, "
                + "title, "
                + "description, "
                + "location_id, "
                + "listing_price, "
                + "currency, "
                + "quantity, "
                + "listing_status, "
                + "marketplace, "
                + "upload_time, "
                + "owner_email_address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setObject(1, listing.getId());
        statement.setString(2, listing.getTitle());
        statement.setString(3, listing.getDescription());
        statement.setObject(4, listing.getInventoryItemLocationId());
        statement.setDouble(5, listing.getListingPrice());
        statement.setString(6, listing.getCurrency());
        statement.setInt(7, listing.getQuantity());
        statement.setInt(8, listing.getListingStatus());
        statement.setInt(9, listing.getMarketplace());
        statement.setObject(10, listing.getUploadTime());
        statement.setString(11, listing.getOwnerEmailAddress());
        statement.execute();
    }

    // Select queries:

    public ResultSet selectMarketplaceById(int marketplaceId, Connection dbConnection) throws SQLException {
        String SQL = "SELECT * FROM public.marketplace WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, marketplaceId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public String selectMarketplaceNameById(int marketplaceId, Connection dbConnection) throws SQLException {
        String marketplaceName = null;
        ResultSet resultSet = selectMarketplaceById(marketplaceId, dbConnection);
        if(resultSet.next()) {
            marketplaceName = resultSet.getString("marketplace_name");
        }
        return marketplaceName;
    }

    // Needed a valid not null field without complications
    public String selectLocationAddressById(UUID locationId, Connection dbConnection) throws SQLException {
        String SQL = "SELECT * FROM public.location WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setObject(1, locationId);
        ResultSet resultSet = statement.executeQuery();
        String foundPrimaryAddress = null;
        if(resultSet.next()) {
            foundPrimaryAddress = resultSet.getString("address_primary");
        }
        return foundPrimaryAddress;
    }

    public String selectListingStatusNameById(int listingStatusId, Connection dbConnection) throws SQLException {
        String listingStatus = null;
        String SQL = "SELECT * FROM public.listing_status WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setObject(1, listingStatusId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            listingStatus = resultSet.getString("status_name");
        }
        return listingStatus;
    }

    // Report-specific queries:

    public int countListings(Connection dbConnection) throws SQLException {
        String SQL = "SELECT COUNT(id) FROM public.listing";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        ResultSet resultSet = statement.executeQuery();
        int countedListings = 0;
        if(resultSet.next()) {
            countedListings = resultSet.getInt("count");
        }
        return countedListings;
    }

    public int countListingsByMarketplace(int marketplaceId, Connection dbConnection) throws SQLException {
        String SQL = "SELECT COUNT(id) FROM public.listing WHERE marketplace=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, marketplaceId);
        ResultSet resultSet = statement.executeQuery();
        int countedListings = 0;
        if(resultSet.next()) {
            countedListings = resultSet.getInt("count");
        }
        return countedListings;
    }

    public double sumTotalListingPriceByMarketplace(int marketplaceId, Connection dbConnection) throws SQLException {
        String SQL = "SELECT SUM(listing_price) FROM public.listing WHERE marketplace=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, marketplaceId);
        ResultSet resultSet = statement.executeQuery();
        double sumOfPrices = 0;
        if(resultSet.next()) {
            sumOfPrices = resultSet.getDouble("sum");
        }
        return sumOfPrices;
    }

    public double averageListingPriceByMarketplace(int marketplaceId, Connection dbConnection) throws SQLException {
        String SQL = "SELECT AVG(listing_price) FROM public.listing WHERE marketplace=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, marketplaceId);
        ResultSet resultSet = statement.executeQuery();
        double avgOfPrices = 0;
        if(resultSet.next()) {
            avgOfPrices = resultSet.getDouble("avg");
        }
        return avgOfPrices;
    }

    public String findBestListerEmail(Connection dbConnection) throws SQLException {
        String SQL = "SELECT COUNT(id), owner_email_address FROM public.listing "
                + "GROUP BY owner_email_address "
                + "ORDER BY count DESC LIMIT 1";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        ResultSet resultSet = statement.executeQuery();
        String bestLister = null;
        if(resultSet.next()) {
            bestLister = resultSet.getString("owner_email_address");
        }
        return bestLister;
    }

    public ResultSet selectReportDataByMarketplace(Connection dbConnection) throws SQLException {
        String SQL =
                "SELECT "
                    + "public.marketplace.marketplace_name, "
                    + "COUNT(public.listing.id), "
                    + "SUM(public.listing.listing_price), "
                    + "AVG(public.listing.listing_price) "
                + "FROM public.listing "
                + "INNER JOIN public.marketplace ON public.listing.marketplace=public.marketplace.id "
                + "GROUP BY public.marketplace.marketplace_name";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        return statement.executeQuery();
    }

    public ResultSet selectMonthlyReportDataByMarketplace(Connection dbConnection) throws SQLException {
        String SQL =
                "SELECT "
                    + "EXTRACT(YEAR FROM public.listing.upload_time) AS year, "
                    + "EXTRACT(MONTH FROM public.listing.upload_time) AS month, "
                    + "public.marketplace.marketplace_name, "
                    + "COUNT(public.listing.id), "
                    + "SUM(public.listing.listing_price), "
                    + "AVG(public.listing.listing_price) "
                + "FROM public.listing "
                + "INNER JOIN public.marketplace ON public.listing.marketplace=public.marketplace.id "
                + "GROUP BY "
                    + "EXTRACT(YEAR FROM upload_time), "
                    + "EXTRACT(MONTH FROM upload_time), "
                    + "public.marketplace.marketplace_name "
                + "ORDER BY "
                    + "EXTRACT(YEAR FROM upload_time), "
                    + "EXTRACT(MONTH FROM upload_time) ";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        return statement.executeQuery();
    }

    public String findMonthlyBestListerEmail(Connection dbConnection, int year, int month) throws SQLException {
        String SQL = "SELECT "
                    + "EXTRACT(YEAR FROM upload_time) AS year, "
                    + "EXTRACT(MONTH FROM upload_time) AS month, "
                    + "COUNT(id), "
                    + "owner_email_address "
                + "FROM public.listing "
                + "WHERE EXTRACT(YEAR FROM upload_time)=? AND EXTRACT(MONTH FROM upload_time)=? "
                + "GROUP BY "
                    + "EXTRACT(YEAR FROM upload_time), "
                    + "EXTRACT(MONTH FROM upload_time), "
                    + "owner_email_address "
                + "ORDER BY "
                    + "COUNT(id) DESC LIMIT 1";
        PreparedStatement statement = dbConnection.prepareStatement(SQL);
        statement.setInt(1, year);
        statement.setInt(2, month);
        ResultSet queryResult = statement.executeQuery();
        if(queryResult.next()) {
            System.out.println("Monthly Lister Found: " + queryResult.getString("owner_email_address"));
            return queryResult.getString("owner_email_address");
        }
        System.out.println("No monthly lister found...............");
        return null;
    }

}
