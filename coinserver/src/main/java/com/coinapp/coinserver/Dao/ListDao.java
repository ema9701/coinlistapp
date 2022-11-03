package com.coinapp.coinserver.Dao;

import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.Watchlist;

import java.util.List;

public interface ListDao {

    List<Watchlist> listAll();

    Watchlist getById(int listId);

    Watchlist createList(String name);

    void deleteList(int listId);

    boolean addEntry(int listId, int coinId);

    void removeEntry(int listId, int coinId);
}
