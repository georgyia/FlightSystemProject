package common.java.root.model.city;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import common.java.root.model.flight.Flight;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String name;


    @OneToMany(mappedBy="from")
    @JsonManagedReference(value="departingFlights")
    private Set<Flight> departingFlights = new HashSet<>();

    @OneToMany(mappedBy="to")
    @JsonManagedReference(value="arrivingFlights")
    private Set<Flight> arrivingFlights = new HashSet<>();

    // interesting implementation -> there might be many airports in one city
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id")
    @JsonBackReference(value = "airport-city")
    private City city;

    public Airport() {
    }

    public Airport(String name, City city) {
        this.name=name;
        this.city = city;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public void setDepartingFlights(Set<Flight> departingFlights) {
        this.departingFlights = departingFlights;
    }

    public Set<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    public void setArrivingFlights(Set<Flight> arrivingFlights) {
        this.arrivingFlights = arrivingFlights;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
