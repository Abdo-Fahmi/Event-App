package models;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import proxyFlyweight.proxyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class eventBrowserModel {
    private static final ArrayList<proxyEvent> page = dbUtils.getInstance().refreshEvents();
    private static final ArrayList<ArrayList<proxyEvent>> pageCollection = new ArrayList<>();
    private static int lastIndex = page.get(page.size()-1).getEventID();
    private static int currPageIndex = 0;

    public static void refreshPage() {
        if(pageCollection.isEmpty()) {
            ArrayList<proxyEvent> temp = new ArrayList<>(page);
            pageCollection.add(temp);
        } else {
            page.clear();
            pageCollection.clear();
            page.addAll(dbUtils.getInstance().refreshEvents());
            ArrayList<proxyEvent> temp = new ArrayList<>(page);
            pageCollection.add(temp);
            lastIndex = page.get(page.size()-1).getEventID();
        }
        currPageIndex = 0;
    }

    public static void moveToNextPage() {
        page.clear();
        page.addAll(pageCollection.get(++currPageIndex));
    }

    public static boolean getNextPage() {
        ArrayList<proxyEvent> temp = dbUtils.getInstance().getNextPageEvents(lastIndex);
        if(temp == null || temp.isEmpty()) return true;
        pageCollection.add(temp);
        lastIndex = temp.get(temp.size()-1).getEventID();
        return temp.size() != 9;
    }

    public static void moveToPrevPage() {
        page.clear();
        page.addAll(pageCollection.get(--currPageIndex));
    }

    public static void displayPage(GridPane eventGrid) {
        eventGrid.getChildren().clear();
        proxyEvent currEvent;
        Iterator<proxyEvent> pageIterator = page.iterator();
        for(int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if(!pageIterator.hasNext()) return;
                currEvent = pageIterator.next();
                eventGrid.add(currEvent.displayGridProxyEvent(), column, row);
            }
        }
    }

    public static void btnVisibility(Button next, Button prev) {
        prev.setVisible(currPageIndex != 0);
        next.setVisible(currPageIndex != pageCollection.size()-1);
    }
}