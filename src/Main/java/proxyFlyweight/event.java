package proxyFlyweight;

import java.sql.Date;
import java.util.ArrayList;
import controllers.pageNavigator;
import models.eventDisplayModel;

public class event implements eventInterface{
    private final int eventID;
    private String creatorName;
    private final String eventName;
    private final String eventLocation;
    private final Date eventDate;
    private final String description;
    private final ArrayList<String> attendees = new ArrayList<>();

    public event(int eventID, String eventName, String eventLocation, Date eventDate, String description) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.description = description;
    }

    @Override
    public void displayEvent() {
        System.out.println("event "+eventID+" is being displayed");
        eventDisplayModel.setEvent(this);
        eventDisplayModel.setAttendees();
        pageNavigator.loadPage(pageNavigator.CURRENT_EVENT);
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public int getEventID() {
        return eventID;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public void setCreatorID(String username) {
        this.creatorName = username;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void addAttendee(String username) {
        attendees.add(username);
    }
}
