package com.coinapp.coinserver.Services;

import com.coinapp.coinserver.model.Coin;
import com.coinapp.coinserver.util.BasicLogger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCoinService implements CoinService {

    private static final String API_BASE_URL = "https://api.coingecko.com/api/v3/coins/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Coin getCoinById(String id) {
        Coin coin = null;
        try {
            coin = restTemplate.getForObject(API_BASE_URL + id, Coin.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return coin;
    }

}
