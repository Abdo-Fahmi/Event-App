package models;

import Iterator.baseIterator;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import proxyFlyweight.proxyEvent;

public class eventBrowserModel {
    private static baseIterator iterator;
    public static void displayNextEvents(GridPane eventGrid) {
        eventGrid.getChildren().clear();
        proxyEvent currEvent;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if(!iterator.hasNext()) return;
                currEvent = iterator.getNext();
                eventGrid.add(currEvent.displayGridProxyEvent(),column,row);
            }
        }
    }

    public static void displayPrevEvents(GridPane eventGrid) {
        proxyEvent currEvent;
        eventGrid.getChildren().clear();
        for (int row = 0; row < 3; row ++) {
            for (int column = 0; column < 3; column++) {
                if(!iterator.hasPrev()) return;
                currEvent = iterator.getPrev();
                eventGrid.add(currEvent.displayGridProxyEvent(),column,row);
            }
        }
    }

    public static void btnVisibility(Button next, Button prev) {
        prev.setVisible(!iterator.firstPage());
        next.setVisible(iterator.hasNext());
    }

    public static void setIterator(baseIterator b) {
        iterator = b;
    }
}
