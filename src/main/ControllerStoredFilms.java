package main;

import classes.DBConnect;
import classes.FilmTitleDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerStoredFilms {

	@FXML
	private Button backToMain;

	@FXML
	private AnchorPane storedFilmsPane;

	@FXML
	private GridPane gridPaneFilm;

	@FXML
	private ListView<String> listview;

	FilmTitleDescription film = new FilmTitleDescription();
	DBConnect myDBConnection = new DBConnect();

	ArrayList<String> listedFilms = new ArrayList<>(FilmTitleDescription.readStoredTitleDescription());

	public void initialize()
	{
		// Puts the titles contained in an ArrayList in a list
		listview.getItems().addAll(listedFilms);
	}

	@FXML
	void onClickDelete(ActionEvent event) {

		int id=listview.getSelectionModel().getSelectedIndex();

		// Error when no item is selected
		if(id==-1)
		{
			Alert msg = new Alert(AlertType.ERROR);
			msg.setContentText("Select an item to delete");
			msg.showAndWait();
		}
		// Ask confirmation to delete
		else
		{
			// Box ask confirmation
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Are you sure you want to delete?");
			alert.setTitle("Confirm delete");
			alert.getButtonTypes().remove(0,2);
			alert.getButtonTypes().add(0, ButtonType.YES);
			alert.getButtonTypes().add(1,ButtonType.NO);
			Optional<ButtonType> confirmationResponse = alert.showAndWait();

			if(confirmationResponse.get() == ButtonType.YES)
			{

				// System.out.println(id);
				ArrayList<Integer> pkListFilms = new ArrayList<Integer>(); 		// Here we will store the primary keys for the delete option
				ArrayList<String> keys = myDBConnection.getFilmForeignKeys(); 	// Contains Primary Key and foreign keys from database

				// Make list of Primary keys
				for(int i=0;i<keys.size();i++)
				{
					String[] parts = keys.get(i).split(" ");		// Retrieve a record and split to array by space
					pkListFilms.add(Integer.parseInt(parts[0])); 	// Primary key
				}

				// Delete
				boolean succes=film.deleteItem(pkListFilms, id);
				ArrayList<String> listedFilms = new ArrayList<>(FilmTitleDescription.readStoredTitleDescription());
				listview.getItems().clear();
				listview.getItems().addAll(listedFilms);

				// Show status msg
				if(succes)
				{
					Alert msg = new Alert(AlertType.INFORMATION);
					msg.setHeaderText("Chosen item deleted");
					msg.setTitle("Deleted");
					msg.showAndWait();
				}
				else
				{
					Alert msg = new Alert(AlertType.ERROR);
					msg.setHeaderText("Item not deleted due database error");
					msg.setTitle("Error");
					msg.showAndWait();
				}	
			}
		}
	}

	@FXML
	void btnExport(ActionEvent event) {
		film.writeToFile(film.getObjName(), listedFilms);
	}

	@FXML
	void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		storedFilmsPane.getChildren().setAll(pane); // load in same window
	}

}
