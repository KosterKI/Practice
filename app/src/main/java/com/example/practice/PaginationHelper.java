package com.example.practice;

import java.util.List;

public class PaginationHelper {

    private static final int ITEMS_PER_PAGE = 15;

    private List<CoinData> coins;
    private int currentPage;

    public PaginationHelper(List<CoinData> coins) {
        this.coins = coins;
        this.currentPage = 0;
    }

    public List<CoinData> getCurrentPageItems() {
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, coins.size());
        return coins.subList(startIndex, endIndex);
    }

    public boolean hasNextPage() {
        int totalPages = getTotalPages();
        return currentPage < totalPages - 1;
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public void goToNextPage() {
        if (hasNextPage()) {
            currentPage++;
        }
    }

    public void goToPreviousPage() {
        if (hasPreviousPage()) {
            currentPage--;
        }
    }

    public void goToPage(int page) {
        int totalPages = getTotalPages();
        if (page >= 0 && page < totalPages) {
            currentPage = page;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        int totalItems = coins.size();
        return (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    }
}