package com.trung.foursquare.service.restful;

import com.trung.foursquare.service.restful.model.SearchVenuesResponse;

import retrofit2.Call;

public class RestfulService {
    private final String CLIENT_ID = "";
    private final String CLIENT_SECRET = "";
    private final String API_VERSION = "20171226";

    private static RestfulService sInstance;
    private FoursquareRestfulApi mFoursquareRestfulApi;

    private RestfulService() {
        mFoursquareRestfulApi = FoursquareRestfulApi.Factory.create();
    }

    public static RestfulService getInstance() {
        if(sInstance == null) {
            sInstance = new RestfulService();
        }
        return sInstance;
    }

    public Call<SearchVenuesResponse> searchVenues(String intent, String keyword) {
        return mFoursquareRestfulApi.searchVenues(CLIENT_ID, CLIENT_SECRET, intent, keyword, API_VERSION);
    }
}
