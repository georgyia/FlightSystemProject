package client.java.com.view;

import client.java.com.FlightSystemClientApplication;
import client.java.com.controller.CityController;
import client.java.com.controller.CustomerController;
import client.java.com.controller.POIController;
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import netscape.javascript.JSObject;
import common.java.root.model.city.City;
import common.java.root.model.city.POI;
import common.java.root.model.customer.Customer;

import java.util.*;

public class POIScene extends Scene implements MapComponentInitializedListener {

    // Global Variables
    private CustomerController customerController;
    private POIController poiController;
    private FlightSystemClientApplication application;
    private final List<City> cities;
    private final List<POI> pois;
    private final HashMap<POI, Marker> poiToMarker;
    private City currentCity;

    // Globale Components
    private final ToolBar tb;
    private VBox rightControl;
    private ToolBar applicationBinder;

    // Map info
    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    private Label lblZoom;
    private Label lblCenter;
    private Label lblClick;
    private ComboBox<MapTypeIdEnum> mapTypeCombo;



    public POIScene(FlightSystemClientApplication application, CustomerController customerController, POIController poiController, CityController cityController) {
        super(new VBox(), 640, 500);

        // Initialize global values
        this.application = application;
        this.customerController = customerController;
        this.poiController = poiController;

        cities = cityController.getCities();
        currentCity = cities.get(0);
        pois = new ArrayList<>();

        for (City city: cities) {pois.addAll(city.getPois());}
        poiToMarker = new HashMap<>();

        tb = new ToolBar();
        configureToolBar();

        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInitializedListener(this);
        VBox leftControl = new VBox(mapComponent);

        configureControllPOI();

        configureApplicationBinder();

        // pack everything together
        BorderPane bp = new BorderPane();
        bp.setTop(tb);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(leftControl, rightControl);
        bp.setCenter(splitPane);
        bp.setBottom(applicationBinder);

        var vBox = new VBox(bp);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
     }

