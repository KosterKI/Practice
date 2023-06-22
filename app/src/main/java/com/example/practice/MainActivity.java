package com.example.practice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CoinCapApiClient coinCapApiClient;
    private RecyclerView recyclerView;
    private CoinListAdapter coinListAdapter;

    private int currentPage = 1;
    private int pageSize = 12;

    private Button btnPrevious;
    private Button btnNext;
    private TextView textCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinCapApiClient = new CoinCapApiClient();
        List<CoinData> coins = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinListAdapter = new CoinListAdapter(MainActivity.this, coins);
        recyclerView.setAdapter(coinListAdapter);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        textCurrentPage = findViewById(R.id.textCurrentPage);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1) {
                    updatePageContent(currentPage - 1);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePageContent(currentPage + 1);
            }
        });

        fetchCoinList(currentPage, pageSize);
    }

    private void fetchCoinList(int page, int limit) {
        coinCapApiClient.getCoinList(page, limit, new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {
                if (response.isSuccessful()) {
                    CoinListResponse coinListResponse = response.body();
                    CoinData[] coins = coinListResponse.getCoins();

                    int startIndex = (page - 1) * pageSize;
                    int endIndex = Math.min(startIndex + pageSize, coins.length);

                    List<CoinData> limitedCoins = Arrays.asList(coins).subList(startIndex, endIndex);

                    coinListAdapter.setData(limitedCoins);
                    updatePagination();
                } else {
                    Log.e(TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
    private void updatePageContent(int page) {
        currentPage = page;
        fetchCoinList(currentPage, pageSize);
    }

    private void updatePagination() {
        textCurrentPage.setText(getString(R.string.page, currentPage));

        if (currentPage > 1) {
            btnPrevious.setEnabled(true);
        } else {
            btnPrevious.setEnabled(false);
        }

        if (coinListAdapter.getItemCount() < pageSize) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
    }
}