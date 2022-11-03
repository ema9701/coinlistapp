package com.coinapp.coinserver.Dao;

import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.Watchlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcListDao implements ListDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcListDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Watchlist> listAll() {
        List<Watchlist> lists = new ArrayList<>();
        final String sql = " SELECT list_id, list_name FROM watchlist; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            lists.add(mapRowToWatchlist(results));
        }
        return lists;
    }

    @Override
    public Watchlist getById(int listId) {
        Watchlist w = null;
        final String sql = " SELECT list_id, list_name FROM watchlist WHERE " +
                " list_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, listId);
        if (result.next()) {
            w = mapRowToWatchlist(result);
        }
        return w;
    }

    @Override
    public Watchlist createList(String name) {
        final String sql = " INSERT INTO watchlist (list_name) " +
                " VALUES (?) RETURNING list_id; ";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return getById(newId);
    }

    @Override
    public void deleteList(int listId) {
        final String sql = " DELETE FROM watchlist WHERE list_id = ?; ";
        jdbcTemplate.update(sql, listId);
    }


    @Override
    public boolean addEntry(int listId, int coinId) {
        final String sql = " INSERT INTO watchlist_coin (watchlist_id, coin_id) " +
                " VALUES (?, ?); ";

        return jdbcTemplate.update(sql, listId, coinId) == 1;
    }

    @Override
    public void removeEntry(int listId, int coinId) {
        final String sql = " DELETE FROM watchlist_coin WHERE " +
                " coin_id = ? AND watchlist_id = ?; ";
        jdbcTemplate.update(sql, listId, coinId);
    }


    private Watchlist mapRowToWatchlist(SqlRowSet rs) {
        Watchlist w = new Watchlist();
        w.setListId(rs.getInt("list_id"));
        w.setListName(rs.getString("list_name"));

        return w;
    }

}
