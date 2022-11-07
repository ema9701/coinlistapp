package com.coinapp.coinclient.services;


import com.coinapp.coinclient.model.Coin;
import com.coinapp.coinclient.model.CoinDTO;
import com.coinapp.coinclient.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class CoinService {

    private static final String COIN_URL = "http://localhost:8080/coins/";
    private final RestTemplate restTemplate = new RestTemplate();


    public Coin[] listSavedCoins() {
        Coin[] coins = null;
        try {
            coins = restTemplate.getForObject(COIN_URL, Coin[].class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return coins;
    }

    public Coin searchCoinOnGecko(String id) {
        Coin coin = null;
        try {
            coin = restTemplate.getForObject(COIN_URL + "gecko?id=" +id, Coin.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return coin;
    }

    public Coin getCoinDetails(Integer coinId) {
        Coin coin = null;
        try {
            coin = restTemplate.getForObject(COIN_URL + coinId, Coin.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return coin;
    }

    public Coin saveCoin(CoinDTO coinDTO) {
        Coin coin = null;
        try {
            ResponseEntity<Coin> response =
            restTemplate.exchange(COIN_URL,HttpMethod.POST, makeCoinDTOEntity(coinDTO), Coin.class);
            coin = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return coin;
    }

    public boolean updateCoinData(Coin coin) {
        boolean success = false;
        try {
            restTemplate.put(COIN_URL + coin.getApiId(), makeCoinEntity(coin));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean deleteCoinEntry(Integer coinId) {
        boolean success = false;
        try {
            restTemplate.delete(COIN_URL + coinId, makeCoinEntity(getCoinDetails(coinId)));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }


    private HttpEntity<CoinDTO> makeCoinDTOEntity(CoinDTO coin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(coin, headers);
    }

    private HttpEntity<Coin> makeCoinEntity(Coin coin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(coin, headers);
    }

}
