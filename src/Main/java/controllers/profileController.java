package controllers;

import java.io.IOException;

import core.App;
import core.currUser;
import core.dbUtils;
import core.inputFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class profileController {
    App app = new App();
    dbUtils db = dbUtils.getInstance();
    currUser user = currUser.getInstance();
    @FXML private Label wrg;
    @FXML private Label crt;
    @FXML private TextField newUser;
    @FXML private TextField newEmail;
    @FXML private PasswordField newPass;
    @FXML private PasswordField oldPass;
    @FXML private PasswordField dPass;
    @FXML
    private void updateUser() {
        resetLabels();
        String newName = newUser.getText().trim();
        String usrRegex = "^[a-zA-Z][a-zA-Z0-9_]{6,19}$";
        if(newName.matches(usrRegex)) {
            if(db.getUserID(newName) == -1) {
                db.changeUsername(newName,user.getUserID());
                user.setUser(newName);
                crt.setText("Username changed successfully!");
            } else wrg.setText("Username already exists!");
        } else wrg.setText("Invalid Username!");
    }

    @FXML
    private void updateEmail() {
        resetLabels();
        String newEmail = this.newEmail.getText().trim();
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(newEmail.matches(emailRegex)) {
            if(!db.checkDupEmail(newEmail)) {
                db.changeEmail(newEmail,user.getUserID());
                crt.setText("Email changed successfully!");
            } else wrg.setText("Email already exists!"); 
        } else wrg.setText("Invalid Email!");
    }

    @FXML
    private void changePass() {
        String newPassword = newPass.getText().trim();
        String oldPassword = oldPass.getText().trim();
        if(newPassword.equals(oldPassword)) {
            newPassword = inputFilter.passwordHash(newPassword);
            db.changePassword(newPassword,user.getUserID());
            crt.setText("Password successfully changed!");
        } else wrg.setText("Passwords don't match!");
    }

    @FXML
    private void deleteUser() throws IOException {
        resetLabels();
        String Pass = dPass.getText().trim();
        Pass = inputFilter.passwordHash(Pass); 
        if(db.validateLogIn(user.getUsername(), Pass)) {
            db.deleteAccount(user.getUserID());
            app.changeScenes(pageNavigator.LOG_IN);
        } else wrg.setText("Incorrect password!");
    }

    @FXML
    public void switchCreateEvent() {
        pageNavigator.loadPage(pageNavigator.CREATE_EVENT);
    }

    //applying filters to the user input similar to the ones put on the signup page
    public void initialize() {
        resetLabels();
        inputFilter.setTextFieldLengthFormatter(newUser, 20);
        inputFilter.setTextFieldLengthFormatter(newPass, 20);
        inputFilter.setTextFieldLengthFormatter(oldPass, 20);
    }

    private void resetLabels() {
        wrg.setText("");
        crt.setText("");
    }
}
