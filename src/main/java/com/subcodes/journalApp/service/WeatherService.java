package com.subcodes.journalApp.service;

import com.subcodes.journalApp.apiresponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_Key = "d2ac298bbf2b2e2ab93af40be826c67b";

    private static final String API = "https://api.weatherstack.com/current?access_key=API_Key&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_Key", API_Key);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
