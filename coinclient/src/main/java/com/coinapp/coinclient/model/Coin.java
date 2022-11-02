package com.coinapp.coinclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {

    private Integer coinId;
    @JsonProperty("id")
    private String apiId;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("market_data")
    private MarketData marketData;
    private Double currentPrice;

    public Coin() {
    }

    public Coin(Integer coinId, String symbol, String name, Double currentPrice) {
        this.coinId = coinId;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
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

    public MarketData getMarketData() {
        return marketData;
    }

    public void setMarketData(MarketData marketData) {
        this.marketData = marketData;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return " Coin ID: " + apiId +
                "\n Coin Name: " + name +
                "\n Coin Ticker: " + symbol +
                "\n Current Price: " + marketData.getCurrentPrice().get("usd") +
                "\n 24-hour Price change: " + marketData.getPriceChange24() +
                "\n 24-hour Low Price: " + marketData.getLow24h().get("usd") +
                "\n 24-hour High Price: " + marketData.getHigh24h().get("usd");
    }


}
