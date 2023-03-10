package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class ControllerHome {

	@FXML
	private Button btnExit;

	@FXML
	private Button btnGenerateDescription;

	@FXML
	private Button btnGenerateFilm;

	@FXML
	private Button btnGenerateTitle;

	@FXML
	private GridPane gridPaneHome;

	@FXML
	private Button options;

	@FXML
	private AnchorPane rootPane; // Needed for loading another pane, id of Anchor in fxml

	@FXML
	private Button btnStoredDescriptions;

	@FXML
	private Button btnStoredFilms;

	@FXML
	private Button btnStoredTitles;


	@FXML
	public void onClickGenerateFilmStage(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiGenerateFilm.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	public void onClickGenerateTitleStage(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiGenerateTitle.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	public void onClickGenerateDescriptionStage(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiGenerateDescription.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	public void onClickStoredTitles(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiStoredTitles.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickStoredFilms(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiStoredFilms.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickStoredDescriptions(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiStoredDescriptions.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickStats(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiStats.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
	}
	
	@FXML
    void onClickOptions(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiOptions.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
    }

	// Button EXIT
	@FXML
	public void onClickExit(ActionEvent event) {
		// Box ask confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to exit the program?");
		alert.setTitle("Confirm exit");
		alert.getButtonTypes().remove(0,2);
		alert.getButtonTypes().add(0, ButtonType.YES);
		alert.getButtonTypes().add(1,ButtonType.NO);
		Optional<ButtonType> confirmationResponse = alert.showAndWait();
		if(confirmationResponse.get() == ButtonType.YES)
		{
			// Exit the program
			System.exit(0);
		}
	}

}
