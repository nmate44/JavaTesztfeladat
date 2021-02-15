package com.nmatt44.service;

import com.nmatt44.models.ListingStatus;
import com.nmatt44.models.Location;
import com.nmatt44.models.Marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryTool {

    public void insertListingStatus(ListingStatus listingStatus, Connection dbConnection) throws SQLException {
        String SQLQuery = "INSERT INTO public.listing_status (id, status_name) " + "VALUES (?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
            statement.setInt(1, listingStatus.getId());
            statement.setString(2, listingStatus.getStatusName());
            statement.execute();
            System.out.println("Listing status inserted to DB.");
    }

    public void insertMarketplace(Marketplace marketplace, Connection dbConnection) throws SQLException {
        String SQLQuery = "INSERT INTO public.marketplace (id, marketplace_name) " + "VALUES (?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
            statement.setInt(1, marketplace.getId());
            statement.setString(2, marketplace.getMarketplaceName());
            statement.execute();
            System.out.println("Marketplace inserted to DB.");
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
            System.out.println("Marketplace inserted to DB.");
    }

}
