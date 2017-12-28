package com.trung.foursquare.data.interaction;

import com.trung.foursquare.data.model.Venue;

import java.util.List;

public interface OnResponseListener {
    void onSuccess(List<Venue> items);

    void onFailure(String error);
}