package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerGenerateTitle {

    @FXML
    private Button backToMain;

    @FXML
    private AnchorPane generateTitlePane;

    @FXML
    private Button generatetitle;

    @FXML
    private GridPane gridPaneGenerateTitle;

    @FXML
    private Button saveToDB;

    @FXML
    private Text titleText;

    @FXML
    void onClickGenerateTitle(ActionEvent event) {

    }

    @FXML
    void onClickSaveToDB(ActionEvent event) {

    }

    @FXML
    public void onClickToMain(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("guiHome.fxml"));
		generateTitlePane.getChildren().setAll(pane); // load in same window
    }

}

