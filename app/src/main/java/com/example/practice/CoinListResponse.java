package com.example.practice;

import com.google.gson.annotations.SerializedName;

public class CoinListResponse {
    @SerializedName("data")
    private CoinData[] coins;


    public  CoinData[] getCoins() {
        return coins;
    }

}


