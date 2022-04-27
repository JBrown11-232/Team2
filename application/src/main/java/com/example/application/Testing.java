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
		System.out.println("Beginning Test");
		PizzaDBManager.getConn();
		System.out.println("Connected!");
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
