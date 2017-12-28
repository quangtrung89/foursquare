package com.trung.foursquare.presenter;

import android.support.test.runner.AndroidJUnit4;

import com.trung.foursquare.data.interaction.OnResponseListener;
import com.trung.foursquare.data.interaction.VenuesInteraction;
import com.trung.foursquare.data.model.Venue;
import com.trung.foursquare.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class MainPresenterTest {
    @Mock
    private VenuesInteraction mVenuesInteraction;
    @Mock
    private MainView mMainView;
    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                OnResponseListener listener = invocation.getArgument(3);
                List<Venue> testData = new ArrayList<>();
                testData.add(new Venue("123", "abc", "xyz", 12));
                testData.add(new Venue("1234", "abc1", "xyz1", 121));
                listener.onSuccess(testData);
                return null;
            }
        }).when(mVenuesInteraction).searchVenues(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString(), any(OnResponseListener.class));
        mMainPresenter = new MainPresenterImpl(mMainView, mVenuesInteraction);
    }

    @Test
    public void testSearch() throws Exception {
        mMainPresenter.search(60.1663601, 24.6529858, "ravintola");
        Thread.sleep(3000);
        verify(mVenuesInteraction).searchVenues(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString(), any(OnResponseListener.class));
        verify(mMainView).showData(ArgumentMatchers.<Venue>anyList());
    }
}
