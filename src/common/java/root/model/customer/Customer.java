package common.java.root.model.customer;

import root.model.flight.Flight;
import root.model.flight.PlaneSeat;
import root.model.city.POI;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;
    private String email;
    private String password;

    @OneToMany(mappedBy="customer")
    private Set<PlaneSeat> planeSeats = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "customer_poi",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    Set<POI> favoritePOI;

    @ManyToMany
    @JoinTable(
            name = "customer_flight",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    Set<Flight> customerFlights;

    public Customer() {
    }

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<PlaneSeat> getPlaneSeats() {
        return planeSeats;
    }

    public void setPlaneSeats(Set<PlaneSeat> planeSeats) {
        this.planeSeats = planeSeats;
    }

    public void addPOI(POI poi)
    {
        favoritePOI.add(poi);
    }

    public Set<POI> getFavoritePOI() {
        return favoritePOI;
    }

    public void setFavoritePOI(Set<POI> favoritePOI) {
        this.favoritePOI = favoritePOI;
    }

    public void addFlight(Flight flight) {
        customerFlights.add(flight);
    }

    public Set<Flight> getCustomerFlights() {
        return customerFlights;
    }

    public void setCustomerFlights(Set<Flight> customerFlights) {
        this.customerFlights = customerFlights;
    }
}
