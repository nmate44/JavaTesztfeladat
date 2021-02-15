package com.nmatt44.models;

import com.nmatt44.service.JsonHandler;
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

    JsonHandler jsonHandler = new JsonHandler();

    public Location(JSONObject locationObject) {
        this.id = jsonHandler.getUUIDFromJSON(locationObject, "id");
        this.managerName = jsonHandler.getStringFromJSON(locationObject, "manager_name");
        this.phone = jsonHandler.getStringFromJSON(locationObject, "phone");
        this.addressPrimary = jsonHandler.getStringFromJSON(locationObject, "address_primary");
        this.addressSecondary = jsonHandler.getStringFromJSON(locationObject, "address_secondary");
        this.country = jsonHandler.getStringFromJSON(locationObject, "country");
        this.town = jsonHandler.getStringFromJSON(locationObject, "town");
        this.postalCode = jsonHandler.getStringFromJSON(locationObject, "postal_code");
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
