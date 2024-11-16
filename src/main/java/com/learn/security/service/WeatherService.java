package com.learn.security.service;

import com.learn.security.entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {


    RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String APIKEY = "f45df24eae8d4294b6f63520241611";
    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY";

    public WeatherResponse getWeather(String city) {
        String finalUrl = URL.replace("CITY", city).replace("API_KEY", APIKEY);
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponse.class);
        return responseEntity.getBody();
    }

}
