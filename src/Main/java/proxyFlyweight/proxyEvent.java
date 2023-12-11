package proxyFlyweight;

import core.dbUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Date;

public class proxyEvent implements eventInterface {
    private final dbUtils db = dbUtils.getInstance();
    private event realEvent;
    private final int eventID;
    private final String eventName;
    private final String eventLocation;
    private final Date eventDate;
    
    public proxyEvent (int id, String name, String location, Date date) {
        this.eventID = id;
        this.eventName = name;
        this.eventLocation = location;
        this.eventDate = date;
        System.out.println("Proxy obj has been created for event: " + eventID);
    }

    @Override
    public void displayEvent() {
        if(realEvent == null) {
            realEvent = db.getEvent(eventID);
            System.out.println("Created real event object of id: "+ eventID);
        }
        realEvent.displayEvent();
    }

    public VBox displayGridProxyEvent() {
        VBox box = new VBox();
        Label name = new Label(eventName);
        Text location = new Text(eventLocation);
        location.getStyleClass().add("text");

        Text date = new Text(eventDate.toString());
        date.getStyleClass().add("text");

        Button detailsBtn = new Button();
        detailsBtn.setText("Show More");
        detailsBtn.setOnAction(
                e-> this.displayEvent()
        );
        detailsBtn.setAlignment(Pos.CENTER);

        box.getChildren().setAll(name, location, date, detailsBtn);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("browseEvents");

        return box;
    }

    public HBox displayListProxyEvent() {
        HBox hbox = new HBox();
        VBox box = new VBox();
        Label name = new Label(eventName);
        name.setAlignment(Pos.CENTER);
        Text location = new Text(eventLocation);

        Label date = new Label(eventDate.toString());
        date.setAlignment(Pos.CENTER);
        box.getChildren().setAll(name ,location);

        Button show = new Button();
        show.setText("Show More");
        show.setOnAction(
                e-> this.displayEvent()
        );
        hbox.getChildren().setAll(box, show);

        return hbox;
    }

    @Override
    public String toString() {
        return eventName;
    }
}
