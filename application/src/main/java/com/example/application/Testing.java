//Written by Josh Brown

package com.example.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Testing extends Application{
	public static void main(String[] args) throws SQLException{
		System.out.println("Beginning Tests");
		System.out.println();
		
		PizzaDBManager.createDB();
		System.out.println();
		
		int[] PIDs = {3,12,0}, OIDs = {1,22,0}, CIDs = {2,5,8,0};
		
		System.out.println("PizzaDBManager.getCustomerNames() = " + PizzaDBManager.getCustomerNames());
		System.out.println("PizzaDBManager.getCustomers() = " + PizzaDBManager.getCustomers());
		System.out.println("PizzaDBManager.getCustomerIDs() = " + PizzaDBManager.getCustomerIDs());
		System.out.println("PizzaDBManager.getPizzaIDs() = " + PizzaDBManager.getPizzaIDs());
		System.out.println("PizzaDBManager.getAvailableOptionIDs() = " + PizzaDBManager.getAvailableOptionIDs());
		System.out.println("PizzaDBManager.getAvailableOptions() = " + PizzaDBManager.getAvailableOptions());
		System.out.println("PizzaDBManager.getAvailableCrusts() = " + PizzaDBManager.getAvailableCrusts());
		System.out.println("PizzaDBManager.getAvailableSauces() = " + PizzaDBManager.getAvailableSauces());
		System.out.println("PizzaDBManager.getAvailableToppings() = " + PizzaDBManager.getAvailableToppings());
		System.out.println();
		
		for(int CID : CIDs){
			System.out.println("PizzaDBManager.getCustomerName("+CID+") = " + PizzaDBManager.getCustomerName(CID));
			System.out.println("PizzaDBManager.getCustomerPizzas("+CID+") = " + PizzaDBManager.getCustomerPizzas(CID));
			System.out.println("PizzaDBManager.getCustomer("+CID+") = " + PizzaDBManager.getCustomer(CID));
			System.out.println("PizzaDBManager.getTotalOwed("+CID+") = " + PizzaDBManager.getTotalOwed(CID));
		}
		System.out.println();
		
		for(int OID : OIDs){
			System.out.println("PizzaDBManager.getAvailableOptionPrice("+OID+") = " + PizzaDBManager.getAvailableOptionPrice(OID));
			System.out.println("PizzaDBManager.getAvailableOptionName("+OID+") = " + PizzaDBManager.getAvailableOptionName(OID));
			System.out.println("PizzaDBManager.getAvailableOption("+OID+") = " + PizzaDBManager.getAvailableOption(OID));
		}
		System.out.println();
		
		for(int PID : PIDs){
			System.out.println("PizzaDBManager.getPizzaPrice("+PID+") = " + PizzaDBManager.getPizzaPrice(PID));
			System.out.println("PizzaDBManager.getPizzaCustomer("+PID+") = " + PizzaDBManager.getPizzaCustomer(PID));
			System.out.println("PizzaDBManager.getPizza("+PID+") = " + PizzaDBManager.getPizza(PID));
			System.out.println("PizzaDBManager.getUsedOptions("+PID+") = " + PizzaDBManager.getUsedOptions(PID));
			System.out.println("PizzaDBManager.getPizzaCrust("+PID+") = " + PizzaDBManager.getPizzaCrust(PID));
			System.out.println("PizzaDBManager.getPizzaSauce("+PID+") = " + PizzaDBManager.getPizzaSauce(PID));
			System.out.println("PizzaDBManager.getPizzaToppings("+PID+") = " + PizzaDBManager.getPizzaToppings(PID));
			System.out.println("PizzaDBManager.getPizzaSize("+PID+") = " + PizzaDBManager.getPizzaSize(PID));
		}
		System.out.println();
		
		Customer new_customer = PizzaDBManager.getCustomer(PizzaDBManager.insertCustomer("John", "000 Nowhere Lane", "518-425-9754"));
		Option new_crust = PizzaDBManager.getAvailableOption(PizzaDBManager.insertAvailableOption("Fruit Crust", "C", 8.10));
		Option new_sauce = PizzaDBManager.getAvailableOption(PizzaDBManager.insertAvailableOption("Apple Sauce", "S", 4.75));
		Option new_topping = PizzaDBManager.getAvailableOption(PizzaDBManager.insertAvailableOption("Strawberries", "T", 2.55));
		Pizza new_pizza = PizzaDBManager.getPizza(PizzaDBManager.submitOrder(new_customer,
				new ArrayList<>(Arrays.asList(new_crust, new_sauce, new_topping, PizzaDBManager.getAvailableOption(40))), "M"));
		if(new_crust.isCrust() && new_sauce.isSauce() && new_topping.isTopping()){
			System.out.println("Options know who they are");
		}
		else{
			throw new RuntimeException("Options are majorly confused");
		}
		if(new_crust.getName().equals("Fruit Crust")){
			System.out.println("Fruit Crust is OK");
		}
		else{
			throw new RuntimeException("Fruit Crust lost!");
		}
		System.out.println("new_customer.getAddress() = " + new_customer.getAddress());
		System.out.println("new_customer = " + new_customer);
		System.out.println("new_pizza = " + new_pizza);
		System.out.println("PizzaDBManager.getWorldNum() = " + PizzaDBManager.getWorldNum());
		PizzaDBManager.removeAvailableOption(40);
		PizzaDBManager.updateCustomer(new_customer.getCID(), new_customer.getName(), "None", "000-000-0000");
		PizzaDBManager.updateAvailableOption(new_topping.getOID(), new_topping.getName(), new_topping.getOptionType(), 1.0);
		PizzaDBManager.recalculatePizzaPrice(new_pizza.getPID());
		new_customer = PizzaDBManager.getCustomer(new_customer.getCID());
		new_topping = PizzaDBManager.getAvailableOption(new_topping.getOID());
		new_pizza = PizzaDBManager.getPizza(new_pizza.getPID());
		
		System.out.println("new_customer = " + new_customer);
		System.out.println("new_pizza = " + new_pizza);
		System.out.println("PizzaDBManager.getWorldNum() = " + PizzaDBManager.getWorldNum());
		PizzaDBManager.removePizza(new_pizza);
		PizzaDBManager.removeCustomer(new_customer);
		PizzaDBManager.removeAvailableOption(new_topping);
		
		System.out.println("Tests Done!");
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws SQLException{
		ComboBox<Option> cb = new ComboBox<>();
		cb.getItems().setAll(PizzaDBManager.getAvailableCrusts());
		
		VBox root = new VBox(new Label("JFX is cool!"), cb);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 600, 300);
		primaryStage.setTitle("Testing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
