package controllers;

import models.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import models.eventBrowserModel;

import java.util.Objects;

public class eventBrowserController implements Observer{
    private static dbUtils db;
    private static boolean dbIsEmpty;
    //This list will only be updated when informed of a change by the database, instead of every time the page is loaded
    @FXML private GridPane eventGrid;
    @FXML private Button nextEventPage;
    @FXML private Button prevEventPage;
    @FXML private Button refreshBtn;

    //switch between events on the browsing page and control navigation buttons so that they only appear when needed
    @FXML
    private void nextPage() {
        if(!dbIsEmpty) dbIsEmpty = eventBrowserModel.getNextPage();
        eventBrowserModel.moveToNextPage();
        eventBrowserModel.displayPage(eventGrid);
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);
    }

    @FXML
    private void prevPage() {
        eventBrowserModel.moveToPrevPage();
        eventBrowserModel.displayPage(eventGrid);
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);
    }

    @FXML
    private void refreshPage() {
        eventBrowserModel.refreshPage();
        eventBrowserModel.getNextPage();
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);
        refreshBtn.setVisible(false);
    }

    //initialize the page and set the events to be browser
    public void initialize() {
        if(db == null) {
            db = dbUtils.getInstance();
            db.subscribe(this);
            System.out.println("Initial batch of events received to browsing page");
        }
        refreshBtn.setVisible(false);
        eventBrowserModel.refreshPage();
        eventBrowserModel.displayPage(eventGrid);
        dbIsEmpty = eventBrowserModel.getNextPage();
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);

        String css = Objects.requireNonNull(this.getClass().getResource("/CSStyle.css")).toExternalForm();
        eventGrid.getStylesheets().add(css);
    }

    //the update fxn called by the observable to refresh the eventlist on the browsing page
    @Override
    public void updateEventCollection() {
        refreshBtn.setVisible(true);
        System.out.println("new events received to event browsing page");
    }
}
    
