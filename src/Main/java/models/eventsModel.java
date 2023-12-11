package models;

import java.util.ArrayList;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import proxyFlyweight.proxyEvent;

public class eventsModel {
    public static void switchDisplayedEvents(ListView<HBox> list, ArrayList<proxyEvent> events) {
        list.getItems().clear();
        if(events == null ) return;
        for(proxyEvent i: events) {
            HBox box = i.displayListProxyEvent();
            list.getItems().add(box);
        }
    }
}
