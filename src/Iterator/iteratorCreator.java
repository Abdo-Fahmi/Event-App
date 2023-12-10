package Iterator;

import java.util.ArrayList;

import proxyFlyweight.proxyEvent;

public class iteratorCreator implements collectionInterface{
    @Override
    public baseIterator createBaseIterator(ArrayList<proxyEvent> eventList) {
        return new baseIterator(eventList);
    }
    
}
