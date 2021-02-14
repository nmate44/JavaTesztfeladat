package com.nmatt44.service;

import com.nmatt44.models.ListingStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryTool {

    public void insertListingStatus(ListingStatus listingStatus, Connection dbConnection) {
        String SQLQuery = "INSERT INTO public.listing_status (id, status_name) " + "VALUES (?, ?)";
        try {
            PreparedStatement statement = dbConnection.prepareStatement(SQLQuery);
            statement.setInt(1, listingStatus.getId());
            statement.setString(2, listingStatus.getStatusName());
            statement.execute();
            System.out.println("Listing status inserted to DB.");
        }
        catch (SQLException exception) {
            System.out.println("SQLException thrown: " + exception);
        }
    }

}
