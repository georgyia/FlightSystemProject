package common.java.root.model.flight;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import root.model.city.Airport;
import common.java.root.model.customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity(name="Flight")
@Table(name="flight")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;
    @Column(nullable = false)
    private String flightNumber;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Transient
    private Period flightTime;

    private String direction;

    private String terminal;
    private String gate;

    @OneToMany(mappedBy="flight")
    private Set<PlaneSeat> planeSeats = new HashSet<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="airport_from")
    @JsonBackReference(value="departingFlights")
    private Airport from;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="airport_to")
    @JsonBackReference(value="arrivingFlights")
    private Airport to;

    @ManyToMany(mappedBy = "customerFlights")
    Set<Customer> customers;

    public Flight() {
    }

    public Flight(String flightNumber, LocalDateTime startTime, LocalDateTime endTime, String terminal, String gate, Airport from, Airport to) {
        this.flightNumber = flightNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.terminal = terminal;
        this.gate = gate;
        this.from = from;
        this.to = to;
        this.direction = ""+this.from.getName()+"->"+this.to.getName();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Period getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Period flightTime) {
        this.flightTime = flightTime;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public Set<PlaneSeat> getPlaneSeats() {
        return planeSeats;
    }

    public void setPlaneSeats(Set<PlaneSeat> planeSeats) {
        this.planeSeats = planeSeats;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