    private void configureApplicationBinder() {
        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> {
            application.showHomeScene();
        });
        applicationBinder = new ToolBar();
        applicationBinder.getItems().addAll(btnBack);
    }

    private void configureControllPOI() {


        List<MenuItem> cityItems = new ArrayList<>();

        for (City city: cities)
        {
            MenuItem cityItem = new MenuItem(city.getName());
            cityItem.setOnAction(event -> {
                currentCity = city;
                for (Map.Entry<POI, Marker> poiMarkerEntry : poiToMarker.entrySet()) {
                    poiMarkerEntry.getValue().setVisible(city.getPois().contains(poiMarkerEntry.getKey()));
                }
            });
            cityItems.add(cityItem);
        }

        List<MenuItem> customerItems = new ArrayList<>();

        MenuItem searchItem = new MenuItem("Search");
        searchItem.setOnAction(event -> {
            for (Map.Entry<POI, Marker> poiMarkerEntry : poiToMarker.entrySet()) {
                poiMarkerEntry.getValue().setVisible(true);
            }
        });

        MenuItem customerItem = new MenuItem("Customer");
        customerItem.setOnAction(event -> {
            for (Map.Entry<POI, Marker> poiMarkerEntry : poiToMarker.entrySet()) {
                poiMarkerEntry.getValue().setVisible(
                        customerController.getPOIIDs().contains(poiMarkerEntry.getKey().getId()));
            }
        });

        customerItems.add(searchItem);
        customerItems.add(customerItem);

        Set<String> categories = new HashSet<>();
        for (POI poi:pois)   {categories.add(poi.getType());}


        List<MenuItem> categoryItems = new ArrayList<>();
        for (String category: categories)
        {
            MenuItem menuItem = new MenuItem(category);
            menuItem.setOnAction(event -> {
                for (Map.Entry<POI, Marker> poiMarkerEntry : poiToMarker.entrySet()) {
                    poiMarkerEntry.getValue().setVisible(poiMarkerEntry.getKey().getType().equals(category) &&
                            currentCity.getPois().contains(poiMarkerEntry.getKey()));
                }
            });
            categoryItems.add(menuItem);
        }


        for (String category: categories)
        {
            MenuItem menuItem = new MenuItem(category);
            menuItem.setOnAction(event -> {
                for (Map.Entry<POI, Marker> poiMarkerEntry : poiToMarker.entrySet()) {
                    poiMarkerEntry.getValue().setVisible(poiMarkerEntry.getKey().getType().equals(category) &&
                            currentCity.getPois().contains(poiMarkerEntry.getKey()));
                }
            });
            categoryItems.add(menuItem);
        }

        Menu cityMenu = new Menu("Cities");
        cityMenu.getItems().addAll(cityItems);

        Menu categoryMenu = new Menu("Categories");
        categoryMenu.getItems().addAll(categoryItems);

        Menu customerMenu = new Menu("My POIs");
        customerMenu.getItems().addAll(customerItems);

        List<POIHBoxCell> list = new ArrayList<>();
        for (POI poi:pois) {
            list.add(new POIHBoxCell(poi.getName(), "Add to favorite", poi));
        }

        ListView<POIHBoxCell> listView = new ListView<POIHBoxCell>();
        ObservableList<POIHBoxCell> myObservableList = FXCollections.observableList(list);
        listView.setItems(myObservableList);

        MenuBar filterMenuBar = new MenuBar();
        filterMenuBar.getMenus().addAll(cityMenu, categoryMenu, customerMenu);
        rightControl = new VBox(filterMenuBar, listView);
    }

    private void configureToolBar() {
        Button btnZoomIn = new Button("Zoom In");
        btnZoomIn.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() + 1);
        });

        Button btnZoomOut = new Button("Zoom Out");
        btnZoomOut.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() - 1);
        });

        lblZoom = new Label();
        lblCenter = new Label();
        lblClick = new Label();

        mapTypeCombo = new ComboBox<>();
        mapTypeCombo.setOnAction(e -> {
            map.setMapType(mapTypeCombo.getSelectionModel().getSelectedItem());
        });

        Button btnType = new Button("Map type");
        btnType.setOnAction(e -> {
            map.setMapType(MapTypeIdEnum.HYBRID);
        });

        tb.getItems().addAll(btnZoomIn, btnZoomOut, mapTypeCombo,
                new Label("Zoom: "), lblZoom,
                new Label("Click: "), lblClick);
    }

    public void mapInitialized () {

        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(47.606189, -122.335842);

        MapOptions options = new MapOptions();
        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN)
                .clickableIcons(false)
                .disableDefaultUI(true)
                .disableDoubleClickZoom(true)
                .keyboardShortcuts(false)
                .styleString("[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]");

        map = mapComponent.createMap(options, false);

        map.setHeading(123.2);



        for (POI poi: pois)
        {
            Marker marker = new Marker(new MarkerOptions()
                    .position(new LatLong(poi.getLatitude(), poi.getLongitude()))
                    .title(poi.getName())
                    .visible(true));
            map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
                showWindow(poi, marker);
            });
            poiToMarker.put(poi, marker);
            map.addMarker(marker);
        }

        map.fitBounds(new LatLongBounds(new LatLong(30, 120), center));

        lblCenter.setText(map.getCenter().toString());
        map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
            lblCenter.setText(n.toString());
        });

        lblZoom.setText(Integer.toString(map.getZoom()));
        map.zoomProperty().addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
            lblZoom.setText(n.toString());
        });


        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            lblClick.setText(ll.toString());
        });


        mapTypeCombo.getItems().addAll(MapTypeIdEnum.ALL);

    }

    private void showWindow(POI poi, Marker marker){
        LatLong center = new LatLong(47.606189, -122.335842);

        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content("<h2>"+poi.getName()+"</h2><h3>"+poi.getType()+"<br>"+poi.getAddr()+"</h3>")
                .position(center);

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map, marker);
    }

    private class POIHBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

        POIHBoxCell(String labelText, String buttonText, POI poi) {
            super();

            label.setText(labelText);
            label.setMaxWidth(200);
            HBox.setHgrow(label, Priority.ALWAYS);

            button.setText(buttonText);
            button.setOnAction(event -> {
                customerController.addPOI(poi);
            });

            this.getChildren().addAll(label, button);
        }
    }
}

