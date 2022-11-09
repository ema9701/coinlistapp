package com.coinapp.coinserver.Controller;


import com.coinapp.coinserver.Dao.CoinDao;
import com.coinapp.coinserver.Dao.ListDao;
import com.coinapp.coinserver.Services.CoinService;
import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.model.CoinDTO;
import com.coinapp.coinserver.model.ListCoinDTO;
import com.coinapp.coinserver.model.Watchlist;
import com.coinapp.coinserver.util.GeckoServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import javax.validation.Valid;
import java.nio.file.InvalidPathException;
import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    private CoinDao coinDao;
    private ListDao listDao;
    private CoinService coinService;

    public CoinController(CoinDao coinDao, ListDao listDao, CoinService coinService) {
        this.coinDao = coinDao;
        this.listDao = listDao;
        this.coinService = coinService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/gecko")
    public Coin searchForCoin(@RequestParam(value = "id") String apiId) {
        Coin coin = null;
        try {
            coin = coinService.getCoinById(apiId);
        } catch (InvalidPathException e) {
            System.out.println("\nCoin could not be found at path");
        }
        return coin;
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
    public Coin saveNewCoin(@Valid @RequestBody CoinDTO coinDTO) {
        Coin newCoin = DTOConv(coinDTO);
        Integer newId = coinDao.createEntry(newCoin);
        if (coinDao.getByEntryId(newId) == null) {
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

    private Coin DTOConv(CoinDTO dto) {
        Coin coin = new Coin();
        coin.setSymbol(dto.getSymbol());
        coin.setName(dto.getName());
        coin.setCurrentPrice(dto.getCurrentPrice());
        return coin;
    }

}
