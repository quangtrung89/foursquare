package com.trung.foursquare.data;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.trung.foursquare.data.interaction.OnResponseListener;
import com.trung.foursquare.data.interaction.VenuesInteraction;
import com.trung.foursquare.data.interaction.VenuesInteractionImpl;
import com.trung.foursquare.data.model.Venue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class VenuesInteractionTest {
    VenuesInteraction mInteraction;

    @Before
    public void setUp() throws Exception {
        mInteraction = new VenuesInteractionImpl();
    }

    @Test
    public void testSearchVenues() throws Exception {
        mInteraction.searchVenues(60.1663601, 24.6529858, "ravintola", new OnResponseListener() {
            @Override
            public void onSuccess(List<Venue> items) {
                assertFalse("Search success", items.isEmpty());
            }

            @Override
            public void onFailure(String error) {
                assertFalse("Search failure", TextUtils.isEmpty(error));
            }
        });
    }
}
