package com.coinapp.coinclient.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Watchlist {

    Integer listId;
    String listName;
    List<Coin> coinsToWatch = new ArrayList<>();

    public Watchlist() {}

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public List<Coin> getCoinsToWatch() {
        return coinsToWatch;
    }

    public void setCoinsToWatch(List<Coin> coinsToWatch) {
        this.coinsToWatch = coinsToWatch;
    }

    @Override
    public String toString() {
        return getListId() + "." + getListName();
    }


}
