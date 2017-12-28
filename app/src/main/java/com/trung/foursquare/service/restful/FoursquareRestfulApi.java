package com.trung.foursquare.service.restful;

import com.trung.foursquare.service.restful.model.SearchVenuesResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface FoursquareRestfulApi {
    @GET("venues/search")
    Call<SearchVenuesResponse> searchVenues(@Query("client_id") String clientId,
                                            @Query("client_secret") String clientSecret,
                                            @Query("ll") String intent,
                                            @Query("query") String keyword,
                                            @Query("v") String version);

    class Factory {
        static FoursquareRestfulApi create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.foursquare.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(FoursquareRestfulApi.class);
        }
    }
}
