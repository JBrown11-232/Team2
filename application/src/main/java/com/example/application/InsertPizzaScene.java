/*
	Created by Ian Durant for Team 2
	Refactored by John Brown
*/

package com.example.application;

//Import Statements
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO add reload button

public class InsertPizzaScene{

	//Variables
	final private static double listViewWidth = 300, listViewHeight = 200;
	final private static RadioButton smallPizza = new RadioButton("Small");
	final private static RadioButton mediumPizza = new RadioButton("Medium");
	final private static RadioButton largePizza = new RadioButton("Large");
	final private static ComboBox<Option> pizzaCrustComboBox = new ComboBox<>();
	final private static ComboBox<Option> pizzaSauceComboBox = new ComboBox<>();
	final private static ListView<Option> pizzaToppingsListView = new ListView<>();
	final private static ComboBox<Customer> customerComboBox = new ComboBox<>();
	final private static Label outputLabel = new Label();

	public static Scene createInsertPizzaScene() throws SQLException{
		//Creates and add the three radio buttons to the toggle group
		ToggleGroup pizzaSizeToggleGroup = new ToggleGroup();
		pizzaSizeToggleGroup.getToggles().setAll(smallPizza, mediumPizza, largePizza);

		//Creating a button that will create the pizza
		Button buildPizza = new Button("Build Pizza!");
		buildPizza.setOnAction(event -> handleButton());

		//Labels to show information to user
		Label title = new Label("Build Your Own Pizza");
		Label customerIDLabel = new Label("Customer: ");
		Label pizzaSizeLabel = new Label("Size: ");
		Label pizzaCrustLabel = new Label("Crust: ");
		Label pizzaSauceLabel = new Label("Sauce: ");
		Label pizzaToppingsLabel = new Label("Toppings: ");

		//Adding items from database into the combo boxes
		pizzaCrustComboBox.getItems().setAll(FXCollections.observableArrayList(PizzaDBManager.getAvailableCrusts()));
		pizzaSauceComboBox.getItems().setAll(FXCollections.observableArrayList(PizzaDBManager.getAvailableSauces()));

		//Creating and limiting the list view for toppings
		pizzaToppingsListView.getItems().setAll(FXCollections.observableArrayList(PizzaDBManager.getAvailableToppings()));
		pizzaToppingsListView.setMaxSize(listViewWidth,listViewHeight);
		pizzaToppingsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		//REQUIREMENT 2: Numerical input
		customerComboBox.getItems().setAll(FXCollections.observableArrayList(PizzaDBManager.getCustomers()));

		//Creating the GUI
		GridPane v1 = new GridPane();
		v1.add(customerIDLabel, 0, 0);
		v1.add(customerComboBox, 1, 0);
		v1.add(pizzaSizeLabel, 0, 1);
		v1.add(new HBox(smallPizza, mediumPizza, largePizza), 1, 1);
		v1.add(pizzaCrustLabel, 0, 2);
		v1.add(pizzaCrustComboBox, 1, 2);
		v1.add(pizzaSauceLabel, 0, 3);
		v1.add(pizzaSauceComboBox, 1, 3);
		VBox v2 = new VBox(pizzaToppingsLabel, pizzaToppingsListView, outputLabel, buildPizza);
		HBox subroot = new HBox(v1, v2);
		v1.setHgap(10.0);
		v1.setVgap(10.0);
		v2.setSpacing(10.0);
		subroot.setSpacing(10.0);
		subroot.setPadding(new Insets(10));

		//Adding to menu bar
		MenuBar menuBar = DuesPizzaApplication.createMenuBar();
		VBox root = new VBox(menuBar, title, subroot);
		return new Scene(root);
	}

	private static void handleButton(){
		//Holds the size of the Pizza
		String pizzaSize;

		//Checks which radio button is selected
		try{
			if (smallPizza.isSelected()){
				pizzaSize = "S";
			} else if (mediumPizza.isSelected()){
				pizzaSize = "M";
			} else if (largePizza.isSelected()){
				pizzaSize = "L";
			} else{
				throw new RuntimeException("A size must be selected!");
			}
			//Submitting the order
			PizzaDBManager.submitOrder(customerComboBox.getValue(),
					new ArrayList<>(pizzaToppingsListView.getSelectionModel().getSelectedItems().stream().toList()), pizzaSize);
			//Telling user that order is submitted
			outputLabel.setText("Submitted Pizza!");
		}
		catch(Exception ex){
			outputLabel.setText("Failed to order pizza!\n"+ex.getMessage());
		}
	}
}


