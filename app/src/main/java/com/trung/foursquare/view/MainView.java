package com.trung.foursquare.view;

import com.trung.foursquare.data.model.Venue;

import java.util.List;

public interface MainView {
    void showData(List<Venue> data);
    void showMessage(String message);
}
