package com.nmatt44.service;

import kong.unirest.json.JSONObject;

import java.util.UUID;

public class JsonHandler {

    public String getStringFromJSON(JSONObject jsonObject, String jsonKey) {
        String result;
        if(jsonObject.get(jsonKey) == null) {
            result = null;
        }
        else {
            result = jsonObject.getString(jsonKey);
        }
        return result;
    }

    public int getIntFromJSON(JSONObject jsonObject, String jsonKey) {
        int result;
        if(jsonObject.get(jsonKey) == null) {
            result = 0;
        }
        else {
            result = jsonObject.getInt(jsonKey);
        }
        return result;
    }

    public double getDoubleFromJSON(JSONObject jsonObject, String jsonKey) {
        double result;
        if(jsonObject.get(jsonKey) == null) {
            result = 0;
        }
        else {
            result = jsonObject.getDouble(jsonKey);
        }
        return result;
    }

    public UUID getUUIDFromJSON(JSONObject jsonObject, String jsonKey) throws IllegalArgumentException {
        UUID result = UUID.fromString(jsonObject.getString(jsonKey));
        return result;
    }

}
