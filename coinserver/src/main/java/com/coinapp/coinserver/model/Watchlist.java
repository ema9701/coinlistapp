package com.coinapp.coinserver.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Watchlist {

    Integer listId;
    String listName;
    List<Coin> coinsToWatch = new ArrayList<>();
//    Map<String, Coin> coinMap = new HashMap<>();

    public Watchlist() {}

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Coin> getCoinsToWatch() {
        return coinsToWatch;
    }

    public void setCoinsToWatch(List<Coin> coinsToWatch) {
        this.coinsToWatch = coinsToWatch;
    }

//    public Map<String, Coin> getCoinMap() {
//        return coinMap;
//    }
//
//    public void setCoinMap(Map<String, Coin> coinMap) {
//        this.coinMap = coinMap;
//    }

    @Override
    public String toString() {
        return listId + "." + listName;
    }

}
