package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerOptions {

	@FXML
	private Button backToMain;

	@FXML
	private Button btnGenres;

	@FXML
	private Button btnHyperbolics;

	@FXML
	private Button btnLocations;

	@FXML
	private Button btnStories;

	@FXML
	private Button btnSubjects;

	@FXML
	private Button btnVerbs;

	@FXML
	private Button btnWords;

	@FXML
	private GridPane gridPaneOptions;

	@FXML
	private AnchorPane OptionsPane;

	@FXML
	void onClickGenres(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionGenres.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickHyperbolics(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionHyperbolics.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickLocations(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionLocations.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickStories(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionStories.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickSubjects(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionSubjects.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickVerbs(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionVerbs.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	void onClickWords(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiOptionWords.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

	@FXML
	public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiHome.fxml"));
		OptionsPane.getChildren().setAll(pane); // load in same window
	}

}
