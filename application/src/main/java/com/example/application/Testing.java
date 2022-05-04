package com.example.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Testing extends Application{
	public static void main(String[] args) throws SQLException{
		System.out.println("Beginning Tests");
		System.out.println();
		
		PizzaDBManager.createDB();
		System.out.println();
		
		int[] PIDs = {3,12,0}, OIDs = {1,22,0}, CIDs = {2,5,8,0};
		
		System.out.println("PizzaDBManager.getCustomerNames() = " + PizzaDBManager.getCustomerNames());
		System.out.println("PizzaDBManager.getCustomerIDs() = " + PizzaDBManager.getCustomerIDs());
		System.out.println("PizzaDBManager.getPizzaIDs() = " + PizzaDBManager.getPizzaIDs());
		System.out.println("PizzaDBManager.getAvailableOptionIDs() = " + PizzaDBManager.getAvailableOptionIDs());
		System.out.println("PizzaDBManager.getCustomerSummaries() = " + PizzaDBManager.getCustomerSummaries());
		System.out.println("PizzaDBManager.getAvailableOptions() = " + PizzaDBManager.getAvailableOptions());
		System.out.println("PizzaDBManager.getAvailableCrusts() = " + PizzaDBManager.getAvailableCrusts());
		System.out.println("PizzaDBManager.getAvailableSauces() = " + PizzaDBManager.getAvailableSauces());
		System.out.println("PizzaDBManager.getAvailableToppings() = " + PizzaDBManager.getAvailableToppings());
		System.out.println();
		
		for(int CID : CIDs){
			System.out.println("PizzaDBManager.getCustomerName("+CID+") = " + PizzaDBManager.getCustomerName(CID));
			System.out.println("PizzaDBManager.getCustomerPizzas("+CID+") = " + PizzaDBManager.getCustomerPizzas(CID));
			System.out.println("PizzaDBManager.getCustomerSummary("+CID+") = " + PizzaDBManager.getCustomerSummary(CID));
		}
		System.out.println();
		
		for(int OID : OIDs){
			System.out.println("PizzaDBManager.getAvailableOptionPrice("+OID+") = " + PizzaDBManager.getAvailableOptionPrice(OID));
			System.out.println("PizzaDBManager.getAvailableOptionName("+OID+") = " + PizzaDBManager.getAvailableOptionName(OID));
		}
		System.out.println();
		
		for(int PID : PIDs){
			System.out.println("PizzaDBManager.getPizzaPrice("+PID+") = " + PizzaDBManager.getPizzaPrice(PID));
			System.out.println("PizzaDBManager.getPizzaCustomer("+PID+") = " + PizzaDBManager.getPizzaCustomer(PID));
			System.out.println("PizzaDBManager.getPizzaSummary("+PID+") = " + PizzaDBManager.getPizzaSummary(PID));
			System.out.println("PizzaDBManager.getUsedOptions("+PID+") = " + PizzaDBManager.getUsedOptions(PID));
			System.out.println("PizzaDBManager.getPizzaCrust("+PID+") = " + PizzaDBManager.getPizzaCrust(PID));
			System.out.println("PizzaDBManager.getPizzaSauce("+PID+") = " + PizzaDBManager.getPizzaSauce(PID));
			System.out.println("PizzaDBManager.getPizzaToppings("+PID+") = " + PizzaDBManager.getPizzaToppings(PID));
			System.out.println("PizzaDBManager.getPizzaSize("+PID+") = " + PizzaDBManager.getPizzaSize(PID));
		}
		System.out.println();
		
		
		
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		HBox root = new HBox(new Label("JFX is cool!"));
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 200, 200);
		primaryStage.setTitle("Testing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
