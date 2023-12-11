package controllers;

import java.io.IOException;

import core.App;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class mainController {
    /*
     * This is th email page where all other pages are layer onto the right
     * of the sidebar.
     * which is achieved by having an empty StackPane whose nodes are changing
     * using the switch methods below
     */
    @FXML private StackPane pageHolder;

    public void initialize() { }

    public void switchProfile( ) {
        pageNavigator.loadPage(pageNavigator.PROFILE_PAGE); 
    }
    
    public void logout() throws IOException {
        App app = new App();
        app.changeScenes("/LOGIN_PAGE.fxml");
    }

    //setting the parent for the back button before jumping to a different page
    public void switchEvents() {
        eventDisplayController.setParent(pageNavigator.EVENT_BROWSER);
        pageNavigator.loadPage(pageNavigator.EVENT_BROWSER);
    }
    public void switchMyEvents() {
        eventDisplayController.setParent(pageNavigator.MY_EVENTS);
        pageNavigator.loadPage(pageNavigator.MY_EVENTS);
    }

    public void switchCreateEvent() {
        pageNavigator.loadPage(pageNavigator.CREATE_EVENT);
    }

    public void setPage(Node node) {
        pageHolder.getChildren().setAll(node);
    }
}