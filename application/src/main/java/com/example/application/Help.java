/*
	Created By Ian Durant for Team 2
	Programs Purpose text written by Josh Brown
 */

package com.example.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Help extends Application
{

    private Label title;
    private Label purposeHeader;
    private Label explainPurpose;
    private Label basicInstructionsHeader;
    private Label explainInstructions;
    private VBox titleVBox;
    private VBox purposeVBox;
    private VBox instructionsVBox;
    private VBox purposeInstructionsVBox;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        title = new Label("Help: ");
        purposeHeader = new Label("Purpose: ");
        basicInstructionsHeader = new Label("Instructions: ");

        //Runs ProgramsPurpose()
        ProgramsPurpose();

        purposeVBox = new VBox(10,purposeHeader,explainPurpose);

        //Runs InstructionsPurpose()
        InstructionsPurpose();

        instructionsVBox = new VBox(10,basicInstructionsHeader,explainInstructions);

        titleVBox = new VBox(10,title);

        purposeInstructionsVBox = new VBox(30,titleVBox,purposeVBox,instructionsVBox);

        titleVBox.setAlignment(Pos.TOP_CENTER);
        purposeVBox.setAlignment(Pos.CENTER);
        instructionsVBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();

        //borderPane.setTop(helpHBox);
        borderPane.setCenter(purposeInstructionsVBox);

        Scene helpScene = new Scene(borderPane,500,500);

        primaryStage.setScene(helpScene);

        primaryStage.show();

    }

    public void ProgramsPurpose()
    {
        explainPurpose = new Label("This program is a build your own pizza application which will:" + "\n" +
                "* Let the user choose the pizza's size, toppings, sauce, and the crust" + "\n" +
                "* Then it calculates the total cost for the created pizza (and displays it back to the user)" + "\n" +
                "* The application will be able to show the total cost of pizzas that one customer ordered" + "\n" +
                "* In the DB one table will store the available pizza options and their prices" + "\n" +
                "* Another table will store the list of ordered pizzas" + "\n" +
                "* One final table will store the list of customers" + "\n");
    }

    public void InstructionsPurpose()
    {
        explainInstructions =
                new Label("");
    }
}
