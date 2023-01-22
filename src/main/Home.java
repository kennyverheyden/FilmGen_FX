package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Home extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setResizable(false);
		primaryStage.setAlwaysOnTop(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}