package Main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Callback;

public class inputFilter {
    //filter for the text field to prevent non-integer input
    //!! no longer used as age input was removed from the project, 
    //but I wanted to show off my work anyway :)
    public static void setTextTypeFormatter(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]+")) {
                return change;
            }
            return null;
        }));
    }

    /*
     * preventing the user from giving input over a set char nb
     * given by the controller
     */
    public static void setTextAreaLengthFormatter(TextArea field, int MAX_CHARS) {
        field.setTextFormatter(new TextFormatter<String>(change -> 
            change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    public static void setTextFieldLengthFormatter(TextField field, int MAX_CHARS) {
        field.setTextFormatter(new TextFormatter<String>(change -> 
            change.getControlNewText().length() <= MAX_CHARS ? change : null));
    }

    /*
     * a filter applied on each cell in the date picker 
     * to prevent the user from creating an event with a date that has passed
     */
    public static void setDateLimitFilter(DatePicker datePicker) {
        datePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        datePicker.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    //function will be used to hash passwords before tey are stored in the db, and also hash login credentials before comparing them to the hash in the db
    public static String passwordHash(String password) {
        try {
            //prepare the hashing algorithm to use
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            //convert string to byte array since strings are immutable
            byte[] resultByteArray = messageDigest.digest();
            
            //create the string builder object that to convert the byte array to a string
            StringBuilder sb = new StringBuilder();

            //rebuild the string by looping over the array and appending to the new string
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
    }
    
}
