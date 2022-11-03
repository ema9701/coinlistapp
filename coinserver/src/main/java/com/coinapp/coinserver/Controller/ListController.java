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
@RequestMapping("/watchlist")
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
        return listDao.listAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    public Watchlist getListId(@PathVariable(name = "id") int listId) {
        if (listId == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listDao.getById(listId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/entries")
    public List<Coin> coinsByListId(@PathVariable(name="id") int listId) {
        Watchlist w = listDao.getById(listId);
        if (w == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        w.setCoinsToWatch(coinDao.getListEntries(listId));
        return w.getCoinsToWatch();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public boolean createNewList(@RequestParam String name) {
        boolean success = false;
        Watchlist newList = listDao.createList(name);
        if (newList != null) {
            success = true;
        }
        return success;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteList(@PathVariable(name="id") int listId) {
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
        w.getCoinsToWatch().add(c);
        listDao.addEntry(w.getListId(), c.getCoinId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{listId}/coin/{coinId}")
    public void deleteEntries(@PathVariable int listId, @PathVariable int coinId) {
        Watchlist w = listDao.getById(listId);
        Coin c = coinDao.getByEntryId(coinId);
        if (w == null || c == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        w.getCoinsToWatch().remove(c);
        listDao.removeEntry(listId, coinId);
    }
}
