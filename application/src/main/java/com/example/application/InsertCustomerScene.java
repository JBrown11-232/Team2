package com.example.application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InsertCustomerScene{
	//TODO add reload button

	final private static TextField customerNameTextField = new TextField();
	final private static TextField customerAddressTextField = new TextField();
	final private static TextField customerPhoneNumberTextField = new TextField();
	final private static Label outputLabel = new Label();
	
	public static Scene createInsertCustomerScene(){
		Label title = new Label("Add new Customer");
		Label customerNameLabel = new Label("Enter Your Name: ");
		Label customerAddressLabel = new Label("Enter Your Address: ");
		Label customerPhoneNumberLabel = new Label("Enter Your Phone Number: ");
		Button createCustomerButton = new Button("Add Customer!");
		createCustomerButton.setOnAction(actionEvent -> handleButton());
		
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
		
		MenuBar menuBar = DuesPizzaApplication.createMenuBar();
		VBox root = new VBox(menuBar, title, subroot);
		return new Scene(root);
	}
	
	private static void handleButton(){
		try{
			int CID = PizzaDBManager.insertCustomer(customerNameTextField.getText(),
					customerAddressTextField.getText(), customerPhoneNumberTextField.getText());
			outputLabel.setText("Added new customer! Your CID is %d.".formatted(CID));
		}
		catch(Exception ex){
			outputLabel.setText("Failed to add customer!\n"+ex.getMessage());
		}
	}
}
