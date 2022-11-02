package com.coinapp.coinclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)

public class MarketData {

    @JsonProperty("current_price")
    private Map<String, Double> currentPrice;
    @JsonProperty("high_24h")
    private Map<String, Double> high24h;
    @JsonProperty("low_24h")
    private Map<String, Double> low24h;
    @JsonProperty("price_change_24h")
    private double priceChange24;

    public MarketData() {

    }

    public Map<String, Double> getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Map<String, Double> currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Map<String, Double> getHigh24h() {
        return high24h;
    }

    public void setHigh24h(Map<String, Double> high24h) {
        this.high24h = high24h;
    }

    public Map<String, Double> getLow24h() {
        return low24h;
    }

    public void setLow24h(Map<String, Double> low24h) {
        this.low24h = low24h;
    }

    public double getPriceChange24() {
        return priceChange24;
    }

    public void setPriceChange24(double priceChange24) {
        this.priceChange24 = priceChange24;
    }
}
