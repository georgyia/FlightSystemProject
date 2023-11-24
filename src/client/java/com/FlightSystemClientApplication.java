package client.java.com;

import com.controller.*;
import com.view.FlightScene;
import com.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlightSystemClientApplication extends Application {
    private final CustomerController customerController = new CustomerController();
    private final POIController poiController = new POIController();
    private final FlightController flightController = new FlightController();
    private final CityController cityController = new CityController();
    private final AirportController airportController = new AirportController();
    private Stage stage;
    private Scene POIScene;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.POIScene = new POIScene(this, customerController, poiController, cityController);
        primaryStage.setScene(new StartScene(customerController, this));
        primaryStage.show();
    }

    public void showStartScene() {
        stage.setScene(new StartScene(customerController, this));
    }

    public void showHomeScene() {
        stage.setScene(new HomeScene(this, customerController));
    }

    public void showFlightScene() { stage.setScene(new FlightScene(this, flightController, cityController, airportController, customerController)); }

    public void showPOIScene() { stage.setScene(this.POIScene); }

    public Stage getStage() {
        return stage;
    }
}
