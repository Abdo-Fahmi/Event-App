package iterator;


import proxyFlyweight.proxyEvent;

public interface iterator {
    proxyEvent getNext();
    proxyEvent getPrev();
    boolean hasNext();
    boolean hasPrev();
}
