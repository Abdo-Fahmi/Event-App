package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.eventDisplayModel;

public class eventDisplayController {
    private static String parent;
    @FXML private Text creatorName;
    @FXML private Text eventName;
    @FXML private Text date;
    @FXML private Label wrg;
    @FXML private ListView<String> attendees;
    @FXML private Label crt;
    @FXML private TextFlow description;
    @FXML private Text eventLocation;
    @FXML private Button backBtn;
    @FXML
    private void goBack(){
        pageNavigator.loadPage(parent);
    }
    
    //  loading the event from stored in the display model onto this page
    public void initialize() {
        eventDisplayModel.displayCurrentEvent(creatorName, eventName, date, description, eventLocation, attendees);
    }

    //setting the parent of this page to decide which page the back btn will take us to
    //since browsing page and my events page will both call this page to view event details
    public static void setParent(String eventBrowser) {
        parent = eventBrowser;
    }

    public void reserve() {
        eventDisplayModel.reserveCurrUsr(wrg, crt);
    }
}
