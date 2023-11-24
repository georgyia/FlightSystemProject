package common.java.root.model.city;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static common.java.utils.JSONResult.getJSON;

@Entity
@Table

public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;
    private String name;
    private String country;
    private Double lat;
    private Double lon;
    @Transient
    private double temperature;
    @Transient
    private double humidity;

    @OneToMany(mappedBy="city")
    @JsonManagedReference(value = "airport-city")
    private Set<Airport> airports = new HashSet<>();

    @OneToMany(mappedBy="city")
    @JsonManagedReference(value = "city-poi")
    private Set<POI> pois = new HashSet<>();

    public City() {
    }

    public City(String name, String country, Double lat, Double lon) {
        this.name = name;
        this.country = country;
        this.lat=lat;
        this.lon=lon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature(){
        return -1;
        /*
        String s = "https://api.openweathermap.org/data/2.5/weather?lat="+this.lat+"&lon="+this.lon+"&appid=8620d04d15645872ca08908de763d78a";
        try {
            JSONObject obj = getJSON(s);
            return obj.getJSONObject("main").getDouble("temp")-273.15;
        } catch (IOException e) {
            return -1;
        }
        */
    }

    public double getHumidity() {
        return -1;
        /*
        String s = "https://api.openweathermap.org/data/2.5/weather?lat="+this.lat+"&lon="+this.lon+"&appid=8620d04d15645872ca08908de763d78a";
        try {
            JSONObject obj = getJSON(s);
            return obj.getJSONObject("main").getDouble("humidity");
        } catch (IOException e) {
            return -1;
        }
        */
    }

    public Set<Airport> getAirports() {
        return airports;
    }

    public void setAirports(Set<Airport> airports) {
        this.airports = airports;
    }

    public Set<POI> getPois() {
        return pois;
    }

    public void setPois(Set<POI> pois) {
        this.pois = pois;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
