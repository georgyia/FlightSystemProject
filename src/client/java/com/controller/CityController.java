package client.java.com.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import common.java.root.model.city.City;
import common.java.root.model.city.POI;

import java.util.List;

public class CityController {
    private final WebClient webClient;
    private List<City> cities;

    public CityController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://131.159.218.10:8888/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        webClient.get()
                .uri("city")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<City>>() {})
                .onErrorStop()
                .subscribe((cityList) -> {
                    cities = cityList;
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> getCities()
    {
        return cities;
    }
}
