package com.trung.foursquare.data.model;

public class Venue {
    private String id;
    private String name;
    private String address;
    private int distance;

    public Venue(String id, String name, String address, int distance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return name + " | " + address + " | " + distance;
    }
}
