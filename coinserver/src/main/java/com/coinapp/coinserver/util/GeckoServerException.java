package com.coinapp.coinserver.util;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Invalid id entered for query")
public class GeckoServerException extends RuntimeException{
}
