package com.coinapp.coinserver.Controller;


import com.coinapp.coinserver.Dao.CoinDao;
import com.coinapp.coinserver.Services.CoinService;
import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.CoinDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    private CoinDao coinDao;
    private CoinService coinService;

    public CoinController(CoinDao coinDao, CoinService coinService) {
        this.coinDao = coinDao;
        this.coinService = coinService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/gecko")
    public Coin searchForCoin(@RequestParam(value = "id") String apiId) {
        return coinService.getCoinById(apiId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "")
    public List<Coin> list() {
        return coinDao.listEntries();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    public Coin getFromDatabase(@PathVariable int id) {
        Coin coin = coinDao.getByEntryId(id);
        if (coin == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return coin;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Coin saveNewCoin(@RequestBody CoinDTO coinDTO) {
        Integer newId = coinDao.createEntry(coinDTO);
        if (coinDao.createEntry(coinDTO) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return coinDao.getByEntryId(newId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{id}")
    public Coin updateCoin(@RequestBody Coin updatedCoin, @PathVariable int id) {
        Coin c = coinDao.getByEntryId(id);
        if (!coinDao.updateCoinData(updatedCoin, c.getCoinId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return c;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteCoin(@PathVariable int id) {
        Coin coin = coinDao.getByEntryId(id);
            if (coin == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        coinDao.deleteEntry(coin.getCoinId());
    }

}
