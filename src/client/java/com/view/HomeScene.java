package client.java.com.view;

import client.java.com.FlightSystemClientApplication;
import client.java.com.controller.CustomerController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomeScene extends Scene {

    public HomeScene(FlightSystemClientApplication application, CustomerController customerController) {
        super(new VBox(), 640, 500);

        var welcomeText = new Text("Welcome "+customerController.getCurrentCustomer().getEmail());

        var search_flights = new Button("Search flights");
        search_flights.setOnAction(event -> application.showFlightScene());

        var poi_public = new Button("Search POI");
        poi_public.setOnAction(event -> application.showPOIScene());

        var log_out = new Button("Log out");
        log_out.setOnAction(event -> {
            customerController.resetCurrentCustomer();
            application.showStartScene();
        });

        var vBox = new VBox(10, welcomeText, search_flights, poi_public, log_out);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }
}
