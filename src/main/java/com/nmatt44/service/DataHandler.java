package com.nmatt44.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.net.URLEncoder;

public class DataHandler {

    public static void getDataFromAPI() {
        String apiUrl = "https://my.api.mockaroo.com/listing?key=63304c70";
        HttpResponse<String> response = Unirest.get(apiUrl).asString();
        String stringifiedResponse = response.getBody();

        System.out.println("Response from API:" + stringifiedResponse);
    }

}
