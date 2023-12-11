package controllers;

import Iterator.iteratorCreator;
import core.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import models.eventBrowserModel;
import proxyFlyweight.proxyEvent;

import java.util.ArrayList;
import java.util.Objects;

public class eventBrowserController implements Observer{
    private final iteratorCreator iterator = new iteratorCreator();
    private static dbUtils db;
    //This list will only be updated when informed of a change by the database, instead of every time the page is loaded
    private static ArrayList<proxyEvent> eventsToDisplay = new ArrayList<>();
    @FXML private GridPane eventGrid;
    @FXML private Button nextEventPage;
    @FXML private Button prevEventPage;
    //switch between events on the browsing page and control navigation buttons so that they only appear when needed
    @FXML
    private void nextPage() {
        eventBrowserModel.displayNextEvents(eventGrid);
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);
    }

    @FXML
    private void prevPage() {
        eventBrowserModel.displayPrevEvents(eventGrid);
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);
    }

    //initialize the page and set the events to be browser
    public void initialize() {
        if(db == null) {
            db = dbUtils.getInstance();
            db.subscribe(this);
            eventsToDisplay = db.getEvents();
            System.out.println("Initial batch of events received to browsing page");
        }
        eventBrowserModel.setIterator(iterator.createBaseIterator(eventsToDisplay));
        eventBrowserModel.displayNextEvents(eventGrid);
        eventBrowserModel.btnVisibility(nextEventPage, prevEventPage);

        String css = Objects.requireNonNull(this.getClass().getResource("/CSStyle.css")).toExternalForm();
        eventGrid.getStylesheets().add(css);
    }

    //the update fxn called by the observable to refresh the eventlist on the browsing page
    @Override
    public void updateEventCollection() {
        eventsToDisplay = db.getEvents();
        System.out.println("new events received to event browsing page");
    }
}
    
