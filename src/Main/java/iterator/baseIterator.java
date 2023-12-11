package iterator;

import java.util.ArrayList;
import proxyFlyweight.proxyEvent;
/*
 * the use of this pattern will allow for future expansion by
 * letting us change iteration logic to follow possible restrictions
 * such as a search bar
 * 
 */
public class baseIterator implements iterator{
    private final ArrayList<proxyEvent> eventList;
    private final ArrayList<proxyEvent> currentPage = new ArrayList<>();
    private int currentPosition = 0;
    private int flag = 9;
    public baseIterator(ArrayList<proxyEvent> newList) {
        this.eventList = newList;
    }
    @Override
    public proxyEvent getNext() {
        proxyEvent temp = eventList.get(currentPosition);
        if(currentPage.size() == 9) currentPage.clear();
        currentPage.add(temp);
        if(hasNext() && currentPosition != eventList.size()) currentPosition++;
        return temp;
    }
    @Override
    public proxyEvent getPrev() {
        if(flag == 9) {
            flag = 0;
            while(currentPage.contains(eventList.get(currentPosition-1))) {
                currentPosition--;
            }
            currentPage.clear();
            currentPosition -= 9;
        }
        flag++;
        return getNext();
    }
    @Override
    public boolean hasNext() {
        return currentPosition != eventList.size();
    }

    @Override
    public boolean hasPrev() {
        return currentPosition != 0;
    }
    public boolean firstPage() {
        return eventList.isEmpty() || (currentPage.get(0) == eventList.get(0));
    }
}
