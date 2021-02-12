package com.nmatt44.models;

import kong.unirest.json.JSONObject;

import java.util.UUID;

public class Location {

    private UUID id;
    private String managerName;
    private String phone;
    private String addressPrimary;
    private String addressSecondary;
    private String country;
    private String town;
    private String postalCode;

    public Location(UUID id, String managerName, String phone, String addressPrimary, String addressSecondary, String country, String town, String postalCode) {
        this.id = id;
        this.managerName = managerName;
        this.phone = phone;
        this.addressPrimary = addressPrimary;
        this.addressSecondary = addressSecondary;
        this.country = country;
        this.town = town;
        this.postalCode = postalCode;
    }

    // Construct from JSONObject (Check fields w/ possible nulls)
    public Location(JSONObject locationObject) {
        this.id = UUID.fromString(locationObject.getString("id"));
        this.managerName = locationObject.getString("manager_name");
        this.phone = locationObject.getString("phone");
        this.addressPrimary = locationObject.getString("address_primary");

        if(locationObject.get("address_secondary") == null) {
            this.addressSecondary = null;
        }
        else {
            this.addressSecondary = locationObject.getString("address_secondary");
        }

        this.country = locationObject.getString("country");
        this.town = locationObject.getString("town");

        if(locationObject.get("postal_code") == null) {
            this.postalCode = null;
        }
        else {
            this.postalCode = locationObject.getString("postal_code");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
