package com.nmatt44.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class DataHandler {

    public static void getDataFromAPI(String apiUrl) {
        HttpResponse<String> response = Unirest.get(apiUrl).asString();
        String stringifiedResponse = response.getBody();

        System.out.println("Response from API:" + stringifiedResponse);
    }

}
