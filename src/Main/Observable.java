package Main;

import controllers.Observer;

public interface Observable {
    void notifyControllers();
    void subscribe(Observer obs);
}
