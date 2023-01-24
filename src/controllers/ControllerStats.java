package controllers;

import java.io.IOException;

import classes.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerStats {

	@FXML
	private Button btnBackToMain;

	@FXML
	private AnchorPane statsPane;

	@FXML
	private GridPane gridPaneStats;

	@FXML
	private Label nfilm;

	@FXML
	private Label ndescrip;

	@FXML
	private Label ngenre;

	@FXML
	private Label nhyperbolic;

	@FXML
	private Label nloc;

	@FXML
	private Label nstory;

	@FXML
	private Label nsubject;

	@FXML
	private Label ntitle;

	@FXML
	private Label nverb;

	@FXML
	private Label nword;

	private DBConnect db = new DBConnect();

	public void initialize()
	{
		nword.setText(Integer.toString(db.getWords().size()));
		nverb.setText(Integer.toString(db.getVerbs().size()));
		nsubject.setText(Integer.toString(db.getSubjects().size()));
		nstory.setText(Integer.toString(db.getStories().size()));
		nloc.setText(Integer.toString(db.getLocations().size()));
		nhyperbolic.setText(Integer.toString(db.getHyperbolics().size()));
		ngenre.setText(Integer.toString(db.getCategories().size()));
		nfilm.setText(Integer.toString(db.getFilmForeignKeys().size()));
		ntitle.setText(Integer.toString(db.getTitleForeignKeys().size()));
		ndescrip.setText(Integer.toString(db.getDescriptionForeignKeys().size()));
	}
	
	@FXML
	public  void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/guiHome.fxml"));
		statsPane.getChildren().setAll(pane); // load in same window
	}

}

