package com.coinapp.coinserver.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ListCoinDTO {

    @Min(value = 1, message = "must be a valid list id")
    private Integer listId;
    @Min(value = 1, message = "must be a valid coin id")
    private Integer coinId;

    public ListCoinDTO() {}

    public ListCoinDTO(Integer listId, Integer coinId) {
        this.listId = listId;
        this.coinId = coinId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }



}
