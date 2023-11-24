package client.java.com.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import common.java.root.model.city.POI;
import common.java.root.model.customer.Customer;
import common.java.root.model.flight.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerController {
    private final WebClient webClient;
    private Customer currentCustomer;

    public CustomerController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://131.159.218.10:8888/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Customer getCurrentCustomer()
    {
        return currentCustomer;
    }

    public void resetCurrentCustomer()
    {
        currentCustomer=null;
    }

    public Customer signUp(Customer customer) {
        // DONE Part 2: Make an http post request to the server
        webClient.post()
                .uri("customer/signUp")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorStop()
                .subscribe((newCustomer) -> {
                    currentCustomer = newCustomer;
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return currentCustomer;
    }

    public Customer signIn(Customer customer) {
        // DONE Part 2: Make an http post request to the server
        webClient.post()
                .uri("customer/signIn")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorStop()
                .subscribe((newCustomer) -> {
                    currentCustomer = newCustomer;
                });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return currentCustomer;
    }

    public void addPOI(POI poi) {
        webClient.put()
                .uri(uriBuilder -> uriBuilder
                    .path("customer/addPOI")
                    .queryParam("user_id", currentCustomer.getId())
                    .queryParam("poi_id", poi.getId())
                    .build())
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorStop()
                .subscribe((newCustomer) -> {
                    currentCustomer = newCustomer;
                });

    }

    public void addFlight(Flight flight) {
        webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("customer/addFlight")
                        .queryParam("user_id", currentCustomer.getId())
                        .queryParam("flight_id", flight.getId())
                        .build())
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorStop()
                .subscribe((newCustomer) -> {
                    currentCustomer = newCustomer;
                });
    }

    public List<Long> getPOIIDs()
    {
        List<Long> ids = new ArrayList<>();
        Set<POI> pois = currentCustomer.getFavoritePOI();
        for (POI poi: currentCustomer.getFavoritePOI())
        {
            ids.add(poi.getId());
        }

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ids;
    }

    public List<Long> getFlightIds()
    {
        List<Long> ids = new ArrayList<>();
        for (Flight flight: currentCustomer.getCustomerFlights())
        {
            ids.add(flight.getId());
        }

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ids;
    }
}
