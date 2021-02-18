package com.nmatt44.service;

import kong.unirest.json.JSONObject;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reporter {

    private final QueryTool queryTool = new QueryTool();
    private Connection dbConnection;

    public Reporter(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void generateReport() throws IOException {
        JSONObject jsonReport = new JSONObject();
        addSimpleData(jsonReport);
        addDataByMarketplace(jsonReport);
        try {
            addMonthlyData2(jsonReport);
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        File reportOutputFile = new File(
                "C:/Users/Lenovo/IdeaProjects/JavaTesztfeladat/src/main/report/report.json"
        );
        reportOutputFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(reportOutputFile);
        printWriter.write(jsonReport.toString());
        printWriter.close();
        FileInputStream reportOutputFileStream = new FileInputStream(
                "C:/Users/Lenovo/IdeaProjects/JavaTesztfeladat/src/main/report/report.json");
        try {
            uploadReportToFTP(reportOutputFileStream);
        } catch (IOException exception) {
            System.out.println(exception);
        }
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

    private void addMonthlyData2(JSONObject jsonReport) throws SQLException {
            ResultSet queryResult = queryTool.selectMonthlyReportDataByMarketplace(dbConnection);
            JSONObject allMonthlyReports = new JSONObject();
            monthlyDataGeneratorFromQuery(queryResult, allMonthlyReports);
            jsonReport.put("monthlyReports", allMonthlyReports);
    }

    private void monthlyDataGeneratorFromQuery(ResultSet queryResult, JSONObject monthlyReports) throws SQLException {
        if(queryResult.next()) {
            JSONObject reportOfOneMonth = new JSONObject();
            String monthFromRow = queryResult.getInt("year") + "/"
                    + queryResult.getInt("month");
            String bestListerEmail = queryTool.findMonthlyBestListerEmail(
                    dbConnection,
                    queryResult.getInt("year"),
                    queryResult.getInt("month")
                    );
            addMonthlyRecordRowsByMarketplace(queryResult, reportOfOneMonth);
            while(queryResult.next()) {
                String nextMonthFromRow = queryResult.getInt("year") + "/"
                        + queryResult.getInt("month");
                if (!nextMonthFromRow.equals(monthFromRow)) {
                    reportOfOneMonth.put("monthlyBestLister", bestListerEmail);
                    monthlyReports.put(monthFromRow, reportOfOneMonth);
                    reportOfOneMonth = new JSONObject();
                    monthFromRow = nextMonthFromRow;
                    bestListerEmail = queryTool.findMonthlyBestListerEmail(
                            dbConnection,
                            queryResult.getInt("year"),
                            queryResult.getInt("month")
                    );
                    addMonthlyRecordRowsByMarketplace(queryResult, reportOfOneMonth);
                }
                addMonthlyRecordRowsByMarketplace(queryResult, reportOfOneMonth);
            }
            reportOfOneMonth.put("monthlyBestLister", bestListerEmail);
            monthlyReports.put(monthFromRow, reportOfOneMonth);
        }
    }

    private void addMonthlyRecordRowsByMarketplace(ResultSet queryResult, JSONObject monthlyRecord) throws SQLException {
        String marketplaceName = queryResult.getString("marketplace_name");
        String countJsonKey = "total" + marketplaceName + "ListingCount";
        monthlyRecord.put(countJsonKey, queryResult.getInt("count"));
        String sumJsonKey = "total" + marketplaceName + "ListingPrice";
        monthlyRecord.put(sumJsonKey, queryResult.getDouble("sum"));
        String avgJsonKey = "average" + marketplaceName + "ListingPrice";
        monthlyRecord.put(avgJsonKey, queryResult.getDouble("avg"));
    }

    private void addMonthlyBestListerRow(ResultSet queryResult, JSONObject monthlyRecord) throws SQLException {
        String bestListerEmail = queryTool.findMonthlyBestListerEmail(
                dbConnection,
                queryResult.getInt("year"),
                queryResult.getInt("month"));
        monthlyRecord.put("monthlyBestLister", bestListerEmail);
    }

    public void uploadReportToFTP(FileInputStream reportOutputFile) throws IOException {
        FTPClient client = new FTPClient();
        client.connect("127.0.0.1");
        client.login("wobtest", "wobtest96");
        client.storeFile("report.json", reportOutputFile);
    }

}
