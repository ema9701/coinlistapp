package com.coinapp.coinserver.Dao;

import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.CoinDTO;

import java.util.List;

public interface CoinDao {

    List<Coin> listEntries();

    Coin getByEntryId(int coinId);

    Integer createEntry(Coin newCoin);

    boolean updateCoinData(Coin coin, int coinId);

    boolean deleteEntry(int coinId);
}
