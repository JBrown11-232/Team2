//Written by Josh Brown

package com.example.application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Home{
	public static Scene createHomeScene(){
		//Make ImageView of the logo stored in resources folder
		Image logo = new Image("file:application/src/main/resources/com/example/application/logo.png");
		ImageView logoView = new ImageView(logo);
		logoView.setPreserveRatio(true);
		logoView.setFitWidth(800.0);
		//Set a title
		Label title = new Label("Welcome To...");
		title.setFont(new Font( 44));
		title.setTextAlignment(TextAlignment.CENTER);
		VBox subroot = new VBox(title, logoView);
		subroot.setAlignment(Pos.CENTER);
		
		//Add MenuBar and return scene
		MenuBar menuBar = DuesPizzaApplication.createMenuBar();
		VBox root = new VBox(menuBar, subroot);
		root.setAlignment(Pos.TOP_CENTER);
		return new Scene(root, DuesPizzaApplication.SCENEWIDTH, DuesPizzaApplication.SCENEHEIGHT);
	}
}
