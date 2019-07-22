package com.qourier.technicaltest.question2.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hoa Nguyen on Jul 21 2019.
 */
public class RestApiFactory {
    public static String API_KEY = "1f5b86b5d2ec241ec5863bc7941fad12";
    private static volatile RestApiFactory _instance;
    /**
     * Sample api
     * https://api.themoviedb.org/3/discover/movie?api_key=1f5b86b5d2ec241ec5863bc7941fad12&page=1
     */
    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private RestApi restApi;

    public RestApiFactory() {
        restApi = create();
    }

    private static void init() {
        _instance = new RestApiFactory();
    }

    public static RestApiFactory newInstance() {
        if (_instance == null) {
            synchronized (RestApiFactory.class) {
                if (_instance == null) {
                    init();
                }
            }
        }
        return _instance;
    }

    public RestApi getRestApi() {
        return restApi;
    }

    private RestApi create() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(RestApi.class);
    }
}
