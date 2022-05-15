/*
	Created By Ian Durant for Team 2
	Programs Purpose text written by Josh Brown
 */

package com.example.application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Help{
    public static Scene createHelpScene(){
        //Creating labels
        Label title = new Label("Help: ");
        title.setFont(new Font(36));
        Label purposeHeader = new Label("Purpose: ");
        purposeHeader.setFont(new Font(24));
        Label explainPurpose = ProgramsPurpose();
        Label basicInstructionsHeader = new Label("Instructions: ");
        basicInstructionsHeader.setFont(new Font(24));
        Label explainInstructions = InstructionsPurpose();

        //Creating and putting variables into VBoxes
        VBox purposeVBox = new VBox(10,purposeHeader,explainPurpose);
        VBox instructionsVBox = new VBox(10,basicInstructionsHeader,explainInstructions);
        VBox titleVBox = new VBox(10,title);
        VBox purposeInstructionsVBox = new VBox(30,titleVBox,purposeVBox,instructionsVBox);

        //Positioning the VBoxes
        titleVBox.setAlignment(Pos.TOP_CENTER);
        purposeVBox.setAlignment(Pos.CENTER);
        instructionsVBox.setAlignment(Pos.CENTER);

        //Adding to menu bar
        MenuBar menuBar = DuesPizzaApplication.createMenuBar();
        VBox root = new VBox(menuBar, purposeInstructionsVBox);
        root.setAlignment(Pos.TOP_CENTER);
        return new Scene(root, DuesPizzaApplication.SCENEWIDTH, DuesPizzaApplication.SCENEHEIGHT);
    }

    public static Label ProgramsPurpose()
    {
        //Label for programs purpose
        return new Label("""
                This program is a build your own pizza application which will:
                * Let the user choose the pizza's size, toppings, sauce, and the crust
                * Then it calculates the total cost for the created pizza (and displays it back to the user)
                * The application will be able to show the total cost of pizzas that one customer ordered
                * In the DB one table will store the available pizza options and their prices
                * Another table will store the list of ordered pizzas
                * One final table will store the list of customers
                """);
    }

    public static Label InstructionsPurpose()
    {
        //Label for instructions purpose
        return new Label("""
                You can navigate this application by clicking an item in the submenu
                To create your a pizza:
                * You will need to create a Customer Id, you can do this by selecting Create New... then Create Customer
                * Please enter your name, address, and phone number and then click the button labeled Add Customer, it will then show you your ID
                * To create your pizza click Create New... and then Create Pizza, enter your customer number in the space provided, and select what you want on your pizza
                * You can select multiple toppings by ctrl-clicking the options.
                * When finished customizing your pizza, press the button labeled Build Pizza.
                * To review your order and check the cost select Review Order... and then select Total Cost
                Thank you for choosing Due's Pizzeria""");
    }
}
