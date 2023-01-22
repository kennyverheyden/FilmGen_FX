package main;

import classes.FilmTitleDescription;

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
	private Button btnBackToMain;

	@FXML
	private Text descriptionText;

	@FXML
	private AnchorPane generateFilmPane;

	@FXML
	private Button btnGeneratedescription;

	@FXML
	private Button btnGeneratetitle;

	@FXML
	private GridPane gridPaneGenerateTitle; // Needed for loading another pane, id of Anchor in fxml

	@FXML
	private Button btnSaveToDB;

	@FXML
	private Text titleText;

	private FilmTitleDescription film = new FilmTitleDescription(); // Create object

	public void initialize()
	{
		titleText.setText(film.getGeneratedTitle());
		descriptionText.setText(film.getGeneratedDescription());
	}

	@FXML
	public void onClickGenerateDescription(ActionEvent event) {
		film.generateNewDescription();
		descriptionText.setText(film.getGeneratedDescription());
	}

	@FXML
	public void onClickGenerateTitle(ActionEvent event) {
		film.generateNewTitle();
		titleText.setText(film.getGeneratedTitle());
	}

	@FXML
	public void onClickSaveToDB(ActionEvent event) {
		film.storeGeneratedFilmDescription();
	}

	@FXML
	public  void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		generateFilmPane.getChildren().setAll(pane); // load in same window
	}

}
