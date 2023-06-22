package com.example.practice;

import java.lang.reflect.Array;
import java.util.Arrays;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class CoinCapApiClient {
    private static final String BASE_URL = "https://api.coincap.io/v2/";

    private CoinCapApiService apiService;

    public CoinCapApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(CoinCapApiService.class);
    }

    public void getCoinList(int page, int limit, Callback<CoinListResponse> callback) {
        Call<CoinListResponse> call = apiService.getCoinList();
        call.enqueue(callback);
    }


    private interface CoinCapApiService {
        @GET("assets")
        Call<CoinListResponse> getCoinList();

        @GET("assets/{coinId}")
        Call<CoinDetailsResponse> getCoinDetails(@Path("coinId") String coinId);
    }

}