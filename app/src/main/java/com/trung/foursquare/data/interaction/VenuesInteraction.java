package com.trung.foursquare.data.interaction;

public interface VenuesInteraction {

    void searchVenues(double lat, double lng, String keyword, OnResponseListener listener);
}
