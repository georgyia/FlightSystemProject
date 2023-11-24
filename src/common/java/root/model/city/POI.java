package common.java.root.model.city;


import com.fasterxml.jackson.annotation.JsonBackReference;
import common.java.root.model.customer.Customer;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table

public class POI implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;

    private String name;
    private String addr;
    private double latitude;
    private double longitude;
    private String type;

    // Important for statistical reasons (e.g. how many people are interested?)
    @ManyToMany(mappedBy = "favoritePOI")
    Set<Customer> interestedCustomers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id")
    @JsonBackReference(value = "city-poi")
    private City city;

    public POI() {
    }

    public POI(String name, String addr, double latitude, double longitude, String type, City city) {
        this.name = name;
        this.addr = addr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInterestedCustomers(Set<Customer> interestedCustomers) {
        this.interestedCustomers = interestedCustomers;
    }

    public City getCity() {return this.city;}

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof POI)
        {
            return id.compareTo(((POI) o).getId());
        }
        return -1;
    }
}
