package com.coinapp.coinserver.Dao;

import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.CoinDTO;
import com.coinapp.coinserver.model.Watchlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCoinDao implements CoinDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCoinDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Coin> listEntries() {
        List<Coin> coins = new ArrayList<>();
        final String sql = " SELECT coin_id, symbol, coin_name, current_price FROM coin; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            coins.add(mapRowToCoin(results));
        }
        return coins;
    }

    @Override
    public Coin getByEntryId(int coinId) {
        Coin coin = null;
        final String sql = " SELECT coin_id, symbol, coin_name, current_price FROM coin " +
                " WHERE coin_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, coinId);
        if (result.next()) {
            coin = mapRowToCoin(result);
        }
        return coin;
    }

    @Override
    public Integer createEntry(Coin newCoin) {
        final String sql = " INSERT INTO coin (symbol, coin_name, current_price) " +
                " VALUES (?, ?, ?) RETURNING coin_id; ";
        Integer newCoinId = jdbcTemplate.queryForObject(sql, Integer.class,
                newCoin.getSymbol(), newCoin.getName(), newCoin.getCurrentPrice());
        return newCoinId;
    }

    @Override
    public boolean updateCoinData(Coin coin, int coinId) {
        final String sql = " UPDATE coin SET symbol = ?, coin_name = ?, current_price = ? " +
                "WHERE coin_id = ?; ";
        return jdbcTemplate.update(sql, coin.getSymbol(),
                coin.getName(), coin.getCurrentPrice(), coin.getCoinId()) == 1;
    }

    @Override
    public boolean deleteEntry(int coinId) {
        final String sql = " DELETE FROM coin WHERE coin_id = ?; ";
        return jdbcTemplate.update(sql, coinId) == 1;
    }

    @Override
    public List<Coin> getListEntries(int listId) {
        List<Coin> savedCoins = new ArrayList<>();
        final String sql = " SELECT watchlist_coin.coin_id, symbol, coin_name, current_price FROM coin " +
                " JOIN watchlist_coin ON coin.coin_id = watchlist_coin.coin_id " +
                " WHERE watchlist_coin.watchlist_id = ?;  ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId);
        while (results.next()) {
            savedCoins.add(mapRowToCoin(results));
        }
        return savedCoins;
    }

    private Coin mapRowToCoin(SqlRowSet rs) {
        Coin coin = new Coin();
        coin.setCoinId(rs.getInt("coin_id"));
        coin.setSymbol(rs.getString("symbol"));
        coin.setName(rs.getString("coin_name"));
        coin.setCurrentPrice(rs.getDouble("current_price"));
        return coin;
    }

}
