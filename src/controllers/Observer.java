package controllers;
/**
 * Observer pattern will be used to limit the number of access to the database to retrieve events such that
 * the observers (controllers) will only update their list of events when the observer informs them of a
 * change in the database,
 * 
 */
public interface Observer {
    public void updateEventCollection();
}
