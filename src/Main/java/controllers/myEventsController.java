package controllers;

import java.util.ArrayList;
import java.util.Objects;

import core.currUser;
import core.dbUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import models.eventsModel;
import proxyFlyweight.proxyEvent;

public class myEventsController implements Observer{
    private static dbUtils db;
    private static final currUser user = currUser.getInstance();
    private static final ArrayList<proxyEvent>  reservations = new ArrayList<>();
    private static ArrayList<proxyEvent>  events;
    private static ArrayList<Integer> resIDs;
    @FXML private ListView<HBox> eventList;
    @FXML
    private void displayUserCreatedEvents() {
        eventsModel.switchDisplayedEvents(eventList, events);
    }

    @FXML
    private void displayUserReservations() {
        eventsModel.switchDisplayedEvents(eventList, reservations);
    }

    //initializing the page and subscribing the observable
    public void initialize() {
        if(db == null) {
            db = dbUtils.getInstance();
            events = db.getMyEvents(user.getUserID());
            resIDs = db.getResIDs(user.getUserID());
            //reservations = new ArrayList<proxyEvent>();
            for(Integer i: resIDs) {
                reservations.add(db.getReservedEvent(i));
            }
        }
        displayUserCreatedEvents();
        String css = Objects.requireNonNull(this.getClass().getResource("/CSStyle.css")).toExternalForm();
        eventList.getStylesheets().add(css);
        db.subscribe(this);
    }
    //updating the event created by the user and their reservations when told by the database
    @Override
    public void updateEventCollection() {
        events = db.getMyEvents(user.getUserID());
        resIDs = db.getResIDs(user.getUserID());
        reservations.clear();
        for(Integer i: resIDs) {
            reservations.add(db.getReservedEvent(i));
        }
    }
}
