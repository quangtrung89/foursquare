package com.trung.foursquare.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.trung.foursquare.data.interaction.OnResponseListener;
import com.trung.foursquare.data.model.Venue;
import com.trung.foursquare.data.interaction.VenuesInteraction;
import com.trung.foursquare.view.MainView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenterImpl implements MainPresenter, OnResponseListener {
    private static final int TIME_SEARCH_DELAY = 1000;  // 2s
    private static final int TRIGGER_SEARCH = 1;

    private final WeakReference<MainView> mMainView;
    private final VenuesInteraction mVenuesInteraction;
    private Handler mSearchHandler;

    public MainPresenterImpl(MainView mainView, VenuesInteraction venuesInteraction) {
        mMainView = new WeakReference<>(mainView);
        mVenuesInteraction = venuesInteraction;
        mSearchHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TRIGGER_SEARCH:
                        SearchLocation obj = (SearchLocation)msg.obj;
                        mVenuesInteraction.searchVenues(obj.lat, obj.lng, obj.keyword, MainPresenterImpl.this);
                        break;
                }
            }
        };
    }

    @Override
    public void search(double lat, double lng, String keyword) {
        if(keyword.length() < 3) {
            return;
        }
        mSearchHandler.removeMessages(TRIGGER_SEARCH);
        SearchLocation obj = new SearchLocation();
        obj.lat = lat;
        obj.lng = lng;
        obj.keyword = keyword;
        Message msg = mSearchHandler.obtainMessage(TRIGGER_SEARCH, obj);
        mSearchHandler.sendMessageDelayed(msg, TIME_SEARCH_DELAY);
    }

    @Override
    public void detachView() {
        mMainView.clear();
    }

    @Override
    public void onSuccess(List<Venue> items) {
        mMainView.get().showData(items);
    }

    @Override
    public void onFailure(String error) {
        mMainView.get().showMessage(error);
    }

    private static class SearchLocation {
        double lat;
        double lng;
        String keyword;
    }
}
