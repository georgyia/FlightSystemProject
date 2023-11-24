package server.java.root.config;

import org.apache.tomcat.util.buf.Utf8Encoder;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import root.model.city.*;
import root.model.customer.Customer;
import root.model.customer.CustomerRepository;
import root.model.flight.Flight;
import root.model.flight.FlightRepository;

import java.io.FileReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import org.json.*;

import static utils.JSONResult.getJSON;


@Configuration
public class ConfigAll {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, FlightRepository flightRepository, AirportRepository airportRepository, POIRepository poiRepository, CityRepository cityRepository) {
        return args -> {
            Customer yasso = new Customer("yasso@cool", "123");
            Customer abdo = new Customer("abdo@here", "pass");

            customerRepository.saveAll(List.of(yasso, abdo));

            List<String> cities = List.of("London", "Paris", "Istanbul", "Antalya", "Rome", "Prague", "Amsterdam",
                    "Barcelona", "Vienna", "Milan", "Athens", "Berlin", "Moscow", "Madrid", "Venice", "Dublin", "Florence",
                    "Saint Petersburg", "Brussels", "Munich", "Budapest", "Lisbon", "Copenhagen", "Heraklion", "Mugla",
                    "Krakow", "Warsaw", "Frankfurt", "Stockholm", "Nice", "Porto");

            for (String city:cities)
            {
                if (city.equals("Barcelona"))
                    break;
                String s = "https://api.geoapify.com/v1/geocode/search?text="+URLEncoder.encode(city, StandardCharsets.UTF_8)+"&format=json&apiKey=7bfb9cd960b3496d9ce019358cef6c08";
                JSONObject obj = getJSON(s);

                // get the first result
                JSONArray res = obj.getJSONArray("results");

                for (int i=0;i<res.length();i++)
                {
                    JSONObject object = res.getJSONObject(i);
                    try {
                        if (object.getString("result_type").equals("city")) {

                            City cityRow = new City(object.getString("city"),
                                                    object.getString("country"),
                                                    object.getDouble("lat"),
                                                    object.getDouble("lon"));
                            cityRepository.save(cityRow);


                            // adding the airport
                            Airport airport = new Airport(object.getString("city"), cityRow);
                            airportRepository.save(airport);

                            double lat = object.getDouble("lat");
                            double lon = object.getDouble("lon");

                            String poiURL = "https://api.geoapify.com/v2/places?categories=catering,tourism,accommodation&" +
                                    "conditions=wheelchair.yes,internet_access&filter=circle:"+lon+","+lat+",10000&" +
                                    "bias=proximity:"+lon+","+lat+"4&limit=1&apiKey=7bfb9cd960b3496d9ce019358cef6c08";
                            JSONArray poiArray = getJSON(poiURL).getJSONArray("features");

                            List<POI> pois = new LinkedList<>();

                            for (int j=0;j<poiArray.length();j++)
                            {
                                JSONObject singlePOI = poiArray.getJSONObject(i).getJSONObject("properties");
                                POI newPOI = new POI(singlePOI.getString("name"),
                                                        singlePOI.getString("address_line2"),
                                                        singlePOI.getDouble("lat"),
                                                        singlePOI.getDouble("lon"),
                                                        singlePOI.getJSONArray("categories").getString(0),
                                                        cityRow);
                                pois.add(newPOI);
                            }
                            poiRepository.saveAll(pois);


                            break;
                        }
                    } catch(Exception e){
                        System.out.println(city);
                    }
                }
            }


            // ---------------------------------------------------------------------------------------

            // "London", "Paris", "Istanbul", "Antalya", "Rome", "Prague", "Amsterdam"
            //flightRepository.save(new Flight("EJ 3458", LocalDate.now().atTime(8, 55), LocalDate.now().atTime(9, 10), "Terminal 2", "G26", cityRepository.))
            List<Airport> airports = airportRepository.findAll();
            for (Airport airportFrom:airports)
            {
                for (Airport airportTo: airports)
                {
                    if (airportFrom.equals(airportTo))
                        continue;
                    Random r = new Random();
                    char name1 = (char)(r.nextInt(26) + 'A');
                    char name2 = (char)(r.nextInt(26) + 'A');
                    int name3 = r.nextInt(1000, 10000);
                    String flightNumber = ""+name1+name2+name3;

                    int dateDelta = r.nextInt(1, 5);
                    int timeHourStart = r.nextInt(0, 24);
                    int timeHourEnd = r.nextInt(timeHourStart, 24);
                    int timeMinStart = r.nextInt(0, 60);
                    int timeMinEnd = r.nextInt(timeMinStart, 60);

                    LocalDateTime start = LocalDateTime.now().plusDays(dateDelta).with(LocalTime.of(timeHourStart, timeMinStart));
                    LocalDateTime end = LocalDateTime.now().plusDays(dateDelta).with(LocalTime.of(timeHourEnd, timeMinEnd));

                    // int terminalNumber = r.nextInt(1, 4);
                    // String terminal = r.nextInt(1, 4);

                    Flight flight = new Flight(flightNumber, start, end, "Terminal 0", "Gate 0", airportFrom, airportTo);
                    flightRepository.save(flight);
                }
            }
            /*
            LocalDateTime endTime = LocalDateTime.now();
            LocalDateTime startTime = LocalDateTime.now();
            String terminal = "17";
            String gate = "23";

            Set<Flight> departingFlights = new HashSet<>();
            Set<Flight> arrivingFlights = new HashSet<>();

            String name = "Mexiko Stadt";
            String country = "Mexiko";

            City city = new City(name, country);

            Airport from = new Airport(departingFlights, arrivingFlights, city);
            Airport to = new Airport(departingFlights, arrivingFlights, city);



            Flight flight1 = new Flight("1", startTime, endTime, terminal, gate, from , to);
            Flight flight2 = new Flight("2", startTime, endTime, terminal, gate, from , to);
            Flight flight3 = new Flight("3", startTime, endTime, terminal, gate, from , to);
            Flight flight4 = new Flight("3", startTime, endTime, terminal, gate, from , to);
            Flight flight5 = new Flight("5", startTime, endTime, terminal, gate, from , to);
            Flight flight6 = new Flight("6", startTime, endTime, terminal, gate, from , to);
            Flight flight7 = new Flight("7", startTime, endTime, terminal, gate, from , to);

            flightRepository.saveAll(List.of(flight1, flight2, flight3, flight4, flight5, flight6, flight6, flight7));

            */
        };
    }
}


