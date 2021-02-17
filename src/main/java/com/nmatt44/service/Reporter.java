package com.nmatt44.service;

import kong.unirest.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reporter {

    private final QueryTool queryTool = new QueryTool();
    private Connection dbConnection;

    public Reporter(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void generateReport() {
        JSONObject jsonReport = new JSONObject();
        addSimpleData(jsonReport);
        addDataByMarketplace(jsonReport);
        addMonthlyData(jsonReport);
        System.out.println("Report: " + jsonReport.toString());
    }

    private void addSimpleData(JSONObject jsonReport) {
        try {
            jsonReport.put("totalListingCount", queryTool.countListings(dbConnection));
            jsonReport.put("bestListerEmail", queryTool.findBestListerEmail(dbConnection));
        }
        catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    private void addDataByMarketplace(JSONObject jsonReport) {
        try {
            ResultSet queryResult = queryTool.selectReportDataByMarketplace(dbConnection);
            while(queryResult.next()) {
                String marketplaceName = queryResult.getString("marketplace_name");
                String countJsonKey = "total" + marketplaceName + "ListingCount";
                jsonReport.put(countJsonKey, queryResult.getInt("count"));
                String sumJsonKey = "total" + marketplaceName + "ListingPrice";
                jsonReport.put(sumJsonKey, queryResult.getDouble("sum"));
                String avgJsonKey = "average" + marketplaceName + "ListingPrice";
                jsonReport.put(avgJsonKey, queryResult.getDouble("avg"));
            }
        }
        catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    private void addMonthlyData(JSONObject jsonReport) {
        try {
            ResultSet queryResult = queryTool.selectMonthlyReportDataByMarketplace(dbConnection);
            if(queryResult.next()) {
                JSONObject monthlyRecord = new JSONObject();
                String monthOfYear = queryResult.getInt("year") + "/"
                        + queryResult.getInt("month");
                String lastMonthOfYear = monthOfYear;
                String marketplaceName = queryResult.getString("marketplace_name");
                String countJsonKey = "total" + marketplaceName + "ListingCount";
                monthlyRecord.put(countJsonKey, queryResult.getInt("count"));
                String sumJsonKey = "total" + marketplaceName + "ListingPrice";
                monthlyRecord.put(sumJsonKey, queryResult.getDouble("sum"));
                String avgJsonKey = "average" + marketplaceName + "ListingPrice";
                monthlyRecord.put(avgJsonKey, queryResult.getDouble("avg"));
                System.out.println("First month: " + monthOfYear + ": " + monthlyRecord.toString());
                while(queryResult.next()) {
                    monthOfYear = queryResult.getInt("year") + "/"
                            + queryResult.getInt("month");
                    System.out.println("Month check: " + monthOfYear);
                    if (!monthOfYear.equals(lastMonthOfYear)) {
                        jsonReport.put(lastMonthOfYear, monthlyRecord);
                        System.out.println(lastMonthOfYear + ": " + monthlyRecord.toString());
                        monthlyRecord = new JSONObject();
                        lastMonthOfYear = monthOfYear;
                    }
                    marketplaceName = queryResult.getString("marketplace_name");
                    countJsonKey = "total" + marketplaceName + "ListingCount";
                    monthlyRecord.put(countJsonKey, queryResult.getInt("count"));
                    sumJsonKey = "total" + marketplaceName + "ListingPrice";
                    monthlyRecord.put(sumJsonKey, queryResult.getDouble("sum"));
                    avgJsonKey = "average" + marketplaceName + "ListingPrice";
                    monthlyRecord.put(avgJsonKey, queryResult.getDouble("avg"));
                }
                jsonReport.put(monthOfYear, monthlyRecord);
            }
        }
        catch (SQLException exception) {
            System.out.println(exception);
        }
    }

}
