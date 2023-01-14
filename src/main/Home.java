package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Home extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("guiHome.fxml"));
		GridPane gridPane = loader.load();

		primaryStage.setResizable(false);
		Scene scene = new Scene(gridPane);

		primaryStage.setAlwaysOnTop(false);
		primaryStage.setScene(scene);

		primaryStage.show();

	}
	



}