package com.inn.restuarant.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static ResponseEntity<String> getResponseEntity (String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>(String.format("{\"message\":\"%s\"}", responseMessage), httpStatus);
    }
}
