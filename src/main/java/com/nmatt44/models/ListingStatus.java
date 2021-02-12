package com.nmatt44.models;

public class ListingStatus {

    private int id;
    private String statusName;

    public ListingStatus(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }
}
