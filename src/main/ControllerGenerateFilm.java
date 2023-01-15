package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerGenerateFilm {

	@FXML
	private Button backToMain;

	@FXML
	private Text descriptionText;

	@FXML
	private AnchorPane generateFilmPane;

	@FXML
	private Button generatedescription;

	@FXML
	private Button generatetitle;

	@FXML
	private GridPane gridPaneGenerateTitle; // Needed for loading another pane, id of Anchor in fxml

	@FXML
	private Button saveToDB;

	@FXML
	private Text titleText;


	@FXML
	public void onClickGenerateDescription(ActionEvent event) {

	}

	@FXML
	public void onClickGenerateTitle(ActionEvent event) {

	}

	@FXML
	public void onClickSaveToDB(ActionEvent event) {

	}

	@FXML
	public  void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		generateFilmPane.getChildren().setAll(pane); // load in same window
	}

}
