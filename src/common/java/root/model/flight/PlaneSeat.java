package common.java.root.model.flight;


import common.java.root.model.customer.Customer;

import javax.persistence.*;

@Entity(name="PlaneSeat")
@Table(name="plane_seat")

public class PlaneSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="flight_id")
    private Flight flight;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    public PlaneSeat() {
    }

    public PlaneSeat(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
        this.service = new Service();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
