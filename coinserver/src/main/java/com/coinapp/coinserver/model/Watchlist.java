package com.coinapp.coinserver.model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Watchlist {

    Integer listId;
    @NotBlank(message = "New list must have a name")
    String listName;
    List<Coin> coinsToWatch = new ArrayList<>();


    public Watchlist() {
    }

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

    @Override
    public String toString() {
        return listId + "." + listName;
    }

}
