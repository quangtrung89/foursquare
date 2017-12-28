package com.trung.foursquare.data.interaction;

import com.trung.foursquare.data.model.Venue;
import com.trung.foursquare.service.restful.RestfulService;
import com.trung.foursquare.service.restful.model.FoursquareVenue;
import com.trung.foursquare.service.restful.model.SearchVenuesResponse;
import com.trung.foursquare.service.restful.model.VenuesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenuesInteractionImpl implements VenuesInteraction {

    @Override
    public void searchVenues(final double lat, final double lng, final String keyword, final OnResponseListener listener) {
        if (listener == null) {
            return;
        }
        Call<SearchVenuesResponse> call = RestfulService.getInstance().searchVenues(lat+","+lng, keyword);
        call.enqueue(new Callback<SearchVenuesResponse>() {
            @Override
            public void onResponse(Call<SearchVenuesResponse> call, Response<SearchVenuesResponse> response) {
                SearchVenuesResponse data = response.body();
                List<Venue> results = new ArrayList<>();
                if (data == null) {
                    listener.onSuccess(results);
                    return;
                }
                VenuesResponse venuesResponse = data.getResponse();
                if (venuesResponse == null) {
                    listener.onSuccess(results);
                    return;
                }
                List<FoursquareVenue> venues = venuesResponse.getVenues();
                for (FoursquareVenue item : venues) {
                    Venue venue = new Venue(item.getId(), item.getName(), item.getAddress(), item.getDistance());
                    results.add(venue);
                }
                listener.onSuccess(results);
            }

            @Override
            public void onFailure(Call<SearchVenuesResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
