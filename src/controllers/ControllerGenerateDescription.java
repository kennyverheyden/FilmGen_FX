package controllers;

import classes.FilmDescription;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerGenerateDescription {

	@FXML
	private Button btnBackToMain;

	@FXML
	private Text descriptionText;

	@FXML
	private AnchorPane generateDescriptionPane;

	@FXML
	private Button btnGeneratedescription;

	@FXML
	private GridPane gridPaneGenerateDescription;

	@FXML
	private Button btnSaveToDB;

	private FilmDescription description = new FilmDescription(); // Create object
	
	public void initialize()
	{
		descriptionText.setText(description.getGeneratedDescription());
	}
	
	@FXML
	void onClickGenerateDescription(ActionEvent event) {
		descriptionText.setText(description.generateDescription());
	}

	@FXML
	void onClickSaveToDB(ActionEvent event) {
		description.storeGeneratedDescription();
	}

	@FXML
	public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/guiHome.fxml"));
		generateDescriptionPane.getChildren().setAll(pane); // load in same window
	}

}

