package Iterator;

import java.util.ArrayList;

import proxyFlyweight.proxyEvent;

public interface collectionInterface {
    iterator createBaseIterator(ArrayList<proxyEvent> eventList);
}
