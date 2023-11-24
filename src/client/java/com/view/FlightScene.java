package client.java.com.view;

import client.java.com.FlightSystemClientApplication;
import client.java.com.controller.AirportController;
import client.java.com.controller.CityController;
import client.java.com.controller.CustomerController;
import client.java.com.controller.FlightController;
import com.dlsc.gmapsfx.javascript.object.Marker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import common.java.root.model.city.Airport;
import common.java.root.model.city.POI;
import common.java.root.model.flight.Flight;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class FlightScene extends Scene {

    private final FlightSystemClientApplication application;

    private Airport airportFrom;
    private Airport airportTo;
    private List<FlightHBoxCell> list;

    private Label fromLabel = new Label();
    private Label toLabel = new Label();

    private CustomerController customerController;


    public FlightScene(FlightSystemClientApplication application, FlightController flightController, CityController cityController, AirportController airportController, CustomerController customerController) {
        super(new VBox(), 640, 500);
        this.application = application;

        this.customerController = customerController;


        List<Airport> airports = airportController.getAirports();
        airportFrom = airports.get(0);
        fromLabel.setText(airportFrom.getName());
        airportTo = airports.get(1);
        toLabel.setText(airportTo.getName());


        list = new ArrayList<>();
        Set<Long> flightsID = new HashSet<>();

        Map<Airport, List<Long>> departing = new HashMap<>();
        Map<Airport, List<Long>> arriving = new HashMap<>();

        for (Airport airport: airports) {
            for (Flight flight: airport.getDepartingFlights())
            {
                if (!flightsID.contains(flight.getId()))
                {
                    list.add(new FlightHBoxCell(flight.getFlightNumber(),
                            flight.getStartTime().toString(),
                            flight.getEndTime().toString(), "Add to favorite", flight));
                    flightsID.add(flight.getId());
                }

                if (departing.containsKey(airport))
                {
                    departing.get(airport).add(flight.getId());
                }
                else
                {
                    List<Long> tmpFlights = new ArrayList<>();
                    tmpFlights.add(flight.getId());
                    departing.put(airport, tmpFlights);
                }
            }
            for (Flight flight: airport.getArrivingFlights())
            {
                if (!flightsID.contains(flight.getId()))
                {
                    list.add(new FlightHBoxCell(flight.getFlightNumber(),
                            flight.getStartTime().toString(),
                            flight.getEndTime().toString(), "Add to favorite", flight));
                    flightsID.add(flight.getId());
                }

                if (arriving.containsKey(airport))
                {
                    arriving.get(airport).add(flight.getId());
                }
                else
                {
                    List<Long> tmpFlights = new ArrayList<>();
                    tmpFlights.add(flight.getId());
                    arriving.put(airport, tmpFlights);
                }
            }
        }

        ListView<FlightHBoxCell> listView = new ListView<>();
        ObservableList<FlightHBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);


        List<MenuItem> fromCityItems = new ArrayList<>();
        List<MenuItem> toCityItems = new ArrayList<>();

        for (Airport airport: airports)
        {
            MenuItem airportItem = new MenuItem(airport.getName());
            airportItem.setOnAction(event -> {
                fromLabel.setText(airport.getName());
                airportFrom = airport;
                for (var cell: list)
                {
                    Long flightId = cell.flight.getId();
                    cell.setVisible(departing.get(airportFrom).contains(flightId) &&
                            arriving.get(airportTo).contains(flightId));
                }
            });
            fromCityItems.add(airportItem);
        }

        for (Airport airport: airports)
        {
            MenuItem airportItem = new MenuItem(airport.getName());
            airportItem.setOnAction(event -> {
                toLabel.setText(airport.getName());
                airportTo = airport;
                for (var cell: list)
                {
                    Long flightId = cell.flight.getId();
                    cell.setVisible(departing.get(airportFrom).contains(flightId) &&
                            arriving.get(airportTo).contains(flightId));
                }
            });
            toCityItems.add(airportItem);
        }

        List<MenuItem> customerItems = new ArrayList<>();

        MenuItem searchItem = new MenuItem("Search");
        searchItem.setOnAction(event -> {
            for (var cell: list) {
                cell.setVisible(true);
            }
        });

        MenuItem customerItem = new MenuItem("Customer");
        customerItem.setOnAction(event -> {
            for (var cell: list) {
                cell.setVisible(customerController.getFlightIds().contains(cell.flight.getId()));
            }
        });

        customerItems.add(searchItem);
        customerItems.add(customerItem);


        Menu fromCityMenu = new Menu("From");
        fromCityMenu.getItems().addAll(fromCityItems);

        Menu toCityMenu = new Menu("To");
        toCityMenu.getItems().addAll(toCityItems);

        Menu customerMenu = new Menu("My Flights");
        customerMenu.getItems().addAll(customerItems);

        MenuBar filterMenuBar = new MenuBar();
        filterMenuBar.getMenus().addAll(fromCityMenu, toCityMenu, customerMenu);


        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> {
            application.showHomeScene();
        });
        ToolBar applicationBinder = new ToolBar();
        applicationBinder.getItems().addAll(btnBack);

        var vBox = new VBox(fromLabel, toLabel, filterMenuBar, listView, applicationBinder);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private class FlightHBoxCell extends HBox {
        Label direction = new Label();
        Label flightNumber = new Label();
        Label startTime = new Label();
        Label endTime = new Label();
        Button button = new Button();
        Flight flight;

        FlightHBoxCell(String flightNumber, String startTime, String endTime, String buttonText, Flight flight) {
            super();

            this.flight=flight;

            this.direction.setText(flight.getDirection());
            this.direction.setMinWidth(200);

            this.flightNumber.setText(flightNumber);
            this.flightNumber.setMinWidth(100);


            this.startTime.setText(startTime);
            this.startTime.setMinWidth(150);

            this.endTime.setText(endTime);
            this.endTime.setMinWidth(150);

            button.setText(buttonText);
            button.setOnAction(event -> {
                customerController.addFlight(flight);
            });

            this.getChildren().addAll(this.direction, this.flightNumber, this.startTime, this.endTime, button);
        }
    }
}
