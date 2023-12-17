package controllers;

import java.sql.Date;
import core.dbUtils;
import core.inputFilter;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class eventCreationController {
    private static final dbUtils db = dbUtils.getInstance();
    @FXML private TextField eventName;
    @FXML private TextField eventLocation;
    @FXML private TextArea eventDescription;
    @FXML private Label wrg, crt;
    @FXML private DatePicker datePicker;
    @FXML
    private void createEvent() {
        //removing both labels before re-evaluating the new input
        crt.setVisible(false);
        wrg.setVisible(false);
        String name = eventName.getText().trim();
        String location = eventLocation.getText().trim();
        String description = eventDescription.getText().trim();
        Date date = java.sql.Date.valueOf(datePicker.getValue());
        if(validateEventInfo(name, location) && createNewEvent(name, location, description, date)) {
            crt.setVisible(true);
            inputFilter.setDateLimitFilter(datePicker);
        }
    }

    //removing possible labels from past input
    public void initialize() {
        crt.setVisible(false);
        eventDescription.setWrapText(true);
        //adding filters to both text and date picker
        int MAX_CHARS = 300;
        inputFilter.setTextAreaLengthFormatter(eventDescription, MAX_CHARS);
        inputFilter.setDateLimitFilter(datePicker);
    }

    public boolean validateEventInfo(String name, String location) {
        String lengthCheck = "^[a-zA-Z][a-zA-Z0-9 _]{5,19}$";
        if(!name.matches(lengthCheck)) {
            wrg.setVisible(true);
            wrg.setText("Invalid Name!");
            return false;
        }

        String locationCheck = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";
        if(!location.matches(locationCheck)) {
            wrg.setVisible(true);
            wrg.setText("Invalid location!");
            return false;
        }
        return true;
    }
    
    public boolean createNewEvent(String name, String elocation, String description, Date date) {
        return db.createEvent(name, elocation, description, date);
    }
}
