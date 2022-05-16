//Written by Connor Levinson
//The purpose of this code is to display the customers orders and total owed using java fx

package com.example.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class FinalCost{
    final private static ListView<String> listViewReference = new ListView<>();
    
    public static Scene createTotalOwed(){

        //Create Labels
        Label customerLabel = new Label("Customers");
        Label pizzaLabel = new Label("Orders");
        Label orders = new Label("");

        //Create a buttone that reloads the customers information
        Button reloadButton = new Button("Reload");
        reloadButton.setOnAction(event -> reloadData());

        //Create listview that goes through the customer information and their pizzas
        listViewReference.setPrefSize(150,500);
        listViewReference.setOnMouseClicked(mouseEvent ->
        {
            String selected = listViewReference.getSelectionModel().getSelectedItem();
            if(selected != null){
                String[] sections = selected.split(":");
                int CID = Integer.parseInt(sections[0]);
                try{
                    ArrayList<Pizza> pizza = PizzaDBManager.getCustomerPizzas(CID);
        
                    String str = pizza.toString().replaceAll("]", "")
                            .replaceAll(";", "\n")
                            .replaceAll(", PID:", "\nPID:")
                            .replaceAll(",", "\n")
                            .replaceAll("[(){}]", "");
                    String order = str.substring(1);
                    orders.setText(order);
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

        //Reload data
        reloadData();

        //Create a vbox for the customers
        VBox vbox1 = new VBox(customerLabel, listViewReference, reloadButton);
        vbox1.setSpacing(10.0);
        vbox1.setPadding(new Insets(15));
        vbox1.setPrefSize(200,550);

        //Create a vbox for the pizzas
        VBox vbox2 = new VBox(pizzaLabel, orders);
        vbox2.setSpacing(10.0);
        vbox2.setPadding(new Insets(15));
        vbox2.setPrefSize(250,550);

        //Create an hbox for the vboxs
        HBox hbox = new HBox(vbox1,vbox2);
        hbox.setSpacing(10.0);

        //Create a menubar object
        MenuBar menuBar = DuesPizzaApplication.createMenuBar();

        //Create and style the final vbox
        VBox root = new VBox(menuBar, hbox);
        root.setAlignment(Pos.TOP_CENTER);
        return new Scene(root, DuesPizzaApplication.SCENEWIDTH, DuesPizzaApplication.SCENEHEIGHT);
    }

    //Create the reload function to update all the data on the final cost scene
    public static void reloadData(){
        try{
            listViewReference.getItems().clear();
            ArrayList<Customer> customers = PizzaDBManager.getCustomers();
            for(Customer c : customers){
                String customer = "%d: %s\nTotal Owed: $%,.2f".formatted(c.getCID(), c.getName(), PizzaDBManager.getTotalOwed(c.getCID()));
                listViewReference.getItems().add(customer);
            }
        }
        catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
