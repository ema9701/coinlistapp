package com.coinapp.coinserver.util;

import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Coin could not be found")
public class CoinNotFoundException extends RuntimeException {
}
