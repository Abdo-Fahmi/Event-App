package models;

import java.util.ArrayList;

import core.currUser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import proxyFlyweight.event;

public class eventDisplayModel {
    private static final dbUtils db = dbUtils.getInstance();
    private static final currUser user = currUser.getInstance();
    private static event currEvent;

    public static void setEvent(event Event) {
        currEvent = Event;
    }

    public static void displayCurrentEvent(Text creatorName, Text eventName, Text date, TextFlow description, Text eventlocation, ListView<String> attendees) {
        creatorName.setText(currEvent.getCreatorName());
        eventName.setText(currEvent.getEventName());
        eventlocation.setText(currEvent.getEventLocation());
        date.setText(currEvent.getEventDate().toString());
        description.getChildren().setAll(new Text(currEvent.getDescription()));
        ArrayList<String> attends = currEvent.getAttendees();
        if(attends != null) {
            for(String usr: attends) {
                if(!attendees.getItems().contains(usr)){
                    attendees.getItems().add(usr);
                }
            }
        }
    }

    public static void reserveCurrUsr(Label wrg, Label crt) {
        if(currEvent.getAttendees().contains(user.getUsername())){
            wrg.setText("You already have a reservation for this event");
        } else {
            if(db.addReservation(currEvent.getEventID(), user.getUserID())) {
                crt.setText("You have been added to the reservation list");
            }
        }
    }

    public static void setAttendees() {
        ArrayList<Integer> attendeeIDs = db.getReservedUsers(currEvent.getEventID());
        if(attendeeIDs != null) {
            for(int i: attendeeIDs) {
                currEvent.addAttendee(db.getUsername(i));
            }
        }
    }
}
