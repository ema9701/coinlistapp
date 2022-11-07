package com.coinapp.coinserver.Controller;


import com.coinapp.coinserver.Dao.CoinDao;
import com.coinapp.coinserver.Dao.ListDao;
import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.Watchlist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/list")
public class ListController {

    private ListDao listDao;
    private CoinDao coinDao;

    public ListController(ListDao listDao, CoinDao coinDao) {
        this.listDao = listDao;
        this.coinDao = coinDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "")
    public List<Watchlist> list() {
        List<Watchlist> lists = listDao.listAll();
        for (Watchlist w : lists) {
            w.setCoinsToWatch(coinDao.getListEntries(w.getListId()));
        }
        return lists;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    public Watchlist getListId(@PathVariable(name = "id") int listId) {
        Watchlist w = listDao.getById(listId);
        if (w == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        w.setCoinsToWatch(coinDao.getListEntries(w.getListId()));
        return w;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Watchlist createNewList(@RequestParam String name) {
        return listDao.createList(name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteList(@PathVariable(name = "id") int listId) {
        Watchlist w = listDao.getById(listId);
        if (w == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        listDao.deleteList(w.getListId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entry")
    public void addCoinsToList(@RequestParam int listId, @RequestParam int coinId) {
        Watchlist w = listDao.getById(listId);
        Coin c = coinDao.getByEntryId(coinId);
        if (w == null || c == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        listDao.addEntry(w.getListId(), c.getCoinId());
        w.setCoinsToWatch(coinDao.getListEntries(w.getListId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{listId}/coin/{coinId}")
    public void deleteEntries(@PathVariable int listId, @PathVariable int coinId) {
        Watchlist w = listDao.getById(listId);
        Coin c = coinDao.getByEntryId(coinId);
        if (w == null || c == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        listDao.removeEntry(listId, coinId);
        w.setCoinsToWatch(coinDao.getListEntries(w.getListId()));
    }
}
