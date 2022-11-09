package com.coinapp.coinclient.services;

import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.CoinListDTO;
import com.coinapp.coinclient.model.Watchlist;
import com.coinapp.coinclient.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.crypto.Data;

@Component
public class WatchlistService {

    private static final String LIST_URL = "http://localhost:8080/list";
    private final RestTemplate restTemplate = new RestTemplate();


    public Watchlist[] getAllLists() {
        Watchlist[] lists = null;
        try {
            lists = restTemplate.getForObject(LIST_URL, Watchlist[].class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return lists;
    }

    public Watchlist getByListId(int listId) {
        Watchlist w = null;
        try {
            w = restTemplate.getForObject(LIST_URL + "/" + listId, Watchlist.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return w;
    }

    public Watchlist createNewList(String name) {
        Watchlist response = null;
        Watchlist w = new Watchlist();
        w.setListName(name);
        try {
            response = restTemplate.postForObject(LIST_URL + "?name=" + name, makeListEntity(w), Watchlist.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return response;
    }

    public Watchlist saveCoinToList(int listId, int coinId) {
        Watchlist watchlist = null;
        try {
            ResponseEntity<Watchlist> response =
            restTemplate.exchange(LIST_URL + "/entry", HttpMethod.POST,
                    makeCoinListEntity(listId, coinId), Watchlist.class);
            watchlist = response.getBody();
        } catch (RestClientResponseException | ResponseStatusException e) {
            BasicLogger.log(e.getMessage());
        }
        return watchlist;
    }

    public boolean deleteList(int listId) {
        boolean success = false;
        try {
            restTemplate.delete(LIST_URL + "/" + listId);
            success = true;
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean deleteCoinOnList(int listId, int coinId) {
        boolean success = false;
        try {
            restTemplate.delete(LIST_URL + "/"+ listId + "/coin/" + coinId);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }


    private HttpEntity<CoinListDTO> makeCoinListEntity(int listId, int coinId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(new CoinListDTO(listId, coinId), headers);
    }

    private HttpEntity<Watchlist> makeListEntity(Watchlist newList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(newList, headers);
    }
}
