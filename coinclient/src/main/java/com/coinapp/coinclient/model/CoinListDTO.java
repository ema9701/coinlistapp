package com.coinapp.coinclient.model;

public class CoinListDTO {

    private Integer coinId;
    private Integer listId;


    public CoinListDTO(Integer listId, Integer coinId) {
        this.listId = listId;
        this.coinId = coinId;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }


}
