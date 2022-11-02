package com.coinapp.coinclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinList {

    @JsonProperty("id")
    private String id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;

    public CoinList() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " Coin ID: " + id +
        "\n Coin symbol: " + symbol +
        "\n Coin name: " + name;
    }
}
