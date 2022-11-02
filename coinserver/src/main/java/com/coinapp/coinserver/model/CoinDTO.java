package com.coinapp.coinserver.model;

public class CoinDTO {

    private String apiId;
    private String symbol;
    private String name;
    private Double currentPrice;

    public CoinDTO(String apiId, String symbol, String name, Double currentPrice) {
        this.apiId = apiId;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public CoinDTO() {}

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

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
