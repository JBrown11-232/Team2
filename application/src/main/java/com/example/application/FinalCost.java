//Written by Connor Levinson
//The purpose of this code is to display the customers orders and total owed using java fx
package com.example.application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;


public class FinalCost extends Application{
    public static void main(String[] args)
    {launch(args);}
    @Override
    public void start(Stage primaryStage) throws SQLException{
        ArrayList<Integer> CID_List = PizzaDBManager.getCustomerIDs();

        Label customerLabel = new Label("Customers");
        Label pizzaLabel = new Label("Orders");
        Label orders = new Label("");

        ListView<String> listViewReference = new ListView<>();
        listViewReference.setPrefSize(150,500);
        listViewReference.setOnMouseClicked(mouseEvent ->
        {
            String selected = listViewReference.getSelectionModel().getSelectedItem();
            String[] sections = selected.split(":");
            int CID = Integer.parseInt(sections[0]);
            try {
                ArrayList<Pizza> pizza = PizzaDBManager.getCustomerPizzas(CID);

                String str = pizza.toString().replaceAll("]", "")
                        .replaceAll(";","\n")
                        .replaceAll(", PID:","\nPID:")
                        .replaceAll(",", "\n")
                        .replaceAll("[(){}]","");
                String order = str.substring(1);
                orders.setText(order);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });


        for (int CID : CID_List) {
            String totalOwed = String.format("%,.2f", PizzaDBManager.getTotalOwed(CID));
            String customer = CID + ": " + PizzaDBManager.getCustomerName(CID) + "\nTotal Owed: $" + totalOwed;
            listViewReference.getItems().add(customer);

        }
        VBox vbox1 = new VBox(customerLabel, listViewReference);
        vbox1.setPadding(new Insets(15));
        vbox1.setPrefSize(200,550);

        VBox vbox2 = new VBox(pizzaLabel, orders);
        vbox2.setPadding(new Insets(15));
        vbox2.setPrefSize(250,550);

        HBox hbox = new HBox(vbox1,vbox2);

        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}