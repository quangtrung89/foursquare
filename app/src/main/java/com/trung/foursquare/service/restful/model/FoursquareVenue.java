package com.trung.foursquare.service.restful.model;


public class FoursquareVenue {
    private String id;
    private String name;
    private FoursquareLocation location;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FoursquareLocation getLocation() {
        return location;
    }

    public String getAddress() {
        if (location == null) {
            return "";
        }
        return location.getAddress();
    }

    public int getDistance() {
        if (location == null) {
            return 0;
        }
        return location.getDistance();
    }
}
