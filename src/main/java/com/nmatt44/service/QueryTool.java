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

    public void insertListingStatus(ListingStatus listingStatus, Connection dbConnection) throws SQLException {
        String SQLQuery = "INSERT INTO public.listing_status (id, status_name) " + "VALUES (?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
        statement.setInt(1, listingStatus.getId());
        statement.setString(2, listingStatus.getStatusName());
        statement.execute();
    }

    public void insertMarketplace(Marketplace marketplace, Connection dbConnection) throws SQLException {
        String SQLQuery = "INSERT INTO public.marketplace (id, marketplace_name) " + "VALUES (?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
        statement.setInt(1, marketplace.getId());
        statement.setString(2, marketplace.getMarketplaceName());
        statement.execute();
    }

    public void insertLocation(Location location, Connection dbConnection) throws SQLException {
        String SQLQuery = "INSERT INTO public.location "
                + "(id, manager_name, phone, address_primary, address_secondary, country, town, postal_code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
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
        String SQLQuery = "INSERT INTO public.listing "
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
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
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

    public ResultSet selectMarketplaceById(int marketplaceId, Connection dbConnection) throws SQLException {
        String SQLQuery = "SELECT * FROM public.marketplace WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
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
        String SQLQuery = "SELECT * FROM public.location WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
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
        String SQLQuery = "SELECT * FROM public.listing_status WHERE id=?";
        PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
        statement.setObject(1, listingStatusId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            listingStatus = resultSet.getString("status_name");
        }
        return listingStatus;
    }

}
