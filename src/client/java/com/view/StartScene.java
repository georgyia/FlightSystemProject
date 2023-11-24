package client.java.com.view;

import client.java.com.FlightSystemClientApplication;
import client.java.com.controller.CustomerController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import common.java.root.model.customer.Customer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

public class StartScene extends Scene {

    CustomerController customerController;
    private final ObservableList<Customer> customerList;
    private final FlightSystemClientApplication application;



    public StartScene(CustomerController customerController, FlightSystemClientApplication application) {
        super(new VBox(), 640, 500);

        this.application = application;
        this.customerController=customerController;
        this.customerList = FXCollections.observableArrayList();


        var customerSignUpButton = new Button("Customer Sign Up");
        customerSignUpButton.setOnAction(event -> {
            try {
                showPopup(true);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        var customerSignInButton = new Button("Customer Sign In");
        customerSignInButton.setOnAction(event -> {
            try {
                showPopup(false);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        var vBox = new VBox(10, customerSignUpButton, customerSignInButton);
        vBox.setAlignment(Pos.CENTER);
        setRoot(vBox);
    }

    private void showPopup(Boolean signUp) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        var popup = new Popup();
        var emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        emailTextField.setText("name@address.com");

        var passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setText("Not 123 or pass ;)");

        var submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {
            Customer newCustomer = null;

            byte[] bytesEncoded = Base64.getEncoder().encode(passwordTextField.getText().getBytes());

            newCustomer = new Customer(emailTextField.getText(), new String(bytesEncoded).replaceAll("\u0000", "\u0101"));


            if (signUp) customerController.signUp(newCustomer);
            else customerController.signIn(newCustomer);

            if (customerController.getCurrentCustomer()!=null)
                application.showHomeScene();

            popup.hide();
        });

        var cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> popup.hide());


        var hBox = new HBox(10, submitButton, cancelButton);
        hBox.setAlignment(Pos.CENTER);


        var vBox = new VBox(10, emailTextField, passwordTextField, hBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        vBox.setPrefWidth(200);
        vBox.setPrefHeight(150);
        vBox.setPadding(new Insets(5));
        popup.getContent().add(vBox);
        popup.show(application.getStage());
        popup.centerOnScreen();
    }

    private void setCustomer(List<Customer> customers) {
        Platform.runLater(() -> customerList.setAll(customers));
    }

}
