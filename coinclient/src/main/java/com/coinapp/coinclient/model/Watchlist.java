package com.coinapp.coinclient.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Watchlist {

    Integer id;
    String name;
    Map<Integer, Coin> savedCoins = new HashMap<>();

    public Watchlist() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Integer, Coin> getSavedCoins() {
        return savedCoins;
    }

    public void setSavedCoins(Map<Integer, Coin> savedCoins) {
        this.savedCoins = savedCoins;
    }
}
