package com.nmatt44.service;

import com.nmatt44.models.Listing;

import java.util.ArrayList;
import java.util.UUID;

public class DataValidator {

    public void validateListings(ArrayList<Listing> listings) {
        for(int i = 0; i < listings.size(); i++) {

        }
    }

    private void validateOneListing(Listing listing) {

    }

    private void checkUUID(UUID listingUUID) {
        if(listingUUID == null) {

        }
    }

}
