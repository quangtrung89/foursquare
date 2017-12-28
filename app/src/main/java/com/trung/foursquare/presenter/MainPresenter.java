package com.trung.foursquare.presenter;

public interface MainPresenter {
    void search(double lat, double lng, String keyword);
    void detachView();
}
