/*
		Created by Ian Durant for Team 2
 		Refactored by Josh Brown
*/

package com.example.application;

//Import Statements
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class InsertCustomerScene{
	//Creating variables
	final private static TextField customerNameTextField = new TextField();
	final private static TextField customerAddressTextField = new TextField();
	final private static TextField customerPhoneNumberTextField = new TextField();
	final private static Label outputLabel = new Label();
	final private static Button createCustomerButton = new Button("Add Customer!");
	
	public static Scene createInsertCustomerScene(){
		//Creating Labels
		Label title = new Label("Add new Customer");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(24));
		Label customerNameLabel = new Label("Enter Your Name: ");
		Label customerAddressLabel = new Label("Enter Your Address: ");
		Label customerPhoneNumberLabel = new Label("Enter Your Phone Number: ");
		
		//Set event handlers
		createCustomerButton.setOnAction(actionEvent -> handleButton());
		customerNameTextField.textProperty().addListener(event -> setDisabledInsertStatus());
		customerAddressTextField.textProperty().addListener(event -> setDisabledInsertStatus());
		customerPhoneNumberTextField.textProperty().addListener(event -> setDisabledInsertStatus());
		
		//Creating GUI
		GridPane subroot = new GridPane();
		subroot.add(customerNameLabel, 0, 0);
		subroot.add(customerAddressLabel, 0, 1);
		subroot.add(customerPhoneNumberLabel, 0, 2);
		subroot.add(outputLabel, 0, 3);
		subroot.add(customerNameTextField, 1, 0);
		subroot.add(customerAddressTextField, 1, 1);
		subroot.add(customerPhoneNumberTextField, 1, 2);
		subroot.add(createCustomerButton, 1, 3);
		subroot.setHgap(10.0);
		subroot.setVgap(10.0);
		subroot.setPadding(new Insets(10));
		subroot.setAlignment(Pos.CENTER);
		
		setDisabledInsertStatus();
		
		//Adding a menu bar
		MenuBar menuBar = DuesPizzaApplication.createMenuBar();
		VBox root = new VBox(menuBar, title, subroot);
		root.setAlignment(Pos.TOP_CENTER);
		return new Scene(root, DuesPizzaApplication.SCENEWIDTH, DuesPizzaApplication.SCENEHEIGHT);
	}
	
	private static void handleButton(){
		//REQUIREMENT 2: String input
		//Creating and displaying customer ID
		try{
			int CID = PizzaDBManager.insertCustomer(customerNameTextField.getText(),
					customerAddressTextField.getText(), customerPhoneNumberTextField.getText());
			outputLabel.setText("Added new customer! Your CID is %d.".formatted(CID));
			InsertPizzaScene.preloadCIDField(CID);
		}
		catch(Exception ex){
			outputLabel.setText("Failed to add customer!\n"+ex.getMessage());
		}
	}
	
	private static void setDisabledInsertStatus(){
		boolean status = customerNameTextField.getText().equals("") ||
				customerAddressTextField.getText().equals("") || customerPhoneNumberTextField.getText().equals("");
		createCustomerButton.setDisable(status);
	}
}
